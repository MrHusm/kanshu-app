package com.yxsd.kanshu.pay.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.pay.dao.IAlipayOrderDao;
import com.yxsd.kanshu.pay.model.AlipayOrder;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="alipayOrderDao")
public class AlipayOrderDaoImpl extends BaseDaoImpl<AlipayOrder> implements IAlipayOrderDao{
}
