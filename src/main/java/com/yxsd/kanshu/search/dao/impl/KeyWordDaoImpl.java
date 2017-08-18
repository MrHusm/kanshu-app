package com.yxsd.kanshu.search.dao.impl;

import org.springframework.stereotype.Repository;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.search.dao.KeyWordDao;
import com.yxsd.kanshu.search.model.KeyWord;


@Repository(value="keyWordDao")
public class KeyWordDaoImpl  extends BaseDaoImpl<KeyWord> implements KeyWordDao{

}
