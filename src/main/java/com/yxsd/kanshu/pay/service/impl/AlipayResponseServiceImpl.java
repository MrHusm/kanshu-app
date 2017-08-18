package com.yxsd.kanshu.pay.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.pay.dao.IAlipayResponseDao;
import com.yxsd.kanshu.pay.model.AlipayResponse;
import com.yxsd.kanshu.pay.service.IAlipayResponseService;
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
