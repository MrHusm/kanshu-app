package com.kanshu.kanshu.search.dao.impl;

import org.springframework.stereotype.Repository;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.search.dao.KeyWordDao;
import com.kanshu.kanshu.search.model.KeyWord;


@Repository(value="keyWordDao")
public class KeyWordDaoImpl  extends BaseDaoImpl<KeyWord> implements KeyWordDao{

}
