package com.yxsd.kanshu.pay.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.pay.dao.IWeixinOrderDao;
import com.yxsd.kanshu.pay.model.WeixinOrder;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="weixinOrderDao")
public class WeixinOrderDaoImpl extends BaseDaoImpl<WeixinOrder> implements IWeixinOrderDao {
}
