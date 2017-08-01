package com.kanshu.ucenter.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.ucenter.dao.IUserDao;
import com.kanshu.ucenter.model.User;
import com.kanshu.ucenter.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements IUserService {

    @Resource(name="userDao")
    private IUserDao userDao;

    @Override
    public IBaseDao<User> getBaseDao() {
        return userDao;
    }
}
