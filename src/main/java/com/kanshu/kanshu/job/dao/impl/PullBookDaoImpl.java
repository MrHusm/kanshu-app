package com.kanshu.kanshu.job.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.job.dao.IPullBookDao;
import com.kanshu.kanshu.job.model.PullBook;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="pullBookDao")
public class PullBookDaoImpl extends BaseDaoImpl<PullBook> implements IPullBookDao {
}
