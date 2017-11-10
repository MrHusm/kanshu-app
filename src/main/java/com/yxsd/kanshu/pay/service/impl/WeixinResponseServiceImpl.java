package com.yxsd.kanshu.pay.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.pay.dao.IWeixinResponseDao;
import com.yxsd.kanshu.pay.model.WeixinResponse;
import com.yxsd.kanshu.pay.service.IWeixinResponseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/6.
 */
@Service(value="weixinResponseService")
public class WeixinResponseServiceImpl extends BaseServiceImpl<WeixinResponse, Long> implements IWeixinResponseService {
    @Resource(name="weixinResponseDao")
    private IWeixinResponseDao weixinResponseDao;

    @Override
    public IBaseDao<WeixinResponse> getBaseDao() {
        return weixinResponseDao;
    }
}
