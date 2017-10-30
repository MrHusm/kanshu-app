package com.yxsd.kanshu.ucenter.service;

import com.alibaba.fastjson.JSONObject;
import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.ucenter.model.UserQq;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IUserQqService extends IBaseService<UserQq,Long> {

    /**
     * 根据用户id获取绑定的QQ信息
     * @param userId
     * @return
     */
    public UserQq getUserQqByUserId(Long userId);

    /**
     * 保存QQ信息
     * @param json
     * @param openID
     * @param userId
     * @return
     */
    public UserQq saveUserQq(JSONObject json, String openID, Long userId);
}
