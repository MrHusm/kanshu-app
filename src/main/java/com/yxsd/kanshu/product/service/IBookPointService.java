package com.yxsd.kanshu.product.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.product.model.BookPoint;

import java.util.Map;

/**
 * Created by lenovo on 2017/8/7.
 */
public interface IBookPointService extends IBaseService<BookPoint,Long> {

    /**
     * 获取图书计费点
     * @param bookId  图书ID
     * @param channel 渠道号
     * @return
     */
    public Integer getBookPointNum(Long bookId, String channel);

    /**
     * 获取所有图书计费点
     * @return
     */
    public Map<Long,Integer> getBookPointMap();
}
