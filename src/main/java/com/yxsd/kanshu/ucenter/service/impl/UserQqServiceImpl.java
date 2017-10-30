package com.yxsd.kanshu.ucenter.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserQqDao;
import com.yxsd.kanshu.ucenter.model.UserQq;
import com.yxsd.kanshu.ucenter.service.IUserQqService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    @Override
    public UserQq saveUserQq(JSONObject json, String openID, Long userId) {
        UserQq userQq = new UserQq();
        try {
            userQq.setOpenid(openID);
            userQq.setNickname(json.getString("nickname"));
            userQq.setFigureurl(json.getString("figureurl"));
            userQq.setFigureurl1(json.getString("figureurl_1"));
            userQq.setFigureurl2(json.getString("figureurl_2"));
            userQq.setFigureurlQq1(json.getString("figureurl_qq_1"));
            userQq.setFigureurlQq2(json.getString("figureurl_qq_2"));
            userQq.setGender(json.getString("gender"));
            userQq.setIsYellowVip(json.getInteger("is_yellow_vip"));
            userQq.setVip(json.getInteger("vip"));
            userQq.setYellowVipLevel(json.getInteger("yellow_vip_level"));
            userQq.setLevel(json.getInteger("level"));
            userQq.setIsYellowYearVip(json.getInteger("is_yellow_year_vip"));
            userQq.setUserId(userId);
            userQq.setCreateDate(new Date());
            userQq.setUpdateDate(new Date());
            save(userQq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userQq;
    }
}
