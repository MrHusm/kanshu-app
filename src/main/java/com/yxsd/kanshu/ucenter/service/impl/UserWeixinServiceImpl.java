package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserWeixinDao;
import com.yxsd.kanshu.ucenter.model.UserWeixin;
import com.yxsd.kanshu.ucenter.service.IUserWeixinService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userWeixinService")
public class UserWeixinServiceImpl extends BaseServiceImpl<UserWeixin, Long> implements IUserWeixinService {

    @Resource(name="userWeixinDao")
    private IUserWeixinDao userWeixinDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,UserWeixin> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,UserWeixin> slaveRedisTemplate;

    @Override
    public IBaseDao<UserWeixin> getBaseDao() {
        return userWeixinDao;
    }

    @Override
    public UserWeixin getUserWeixinByUserId(Long userId) {
        String key = RedisKeyConstants.CACHE_USER_QQ_ID_KEY + userId;
        UserWeixin userWeixin = slaveRedisTemplate.opsForValue().get(key);
        if(userWeixin == null){
            userWeixin = this.findUniqueByParams("userId",userId);
            if(userWeixin != null){
                masterRedisTemplate.opsForValue().set(key, userWeixin, 2, TimeUnit.DAYS);
            }
        }
        return userWeixin;
    }
}
