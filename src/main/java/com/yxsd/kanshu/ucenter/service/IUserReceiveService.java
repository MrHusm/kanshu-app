package com.yxsd.kanshu.ucenter.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.ucenter.model.UserReceive;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IUserReceiveService extends IBaseService<UserReceive,Long> {
    /**
     * 记录用户第三方绑定状态信息
     * @param userId
     * @param type 1:手机号 2：QQ 3：微信 4：微博
     */
    public UserReceive userThirdBind(Long userId,Integer type);
}
