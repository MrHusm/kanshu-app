package com.kanshu.kanshu.pay.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.pay.dao.IAlipayOrderDao;
import com.kanshu.kanshu.pay.model.AlipayOrder;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="alipayOrderDao")
public class AlipayOrderDaoImpl extends BaseDaoImpl<AlipayOrder> implements IAlipayOrderDao{
}
