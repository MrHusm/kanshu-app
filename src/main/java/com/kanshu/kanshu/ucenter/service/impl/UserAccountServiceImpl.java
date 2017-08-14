package com.kanshu.kanshu.ucenter.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.ucenter.dao.IUserAccountDao;
import com.kanshu.kanshu.ucenter.service.IUserAccountService;
import com.kanshu.kanshu.ucenter.model.UserAccount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userAccountService")
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccount, Long> implements IUserAccountService {

    @Resource(name="userAccountDao")
    private IUserAccountDao userAccountDao;

    @Override
    public IBaseDao<UserAccount> getBaseDao() {
        return userAccountDao;
    }
}
