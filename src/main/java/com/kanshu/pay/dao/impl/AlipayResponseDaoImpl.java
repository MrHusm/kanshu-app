package com.kanshu.pay.dao.impl;

import com.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.pay.dao.IAlipayResponseDao;
import com.kanshu.pay.model.AlipayResponse;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="alipayResponseDao")
public class AlipayResponseDaoImpl extends BaseDaoImpl<AlipayResponse> implements IAlipayResponseDao {
}
