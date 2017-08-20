package com.yxsd.kanshu.product.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.product.model.Category;

import java.util.List;

/**
 * Created by lenovo on 2017/8/7.
 */
public interface ICategoryService extends IBaseService<Category,Long> {

    /**
     * 根据父ID获取分类信息
     * @param pid
     * @return
     */
    public List<Category> getCategorysByPid(Long pid);
}
