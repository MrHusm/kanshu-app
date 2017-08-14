package com.kanshu.kanshu.product.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.product.dao.IVipDao;
import com.kanshu.kanshu.product.service.IVipService;
import com.kanshu.kanshu.product.model.Vip;
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
