package com.kanshu.product.dao.impl;

import com.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.product.dao.IVipDao;
import com.kanshu.product.model.Vip;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="vipDao")
public class VipImpl extends BaseDaoImpl<Vip> implements IVipDao {
}
