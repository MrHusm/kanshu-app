package com.yxsd.kanshu.pay.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.pay.dao.IRechargeItemDao;
import com.yxsd.kanshu.pay.model.RechargeItem;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="rechargeItemDao")
public class RechargeItemDaoImpl extends BaseDaoImpl<RechargeItem> implements IRechargeItemDao {

    @Override
    public Integer getMaxVirtual(Integer type) {
        Map<String,Integer> condition = new HashMap<String,Integer>();
        condition.put("type",type);
        return (Integer) this.getSqlSessionQueryTemplate().selectOne("RechargeItemMapper.getMaxVirtual",condition);
    }
}
