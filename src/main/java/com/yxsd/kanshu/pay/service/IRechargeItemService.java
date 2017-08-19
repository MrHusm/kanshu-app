package com.yxsd.kanshu.pay.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.pay.model.RechargeItem;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IRechargeItemService extends IBaseService<RechargeItem,Long> {

    /**
     * 获取充值返现最大额度
     * @return
     */
    public Integer getMaxVirtual();
}
