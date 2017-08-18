package com.yxsd.kanshu.product.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.product.dao.IVipDao;
import com.yxsd.kanshu.product.service.IVipService;
import com.yxsd.kanshu.product.model.Vip;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/7.
 */
@Service(value="vipService")
public class VipServiceImpl extends BaseServiceImpl<Vip, Long> implements IVipService {

    @Resource(name="vipDao")
    private IVipDao vipDao;

    @Override
    public IBaseDao<Vip> getBaseDao() {
        return vipDao;
    }
}
