package com.kanshu.kanshu.search.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.search.dao.SearchHeatDao;
import com.kanshu.kanshu.search.model.SearchHeat;
import com.kanshu.kanshu.search.service.SearchHeatService;


@Service(value = "searchHeatService")
public class SearchHeatServiceImpl extends BaseServiceImpl<SearchHeat, Long> implements SearchHeatService {

	@Resource(name = "searchHeatDao")
	private SearchHeatDao searchHeatDao;

	@Override
	public IBaseDao<SearchHeat> getBaseDao() {
		return searchHeatDao;
	}

}
