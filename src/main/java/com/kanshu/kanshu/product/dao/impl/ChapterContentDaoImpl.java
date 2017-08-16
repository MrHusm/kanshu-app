package com.kanshu.kanshu.product.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.product.dao.IChapterContentDao;
import com.kanshu.kanshu.product.model.ChapterContent;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="chapterContentDao")
public class ChapterContentDaoImpl extends BaseDaoImpl<ChapterContent> implements IChapterContentDao {
}
