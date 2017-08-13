package com.kanshu.ucenter.dao.impl;

import com.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.ucenter.dao.IUserPayBookDao;
import com.kanshu.ucenter.model.UserPayBook;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userPayBookDao")
public class UserPayBookDaoImpl extends BaseDaoImpl<UserPayBook> implements IUserPayBookDao {
}
