package com.yxsd.kanshu.product.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.product.model.BookChannelPoint;

/**
 * Created by lenovo on 2017/8/7.
 */
public interface IBookChannelPointService extends IBaseService<BookChannelPoint,Long> {

    /**
     * 获取渠道图书默认计费点
     * @return
     */
    public BookChannelPoint getBookChannelPoint();
}
