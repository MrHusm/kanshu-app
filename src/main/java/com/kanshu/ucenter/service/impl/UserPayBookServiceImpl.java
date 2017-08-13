package com.kanshu.ucenter.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.ucenter.dao.IUserPayBookDao;
import com.kanshu.ucenter.model.UserPayBook;
import com.kanshu.ucenter.service.IUserPayBookService;
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
