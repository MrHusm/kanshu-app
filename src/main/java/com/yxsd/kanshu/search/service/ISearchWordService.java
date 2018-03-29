package com.yxsd.kanshu.search.service;

import com.yxsd.kanshu.search.model.SearchWord;

public interface ISearchWordService {

    /**
     * 获取推荐搜索词信息
     * @return
     */
    SearchWord getSearchWord();

}
