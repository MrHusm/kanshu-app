package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserReceiveDao;
import com.yxsd.kanshu.ucenter.model.UserReceive;
import com.yxsd.kanshu.ucenter.service.IUserReceiveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userReceiveService")
public class UserReceiveServiceImpl extends BaseServiceImpl<UserReceive, Long> implements IUserReceiveService {

    @Resource(name="userReceiveDao")
    private IUserReceiveDao userReceiveDao;


    @Override
    public IBaseDao<UserReceive> getBaseDao() {
        return userReceiveDao;
    }

}
