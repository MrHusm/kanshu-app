package com.kanshu.ucenter.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.ucenter.dao.IUserAccountLogDao;
import com.kanshu.ucenter.model.UserAccount;
import com.kanshu.ucenter.model.UserAccountLog;
import com.kanshu.ucenter.service.IUserAccountLogService;
import com.kanshu.ucenter.service.IUserAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userAccountLogService")
public class UserAccountLogServiceImpl extends BaseServiceImpl<UserAccountLog, Long> implements IUserAccountLogService{

    @Resource(name="userAccountLogDao")
    private IUserAccountLogDao userAccountLogDao;

    @Override
    public IBaseDao<UserAccountLog> getBaseDao() {
        return userAccountLogDao;
    }
}
