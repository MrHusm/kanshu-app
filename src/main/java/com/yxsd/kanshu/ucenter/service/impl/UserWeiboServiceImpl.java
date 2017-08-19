package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserWeiboDao;
import com.yxsd.kanshu.ucenter.model.UserWeibo;
import com.yxsd.kanshu.ucenter.service.IUserWeiboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userWeiboService")
public class UserWeiboServiceImpl extends BaseServiceImpl<UserWeibo, Long> implements IUserWeiboService {

    @Resource(name="userWeiboDao")
    private IUserWeiboDao userWeiboDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,UserWeibo> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,UserWeibo> slaveRedisTemplate;

    @Override
    public IBaseDao<UserWeibo> getBaseDao() {
        return userWeiboDao;
    }

    @Override
    public UserWeibo getUserWeiboByUserId(Long userId) {
        String key = RedisKeyConstants.CACHE_USER_QQ_ID_KEY + userId;
        UserWeibo userWeibo = slaveRedisTemplate.opsForValue().get(key);
        if(userWeibo == null){
            userWeibo = this.findUniqueByParams("userId",userId);
            if(userWeibo != null){
                masterRedisTemplate.opsForValue().set(key, userWeibo, 2, TimeUnit.DAYS);
            }
        }
        return userWeibo;
    }
}
