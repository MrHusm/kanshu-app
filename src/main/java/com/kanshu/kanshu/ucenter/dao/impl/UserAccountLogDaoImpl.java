package com.kanshu.kanshu.ucenter.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.ucenter.dao.IUserAccountLogDao;
import com.kanshu.kanshu.ucenter.model.UserAccountLog;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userAccountLogDao")
public class UserAccountLogDaoImpl extends BaseDaoImpl<UserAccountLog> implements IUserAccountLogDao {
}
