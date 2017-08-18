package com.yxsd.kanshu.search.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.search.dao.SearchHeatDao;
import com.yxsd.kanshu.search.model.SearchHeat;
import com.yxsd.kanshu.search.service.SearchHeatService;


@Service(value = "searchHeatService")
public class SearchHeatServiceImpl extends BaseServiceImpl<SearchHeat, Long> implements SearchHeatService {

	@Resource(name = "searchHeatDao")
	private SearchHeatDao searchHeatDao;

	@Override
	public IBaseDao<SearchHeat> getBaseDao() {
		return searchHeatDao;
	}

}
