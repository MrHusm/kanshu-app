package com.kanshu.kanshu.pay.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.pay.dao.IAlipayOrderDao;
import com.kanshu.kanshu.pay.model.AlipayOrder;
import com.kanshu.kanshu.pay.service.IAlipayOrderService;
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
