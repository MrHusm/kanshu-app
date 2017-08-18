package com.yxsd.kanshu.search.dao.impl;

import org.springframework.stereotype.Repository;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.search.dao.SearchHeatDao;
import com.yxsd.kanshu.search.model.SearchHeat;


@Repository(value="searchHeatDao")
public class SearchHeatDaoImpl extends BaseDaoImpl<SearchHeat> implements SearchHeatDao{

}
