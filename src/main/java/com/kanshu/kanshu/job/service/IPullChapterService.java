package com.kanshu.kanshu.job.service;

import com.kanshu.kanshu.base.service.IBaseService;
import com.kanshu.kanshu.job.model.PullChapter;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IPullChapterService extends IBaseService<PullChapter,Long> {

    /**
     * 保存或修改拉取章节的信息
     * @param copyright
     * @param cbid
     * @param cvid
     * @param volumePullStatus
     * @param volumePullFailureCause
     */
    public void saveOrUpdatePullChapter(String copyright, String cbid, String cvid,String ccid, Integer chapterPullStatus, String chapterPullFailureCause);
}
