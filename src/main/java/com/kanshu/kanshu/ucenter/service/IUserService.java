package com.kanshu.kanshu.ucenter.service;

import com.kanshu.kanshu.base.service.IBaseService;
import com.kanshu.kanshu.ucenter.model.User;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IUserService extends IBaseService<User,Long> {

    /**
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    public User getUserByUserId(Long userId);
}
