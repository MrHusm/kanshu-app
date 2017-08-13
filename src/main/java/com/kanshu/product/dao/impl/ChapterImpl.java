package com.kanshu.product.dao.impl;

import com.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.product.dao.IChapterDao;
import com.kanshu.product.model.Chapter;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="chapterDao")
public class ChapterImpl extends BaseDaoImpl<Chapter> implements IChapterDao {
}
