package com.kanshu.ucenter.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.ucenter.dao.IUserAccountDao;
import com.kanshu.ucenter.dao.IUserDao;
import com.kanshu.ucenter.model.User;
import com.kanshu.ucenter.model.UserAccount;
import com.kanshu.ucenter.service.IUserAccountService;
import com.kanshu.ucenter.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userAccountService")
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccount, Long> implements IUserAccountService{

    @Resource(name="userAccountDao")
    private IUserAccountDao userAccountDao;

    @Override
    public IBaseDao<UserAccount> getBaseDao() {
        return userAccountDao;
    }
}
