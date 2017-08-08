package com.kanshu.ucenter.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.ucenter.dao.IUserVipDao;
import com.kanshu.ucenter.model.UserVip;
import com.kanshu.ucenter.service.IUserVipService;
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
