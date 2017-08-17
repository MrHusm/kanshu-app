package com.kanshu.kanshu.job.service;

import com.kanshu.kanshu.base.service.IBaseService;
import com.kanshu.kanshu.job.model.PullChapter;

import java.util.List;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IPullChapterService extends IBaseService<PullChapter,Long> {

    /**
     * 保存或修改拉取章节的信息
     * @param copyrightCode
     * @param copyrightBookId
     * @param copyrightVolumeId
     * @param pullStatus
     * @param pullFailureCause
     */
    public void saveOrUpdatePullChapter(String copyrightCode, String copyrightBookId, String copyrightVolumeId, String copyrightChapterId, int pullStatus , String pullFailureCause);

    /**
     *
     * @Title: findByCopyrightChapters
     * @Description: 通过供应商章节ID批量获取
     * @param copyrightChaptersList
     * @return
     * @author hushengmeng
     */
    List<PullChapter> findByCopyrightChapters(List<String> copyrightChaptersList);

}
