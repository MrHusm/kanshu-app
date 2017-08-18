package com.kanshu.kanshu.search.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.search.dao.KeyWordDao;
import com.kanshu.kanshu.search.model.KeyWord;
import com.kanshu.kanshu.search.service.KeyWordService;


@Service(value = "searchHeatService")
public class KeyWordServiceImpl extends BaseServiceImpl<KeyWord, Long> implements KeyWordService {

	@Resource(name = "keyWordDao")
	private KeyWordDao keyWordtDao;

	@Override
	public IBaseDao<KeyWord> getBaseDao() {
		return keyWordtDao;
	}

}
