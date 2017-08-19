package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserQqDao;
import com.yxsd.kanshu.ucenter.model.UserQq;
import com.yxsd.kanshu.ucenter.service.IUserQqService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userQqService")
public class UserQqServiceImpl extends BaseServiceImpl<UserQq, Long> implements IUserQqService {

    @Resource(name="userQqDao")
    private IUserQqDao userQqDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,UserQq> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,UserQq> slaveRedisTemplate;

    @Override
    public IBaseDao<UserQq> getBaseDao() {
        return userQqDao;
    }

    @Override
    public UserQq getUserQqByUserId(Long userId) {
        String key = RedisKeyConstants.CACHE_USER_QQ_ID_KEY + userId;
        UserQq userQq = slaveRedisTemplate.opsForValue().get(key);
        if(userQq == null){
            userQq = this.findUniqueByParams("userId",userId);
            if(userQq != null){
                masterRedisTemplate.opsForValue().set(key, userQq, 2, TimeUnit.DAYS);
            }
        }
        return userQq;
    }
}
