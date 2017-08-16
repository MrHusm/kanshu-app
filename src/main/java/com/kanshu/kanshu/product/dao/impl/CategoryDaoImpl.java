package com.kanshu.kanshu.product.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.product.dao.ICategoryDao;
import com.kanshu.kanshu.product.model.Category;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="categoryDao")
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements ICategoryDao {
}
