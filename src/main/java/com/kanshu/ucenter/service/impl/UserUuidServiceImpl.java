package com.kanshu.ucenter.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.ucenter.dao.IUserDao;
import com.kanshu.ucenter.dao.IUserUuidDao;
import com.kanshu.ucenter.model.User;
import com.kanshu.ucenter.model.UserUuid;
import com.kanshu.ucenter.service.IUserService;
import com.kanshu.ucenter.service.IUserUuidService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userUuidService")
public class UserUuidServiceImpl extends BaseServiceImpl<UserUuid, Long> implements IUserUuidService {

    @Resource(name="userUuidDao")
    private IUserUuidDao userUuidDao;

    @Override
    public IBaseDao<UserUuid> getBaseDao() {
        return userUuidDao;
    }
}
