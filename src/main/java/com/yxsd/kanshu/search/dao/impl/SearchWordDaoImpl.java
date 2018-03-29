package com.yxsd.kanshu.search.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.search.dao.ISearchWordDao;
import com.yxsd.kanshu.search.model.SearchWord;
import org.springframework.stereotype.Repository;


@Repository(value="searchWordDao")
public class SearchWordDaoImpl extends BaseDaoImpl<SearchWord> implements ISearchWordDao {
}
