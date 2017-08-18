package com.yxsd.kanshu.product.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.product.dao.IVipDao;
import com.yxsd.kanshu.product.model.Vip;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="vipDao")
public class VipDaoImpl extends BaseDaoImpl<Vip> implements IVipDao {
}
