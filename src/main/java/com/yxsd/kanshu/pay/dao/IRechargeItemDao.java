package com.yxsd.kanshu.pay.dao;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.pay.model.RechargeItem;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IRechargeItemDao extends IBaseDao<RechargeItem> {

    /**
     * 获取充值返现最大额度
     * @return
     */
    public Integer getMaxVirtual();
}
