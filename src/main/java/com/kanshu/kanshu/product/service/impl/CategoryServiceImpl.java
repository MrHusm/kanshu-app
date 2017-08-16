package com.kanshu.kanshu.product.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.product.dao.ICategoryDao;
import com.kanshu.kanshu.product.model.Category;
import com.kanshu.kanshu.product.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/7.
 */
@Service(value="bookService")
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements ICategoryService{

    @Resource(name="categoryDao")
    private ICategoryDao categoryDao;

    @Override
    public IBaseDao<Category> getBaseDao() {
        return categoryDao;
    }


}
