package com.kanshu.pay.dao.impl;

import com.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.pay.dao.IRechargeItemDao;
import com.kanshu.pay.model.RechargeItem;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="rechargeItemDao")
public class RechargeItemDaoImpl extends BaseDaoImpl<RechargeItem> implements IRechargeItemDao {
}
