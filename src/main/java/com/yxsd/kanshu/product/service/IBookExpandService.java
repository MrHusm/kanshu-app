package com.yxsd.kanshu.product.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.product.model.BookExpand;

/**
 * Created by lenovo on 2017/8/7.
 */
public interface IBookExpandService extends IBaseService<BookExpand,Long> {

    /**
     * 获取点击量最大的图书
     * @return
     */
    public BookExpand getMaxClickBook();
}
