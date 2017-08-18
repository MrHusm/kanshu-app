package com.yxsd.kanshu.product.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.product.dao.IChapterContentDao;
import com.yxsd.kanshu.product.model.ChapterContent;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="chapterContentDao")
public class ChapterContentDaoImpl extends BaseDaoImpl<ChapterContent> implements IChapterContentDao {
}
