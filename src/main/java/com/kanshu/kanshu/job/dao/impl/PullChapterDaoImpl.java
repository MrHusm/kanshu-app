package com.kanshu.kanshu.job.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.job.dao.IPullChapterDao;
import com.kanshu.kanshu.job.model.PullChapter;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="pullChapterDao")
public class PullChapterDaoImpl extends BaseDaoImpl<PullChapter> implements IPullChapterDao {

    /**
     *
     * @Title: selectByCopyrightChapterIds
     * @Description: 通过供应商章节ID批量获取
     * @param copyrightChapterIds
     * @return
     * @author hushengmeng
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PullChapter> selectByCopyrightChapterIds(List<String> copyrightChapterIds){
        return (List<PullChapter>) this.getSqlSessionQueryTemplate().selectList("PullChapterMapper.selectByCopyrightChapterIds", copyrightChapterIds);
    }
}
