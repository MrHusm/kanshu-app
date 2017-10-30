package com.yxsd.kanshu.ucenter.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.ucenter.dao.IUserShelfDao;
import com.yxsd.kanshu.ucenter.model.UserShelf;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userShelfDao")
public class UserShelfDaoImpl extends BaseDaoImpl<UserShelf> implements IUserShelfDao {
}
