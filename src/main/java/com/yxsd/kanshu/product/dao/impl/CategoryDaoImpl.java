package com.yxsd.kanshu.product.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.product.dao.ICategoryDao;
import com.yxsd.kanshu.product.model.Category;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="categoryDao")
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements ICategoryDao {
}
