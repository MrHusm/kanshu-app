package com.yxsd.kanshu.ucenter.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.ucenter.dao.IUserAccountDao;
import com.yxsd.kanshu.ucenter.model.UserAccount;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userAccountDao")
public class UserAccountDaoImpl extends BaseDaoImpl<UserAccount> implements IUserAccountDao {
}
