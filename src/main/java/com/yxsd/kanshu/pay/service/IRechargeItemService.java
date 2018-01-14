package com.yxsd.kanshu.pay.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.pay.model.RechargeItem;

import java.util.List;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IRechargeItemService extends IBaseService<RechargeItem,Long> {

    /**
     * 获取充值返现最大额度
     * @param type 类型 1：通用 2：微信公众号
     * @return
     */
    public Integer getMaxVirtual(Integer type);

    /**
     * 获取充值信息
     * @param type 类型 1：通用 2：微信公众号
     * @return
     */
    public List<RechargeItem> getRechargeItem(Integer type);
}
