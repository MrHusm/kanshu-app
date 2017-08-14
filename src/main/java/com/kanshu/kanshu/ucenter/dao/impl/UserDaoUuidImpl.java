package com.kanshu.kanshu.ucenter.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.ucenter.dao.IUserUuidDao;
import com.kanshu.kanshu.ucenter.model.UserUuid;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userUuidDao")
public class UserDaoUuidImpl extends BaseDaoImpl<UserUuid> implements IUserUuidDao {
}
