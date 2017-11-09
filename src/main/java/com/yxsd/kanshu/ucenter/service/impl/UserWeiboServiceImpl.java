package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserWeiboDao;
import com.yxsd.kanshu.ucenter.model.UserWb;
import com.yxsd.kanshu.ucenter.model.UserWeibo;
import com.yxsd.kanshu.ucenter.service.IUserWeiboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    @Override
    public UserWeibo saveUserWeibo(UserWb userWb, Long userId) {
        UserWeibo userWeibo = new UserWeibo();
        userWeibo.setUserId(userId);
        userWeibo.setWeiboId(userWb.getId());
        userWeibo.setScreenName(userWb.getScreen_name());
        userWeibo.setName(userWb.getName());
        userWeibo.setProvince(userWb.getProvince());
        userWeibo.setCity(userWb.getCity());
        userWeibo.setLocation(userWb.getLocation());
        userWeibo.setDescription(userWb.getDescription());
        userWeibo.setUrl(userWb.getUrl());
        userWeibo.setProfileImageUrl(userWb.getProfile_image_url());
        userWeibo.setProfileUrl(userWb.getProfile_url());
        userWeibo.setDomain(userWb.getDomain());
        userWeibo.setWeihao(userWb.getWeihao());
        userWeibo.setGender(userWb.getGender());
        userWeibo.setFollowersCount(userWb.getFollowers_count());
        userWeibo.setFriendsCount(userWb.getFriends_count());
        userWeibo.setStatusesCount(userWb.getStatuses_count());
        userWeibo.setFavouritesCount(userWb.getFavourites_count());
        userWeibo.setCreatedAt(userWb.getCreated_at());
        userWeibo.setFollowing(userWb.getFollowing() ? 1 : 0);
        userWeibo.setAllowAllActMsg(userWb.getAllow_all_act_msg() ? 1 : 0);
        userWeibo.setVerified(userWb.getVerified() ? 1 : 0);
        userWeibo.setVerifiedType(userWb.getVerified_type());
        userWeibo.setRemark(userWb.getRemark());
        userWeibo.setStatus(userWb.getStatus() == null ? null : userWb.getStatus().toString());
        userWeibo.setAllowAllComment(userWb.getAllow_all_comment() ? 1 : 0);
        userWeibo.setAvatarLarge(userWb.getAvatar_large());
        userWeibo.setAvatarHd(userWb.getAvatar_hd());
        userWeibo.setVerifiedReason(userWb.getVerified_reason());
        userWeibo.setFollowMe(userWb.getFollow_me() ? 1 : 0);
        userWeibo.setOnlineStatus(userWb.getOnline_status());
        userWeibo.setBiFollowersCount(userWb.getBi_followers_count());
        userWeibo.setCreateDate(new Date());
        userWeibo.setUpdateDate(new Date());
        save(userWeibo);
        return userWeibo;
    }
}
