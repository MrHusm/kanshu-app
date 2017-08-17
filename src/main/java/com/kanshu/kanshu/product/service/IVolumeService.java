package com.kanshu.kanshu.product.service;

import com.kanshu.kanshu.base.service.IBaseService;
import com.kanshu.kanshu.product.model.Volume;

/**
 * Created by lenovo on 2017/8/7.
 */
public interface IVolumeService extends IBaseService<Volume,Long> {

    /**
     *
     * @Title: findCount
     * @Description: 查询卷的数量
     * @param volume
     * @return
     * @author hushengmeng
     */
    int findCount(Volume volume);
}
