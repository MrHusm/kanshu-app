package com.kanshu.kanshu.ucenter.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.ucenter.dao.IUserPayBookDao;
import com.kanshu.kanshu.ucenter.model.UserPayBook;
import com.kanshu.kanshu.ucenter.service.IUserPayBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userPayBookService")
public class UserPayBookServiceImpl extends BaseServiceImpl<UserPayBook, Long> implements IUserPayBookService {

    @Resource(name="userPayBookDao")
    private IUserPayBookDao userPayBookDao;

    @Override
    public IBaseDao<UserPayBook> getBaseDao() {
        return userPayBookDao;
    }

}
