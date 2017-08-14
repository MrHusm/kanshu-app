package com.kanshu.kanshu.pay.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.pay.dao.IRechargeItemDao;
import com.kanshu.kanshu.pay.model.RechargeItem;
import com.kanshu.kanshu.pay.service.IRechargeItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/6.
 */
@Service(value="rechargeItemService")
public class RechargeItemServiceImpl extends BaseServiceImpl<RechargeItem, Long> implements IRechargeItemService {
    @Resource(name="rechargeItemDao")
    private IRechargeItemDao rechargeItemDao;

    @Override
    public IBaseDao<RechargeItem> getBaseDao() {
        return rechargeItemDao;
    }
}
