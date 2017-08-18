package com.yxsd.kanshu.product.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.product.dao.IBookDao;
import com.yxsd.kanshu.product.model.Book;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="bookDao")
public class BookDaoImpl extends BaseDaoImpl<Book> implements IBookDao {
}
