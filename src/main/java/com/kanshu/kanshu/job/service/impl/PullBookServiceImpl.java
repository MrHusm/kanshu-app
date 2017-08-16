package com.kanshu.kanshu.job.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.job.dao.IPullBookDao;
import com.kanshu.kanshu.job.model.PullBook;
import com.kanshu.kanshu.job.service.IPullBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/6.
 */
@Service(value="pullBookService")
public class PullBookServiceImpl extends BaseServiceImpl<PullBook, Long> implements IPullBookService {

    @Resource(name="pullBookDao")
    private IPullBookDao pullBookDao;

    @Override
    public IBaseDao<PullBook> getBaseDao() {
        return pullBookDao;
    }

    @Override
    public void saveOrUpdatePullBook(String copyright, String cbid, Integer pullStatus, String pullFailureCause) {

    }
}
