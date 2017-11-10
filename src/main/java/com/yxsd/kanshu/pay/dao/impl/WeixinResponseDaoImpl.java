package com.yxsd.kanshu.pay.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.pay.dao.IWeixinResponseDao;
import com.yxsd.kanshu.pay.model.WeixinResponse;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="weixinResponseDao")
public class WeixinResponseDaoImpl extends BaseDaoImpl<WeixinResponse> implements IWeixinResponseDao {
}
