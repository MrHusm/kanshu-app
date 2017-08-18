package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserUuidDao;
import com.yxsd.kanshu.ucenter.model.UserUuid;
import com.yxsd.kanshu.ucenter.service.IUserUuidService;
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
