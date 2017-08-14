package com.kanshu.kanshu.ucenter.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.ucenter.dao.IUserVipDao;
import com.kanshu.kanshu.ucenter.model.UserVip;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userVipDao")
public class UserVipDaoImpl extends BaseDaoImpl<UserVip> implements IUserVipDao {
}
