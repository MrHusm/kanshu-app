package com.kanshu.kanshu.job.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.job.dao.IPullChapterDao;
import com.kanshu.kanshu.job.model.PullChapter;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="pullChapterDao")
public class PullChapterDaoImpl extends BaseDaoImpl<PullChapter> implements IPullChapterDao {
}
