package com.yxsd.kanshu.product.dao;

import com.yxsd.kanshu.base.dao.IChapterBaseDao;
import com.yxsd.kanshu.product.model.Chapter;

import java.util.Map;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IChapterDao extends IChapterBaseDao<Chapter> {

    /**
     * 获取更新的章节数量
     * @param params
     * @return
     */
    public Integer updatedChapterCount(Map<String,Object> params);
}
