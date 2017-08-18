package com.yxsd.kanshu.job.dao;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.job.model.PullChapter;

import java.util.List;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IPullChapterDao extends IBaseDao<PullChapter> {

    /**
     *
     * @Title: selectByCopyrightChapterIds
     * @Description: 通过供应商章节ID批量获取
     * @param copyrightChapterIds
     * @return
     * @author hushengmeng
     */
    List<PullChapter> selectByCopyrightChapterIds(List<String> copyrightChapterIds);
}
