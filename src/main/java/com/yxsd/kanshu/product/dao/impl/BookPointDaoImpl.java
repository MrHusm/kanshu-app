package com.yxsd.kanshu.product.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.product.dao.IBookPointDao;
import com.yxsd.kanshu.product.model.BookPoint;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="bookPointDao")
public class BookPointDaoImpl extends BaseDaoImpl<BookPoint> implements IBookPointDao {
}
