package com.kanshu.kanshu.product.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.product.dao.IAuthorDao;
import com.kanshu.kanshu.product.model.Author;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="authorDao")
public class AuthorDaoImpl extends BaseDaoImpl<Author> implements IAuthorDao {
}
