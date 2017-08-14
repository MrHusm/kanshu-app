package com.kanshu.kanshu.ucenter.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.ucenter.dao.IUserPayBookDao;
import com.kanshu.kanshu.ucenter.model.UserPayBook;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userPayBookDao")
public class UserPayBookDaoImpl extends BaseDaoImpl<UserPayBook> implements IUserPayBookDao {
}
