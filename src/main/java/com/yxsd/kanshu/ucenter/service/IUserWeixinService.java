package com.yxsd.kanshu.ucenter.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.ucenter.model.UserWeixin;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IUserWeixinService extends IBaseService<UserWeixin,Long> {

    /**
     * 根据用户id获取绑定的微博信息
     * @param userId
     * @return
     */
    public UserWeixin getUserWeixinByUserId(Long userId);
}
