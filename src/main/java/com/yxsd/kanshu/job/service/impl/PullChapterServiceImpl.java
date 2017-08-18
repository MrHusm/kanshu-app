package com.yxsd.kanshu.job.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.base.utils.DateUtil;
import com.yxsd.kanshu.job.dao.IPullChapterDao;
import com.yxsd.kanshu.job.model.PullChapter;
import com.yxsd.kanshu.job.service.IPullChapterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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


    /**
     *
     * @Title: saveOrUpdatePullChapter
     * @Description: 增加或者更新拉取章节
     * @param pullStatus
     * @param pullFailureCause
     * @author hushengmeng
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveOrUpdatePullChapter(String copyrightCode, String copyrightBookId, String copyrightVolumeId, String copyrightChapterId, int pullStatus , String pullFailureCause){
        //查询该章节是否推送过
        PullChapter pullChapter = this.findMasterUniqueByParams("copyrightCode", copyrightCode, "copyrightChapterId", copyrightChapterId,
                "copyrightBookId", copyrightBookId);
        //增加或者更新拉取卷
        if(pullChapter == null){
            pullChapter = new PullChapter(copyrightCode, copyrightBookId, copyrightVolumeId, copyrightChapterId, pullStatus, pullFailureCause);
            this.save(pullChapter);
        }else{
            pullChapter.setPullStatus(pullStatus);
            pullChapter.setPullFailureCause(pullFailureCause);
            pullChapter.setUpdateDate(DateUtil.getCurrentDateTime());
            this.update(pullChapter);
        }
    }

    @Override
    public List<PullChapter> findByCopyrightChapters(List<String> copyrightChaptersList) {
        return this.pullChapterDao.selectByCopyrightChapterIds(copyrightChaptersList);
    }
}
