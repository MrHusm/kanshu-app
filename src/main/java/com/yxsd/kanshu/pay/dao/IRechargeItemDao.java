package com.yxsd.kanshu.pay.dao;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.pay.model.RechargeItem;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IRechargeItemDao extends IBaseDao<RechargeItem> {

    /**
     * 获取充值返现最大额度
     * @param type 类型 1：通用 2：微信公众号
     * @return
     */
    public Integer getMaxVirtual(Integer type);
}
