package com.kanshu.kanshu.ucenter.service.impl;

import com.kanshu.kanshu.base.contants.RedisKeyConstants;
import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.ucenter.dao.IUserDao;
import com.kanshu.kanshu.ucenter.service.IUserService;
import com.kanshu.kanshu.ucenter.model.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements IUserService {

    @Resource(name="userDao")
    private IUserDao userDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,User> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,User> slaveRedisTemplate;

    @Override
    public IBaseDao<User> getBaseDao() {
        return userDao;
    }


    @Override
    public User getUserByUserId(Long userId) {
        String key = RedisKeyConstants.CACHE_USER_ID_KEY + userId;
        User user = slaveRedisTemplate.opsForValue().get(key);
        if(user == null){
            user = this.get(userId);
            if(user != null){
                masterRedisTemplate.opsForValue().set(key, user, 2, TimeUnit.HOURS);
            }
        }
        return user;
    }
}
