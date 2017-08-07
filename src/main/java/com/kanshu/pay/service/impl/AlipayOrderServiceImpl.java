package com.kanshu.pay.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.pay.dao.IAlipayOrderDao;
import com.kanshu.pay.model.AlipayOrder;
import com.kanshu.pay.service.IAlipayOrderService;
import com.kanshu.ucenter.dao.IUserAccountLogDao;
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
