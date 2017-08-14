package com.kanshu.chapter.base.dao.impl;

import com.kanshu.chapter.base.dao.IChapterDao;
import com.kanshu.kanshu.product.model.Chapter;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="chapterDao")
public class ChapterImpl extends BaseDaoImpl<Chapter> implements IChapterDao {
}
