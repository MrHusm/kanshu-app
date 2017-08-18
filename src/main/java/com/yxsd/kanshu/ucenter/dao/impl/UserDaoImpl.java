package com.yxsd.kanshu.ucenter.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.ucenter.dao.IUserDao;
import com.yxsd.kanshu.ucenter.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{
}
