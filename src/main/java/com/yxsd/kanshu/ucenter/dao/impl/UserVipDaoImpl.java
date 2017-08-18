package com.yxsd.kanshu.ucenter.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.ucenter.dao.IUserVipDao;
import com.yxsd.kanshu.ucenter.model.UserVip;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userVipDao")
public class UserVipDaoImpl extends BaseDaoImpl<UserVip> implements IUserVipDao {
}
