package com.yxsd.kanshu.portal.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.portal.model.Special;

import java.util.List;
/**
 * 
 * @author hanweiwei
 * @date 2017年12月30日
 *
 */
public interface ISpecialService extends IBaseService<Special, Long> {

	public List<Special> getSpecials();
	
} 
