package com.kanshu.ucenter.dao.impl;

import com.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.ucenter.dao.IUserAccountLogDao;
import com.kanshu.ucenter.dao.IUserDao;
import com.kanshu.ucenter.model.User;
import com.kanshu.ucenter.model.UserAccountLog;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userAccountLogDao")
public class UserAccountLogDaoImpl extends BaseDaoImpl<UserAccountLog> implements IUserAccountLogDao {
}
