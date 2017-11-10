package com.yxsd.kanshu.pay.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.pay.dao.IWeixinOrderDao;
import com.yxsd.kanshu.pay.model.WeixinOrder;
import com.yxsd.kanshu.pay.service.IWeixinOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/6.
 */
@Service(value="weixinOrderService")
public class WeixinOrderServiceImpl extends BaseServiceImpl<WeixinOrder, Long> implements IWeixinOrderService {
    @Resource(name="weixinOrderDao")
    private IWeixinOrderDao weixinOrderDao;

    @Override
    public IBaseDao<WeixinOrder> getBaseDao() {
        return weixinOrderDao;
    }
}
