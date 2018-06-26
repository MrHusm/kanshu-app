package com.yxsd.kanshu.ucenter.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.ucenter.model.UserAccountLog;

import java.util.Map;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IUserAccountLogService extends IBaseService<UserAccountLog,Long> {

    /**
     * 获取用户总充值金额
     * @param channel
     * @param userId
     * @return
     */
    public Map<String, Object> getUserTotalMoney(String userId,String channel);
}
