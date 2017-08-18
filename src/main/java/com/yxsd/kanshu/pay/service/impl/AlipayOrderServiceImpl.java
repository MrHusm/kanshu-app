package com.yxsd.kanshu.pay.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.pay.dao.IAlipayOrderDao;
import com.yxsd.kanshu.pay.model.AlipayOrder;
import com.yxsd.kanshu.pay.service.IAlipayOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/6.
 */
@Service(value="alipayOrderService")
public class AlipayOrderServiceImpl extends BaseServiceImpl<AlipayOrder, Long> implements IAlipayOrderService {
    @Resource(name="alipayOrderDao")
    private IAlipayOrderDao alipayOrderDao;

    @Override
    public IBaseDao<AlipayOrder> getBaseDao() {
        return alipayOrderDao;
    }
}
