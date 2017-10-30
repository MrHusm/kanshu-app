package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserShelfDao;
import com.yxsd.kanshu.ucenter.model.UserShelf;
import com.yxsd.kanshu.ucenter.service.IUserShelfService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userShelfService")
public class UserShelfServiceImpl extends BaseServiceImpl<UserShelf, Long> implements IUserShelfService {

    @Resource(name="userShelfDao")
    private IUserShelfDao userShelfDao;

    @Override
    public IBaseDao<UserShelf> getBaseDao() {
        return userShelfDao;
    }

}
