package com.yxsd.kanshu.portal.dao.impl;

import org.springframework.stereotype.Repository;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.portal.dao.ISpecialDao;
import com.yxsd.kanshu.portal.model.Special;
/**
 * 
 * @author hanweiwei
 * @date 2017年12月30日
 *
 */
@Repository(value="specialDao")
public class SpecialDaoImpl extends BaseDaoImpl<Special> implements ISpecialDao {

}
