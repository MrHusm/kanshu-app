package com.yxsd.kanshu.product.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.product.dao.IBookChannelPointDao;
import com.yxsd.kanshu.product.model.BookChannelPoint;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="bookChannelPointDao")
public class BookChannelPointDaoImpl extends BaseDaoImpl<BookChannelPoint> implements IBookChannelPointDao {
}
