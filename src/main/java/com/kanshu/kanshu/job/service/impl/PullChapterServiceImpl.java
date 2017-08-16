package com.kanshu.kanshu.job.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.job.dao.IPullChapterDao;
import com.kanshu.kanshu.job.model.PullChapter;
import com.kanshu.kanshu.job.service.IPullChapterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/6.
 */
@Service(value="pullChapterService")
public class PullChapterServiceImpl extends BaseServiceImpl<PullChapter, Long> implements IPullChapterService {

    @Resource(name="pullChapterDao")
    private IPullChapterDao pullChapterDao;

    @Override
    public IBaseDao<PullChapter> getBaseDao() {
        return pullChapterDao;
    }


    @Override
    public void saveOrUpdatePullChapter(String copyright, String cbid, String cvid, String ccid, Integer chapterPullStatus, String chapterPullFailureCause) {

    }
}
