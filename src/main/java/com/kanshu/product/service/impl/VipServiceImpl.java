package com.kanshu.product.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.product.dao.IVipDao;
import com.kanshu.product.model.Vip;
import com.kanshu.product.service.IVipService;
import com.kanshu.ucenter.dao.IUserAccountLogDao;
import com.kanshu.ucenter.model.UserAccountLog;
import com.kanshu.ucenter.service.IUserAccountLogService;
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
