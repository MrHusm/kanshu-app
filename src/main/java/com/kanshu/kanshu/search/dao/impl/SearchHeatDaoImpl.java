package com.kanshu.kanshu.search.dao.impl;

import org.springframework.stereotype.Repository;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.search.dao.SearchHeatDao;
import com.kanshu.kanshu.search.model.SearchHeat;


@Repository(value="searchHeatDao")
public class SearchHeatDaoImpl extends BaseDaoImpl<SearchHeat> implements SearchHeatDao{

}
