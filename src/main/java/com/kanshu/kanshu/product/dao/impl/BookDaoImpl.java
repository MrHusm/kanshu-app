package com.kanshu.kanshu.product.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.product.dao.IBookDao;
import com.kanshu.kanshu.product.model.Book;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="bookDao")
public class BookDaoImpl extends BaseDaoImpl<Book> implements IBookDao {
}
