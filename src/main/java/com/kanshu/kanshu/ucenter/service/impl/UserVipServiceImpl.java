package com.kanshu.kanshu.ucenter.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.ucenter.dao.IUserVipDao;
import com.kanshu.kanshu.ucenter.model.UserVip;
import com.kanshu.kanshu.ucenter.service.IUserVipService;
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
