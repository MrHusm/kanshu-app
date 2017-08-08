package com.kanshu.pay.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.pay.dao.IAlipayResponseDao;
import com.kanshu.pay.model.AlipayResponse;
import com.kanshu.pay.service.IAlipayResponseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/6.
 */
@Service(value="alipayResponseService")
public class AlipayResponseServiceImpl extends BaseServiceImpl<AlipayResponse, Long> implements IAlipayResponseService {
    @Resource(name="alipayResponseDao")
    private IAlipayResponseDao alipayResponseDao;

    @Override
    public IBaseDao<AlipayResponse> getBaseDao() {
        return alipayResponseDao;
    }
}
