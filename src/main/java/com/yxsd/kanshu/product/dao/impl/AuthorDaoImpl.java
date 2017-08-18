package com.yxsd.kanshu.product.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.product.dao.IAuthorDao;
import com.yxsd.kanshu.product.model.Author;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="authorDao")
public class AuthorDaoImpl extends BaseDaoImpl<Author> implements IAuthorDao {
}
