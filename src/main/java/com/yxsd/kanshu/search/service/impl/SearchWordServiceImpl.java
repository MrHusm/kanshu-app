package com.yxsd.kanshu.search.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.search.dao.ISearchWordDao;
import com.yxsd.kanshu.search.model.SearchWord;
import com.yxsd.kanshu.search.service.ISearchWordService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service(value = "searchWordService")
public class SearchWordServiceImpl extends BaseServiceImpl<SearchWord, Long> implements ISearchWordService {

	@Resource(name = "searchWordDao")
	private ISearchWordDao searchWordDao;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String,SearchWord> masterRedisTemplate;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String,SearchWord> slaveRedisTemplate;

	@Override
	public IBaseDao<SearchWord> getBaseDao() {
		return searchWordDao;
	}

	@Override
	public SearchWord getSearchWord() {
		String key = RedisKeyConstants.CACHE_SEARCH_WORD_KEY;
		SearchWord searchWord = slaveRedisTemplate.opsForValue().get(key);
		if(searchWord == null){
			List<SearchWord> searchWords = this.findListByParamsObjs(null);
			if(CollectionUtils.isNotEmpty(searchWords)){
				searchWord = searchWords.get(0);
				masterRedisTemplate.opsForValue().set(key, searchWord, 1, TimeUnit.DAYS);
			}
		}
		return searchWord;
	}
}
