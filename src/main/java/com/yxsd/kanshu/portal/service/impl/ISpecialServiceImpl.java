package com.yxsd.kanshu.portal.service.impl;


import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.portal.dao.ISpecialDao;
import com.yxsd.kanshu.portal.model.Special;
import com.yxsd.kanshu.portal.service.ISpecialService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * 
 * @author hanweiwei
 * @date 2017年12月30日
 *
 */
@Service(value="specialService")
public class ISpecialServiceImpl extends BaseServiceImpl<Special, Long> implements ISpecialService{

	@Resource(name="specialDao")
	private ISpecialDao specialDao;
	
    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,Special> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,Special> slaveRedisTemplate;
	
	@Override
	public IBaseDao<Special> getBaseDao() {
		return specialDao;
	}

	@Override
	public List<Special> getSpecials() {
		
		return this.specialDao.selectList(null);
	}

	
}
