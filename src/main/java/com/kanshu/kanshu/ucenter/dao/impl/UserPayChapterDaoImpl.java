package com.kanshu.kanshu.ucenter.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.ucenter.dao.IUserPayChapterDao;
import com.kanshu.kanshu.ucenter.model.UserPayChapter;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userPayChapterDao")
public class UserPayChapterDaoImpl extends BaseDaoImpl<UserPayChapter> implements IUserPayChapterDao {
}
