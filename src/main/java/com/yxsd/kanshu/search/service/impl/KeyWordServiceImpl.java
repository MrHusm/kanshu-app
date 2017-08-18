package com.yxsd.kanshu.search.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.search.dao.KeyWordDao;
import com.yxsd.kanshu.search.model.KeyWord;
import com.yxsd.kanshu.search.service.KeyWordService;


@Service(value = "searchHeatService")
public class KeyWordServiceImpl extends BaseServiceImpl<KeyWord, Long> implements KeyWordService {

	@Resource(name = "keyWordDao")
	private KeyWordDao keyWordtDao;

	@Override
	public IBaseDao<KeyWord> getBaseDao() {
		return keyWordtDao;
	}

}
