package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserAccountDao;
import com.yxsd.kanshu.ucenter.service.IUserAccountService;
import com.yxsd.kanshu.ucenter.model.UserAccount;
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
