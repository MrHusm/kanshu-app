package com.kanshu.product.dao.impl;

import com.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.product.dao.IBookDao;
import com.kanshu.product.model.Book;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="bookDao")
public class BookImpl extends BaseDaoImpl<Book> implements IBookDao {
}
