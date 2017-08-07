package com.kanshu.ucenter.service.impl;

import com.kanshu.base.contants.RedisKeyConstants;
import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.ucenter.dao.IUserDao;
import com.kanshu.ucenter.model.User;
import com.kanshu.ucenter.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements IUserService {



    @Resource(name="userDao")
    private IUserDao userDao;

    //@Resource(name = "masterRedisTemplate")
    //private RedisTemplate<String,User> masterRedisTemplate;

    //@Resource(name = "slaveRedisTemplate")
    //private RedisTemplate<String,User> slaveRedisTemplate;

    @Override
    public IBaseDao<User> getBaseDao() {
        return userDao;
    }


    @Override
    public User getUserByUserId(Long userId) {
        String key = RedisKeyConstants.CACHE_USER_ID_KEY + userId;
        //User user = masterRedisTemplate.opsForValue().get(key);
        User user = null;
        if(user == null){
            user = this.get(userId);
            if(user != null){
               // masterRedisTemplate.opsForValue().set(key, user, 2, TimeUnit.HOURS);
            }
        }
        return user;
    }
}
