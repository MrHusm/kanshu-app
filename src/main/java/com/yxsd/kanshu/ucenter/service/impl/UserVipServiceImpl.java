package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserVipDao;
import com.yxsd.kanshu.ucenter.model.UserVip;
import com.yxsd.kanshu.ucenter.service.IUserVipService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userVipService")
public class UserVipServiceImpl extends BaseServiceImpl<UserVip, Long> implements IUserVipService {

    @Resource(name="userVipDao")
    private IUserVipDao userVipDao;

    @Override
    public IBaseDao<UserVip> getBaseDao() {
        return userVipDao;
    }

}
