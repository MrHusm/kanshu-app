package com.yxsd.kanshu.product.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.product.dao.IBookExpandDao;
import com.yxsd.kanshu.product.model.BookExpand;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="bookExpandDao")
public class BookExpandDaoImpl extends BaseDaoImpl<BookExpand> implements IBookExpandDao {
}
