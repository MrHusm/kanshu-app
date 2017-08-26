package com.yxsd.kanshu.product.dao.impl;

import com.yxsd.kanshu.base.dao.impl.ChapterBaseDaoImpl;
import com.yxsd.kanshu.product.dao.IChapterDao;
import com.yxsd.kanshu.product.model.Chapter;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="chapterDao")
public class ChapterDaoImpl extends ChapterBaseDaoImpl<Chapter> implements IChapterDao {


    @Override
    public Integer updatedChapterCount(Map<String,Object> params) {
        return (Integer)this.getSqlSessionQueryTemplate().selectOne("ChapterMapper.updatedChapterCount", params);
    }
}
