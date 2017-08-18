package com.yxsd.kanshu.product.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.product.dao.ICategoryDao;
import com.yxsd.kanshu.product.model.Category;
import com.yxsd.kanshu.product.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/7.
 */
@Service(value="categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements ICategoryService{

    @Resource(name="categoryDao")
    private ICategoryDao categoryDao;

    @Override
    public IBaseDao<Category> getBaseDao() {
        return categoryDao;
    }


}
