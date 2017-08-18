package com.yxsd.kanshu.job.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.base.utils.DateUtil;
import com.yxsd.kanshu.job.dao.IPullVolumeDao;
import com.yxsd.kanshu.job.model.PullVolume;
import com.yxsd.kanshu.job.service.IPullVolumeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lenovo on 2017/8/6.
 */
@Service(value="pullVolumeService")
public class PullVolumeServiceImpl extends BaseServiceImpl<PullVolume, Long> implements IPullVolumeService {

    @Resource(name="pullVolumeDao")
    private IPullVolumeDao pullVolumeDao;

    @Override
    public IBaseDao<PullVolume> getBaseDao() {
        return pullVolumeDao;
    }

    /**
     *
     * @Title: saveOrUpdatePullVolume
     * @Description: 增加或者更新拉取卷
     * @param pullStatus
     * @param pullFailureCause
     * @author hushengmeng
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveOrUpdatePullVolume(String copyrightCode, String copyrightBookId, String copyrightVolumeId, int pullStatus , String pullFailureCause){
        //查询该卷是否推送过
        PullVolume pullVolume = this.findMasterUniqueByParams("copyrightCode", copyrightCode, "copyrightVolumeId", copyrightVolumeId);
        //增加或者更新拉取卷
        if(pullVolume == null){
            pullVolume = new PullVolume(copyrightCode, copyrightBookId, copyrightVolumeId, pullStatus, pullFailureCause);
            this.save(pullVolume);
        }else{
            pullVolume.setPullStatus(pullStatus);
            pullVolume.setPullFailureCause(pullFailureCause);
            pullVolume.setUpdateDate(DateUtil.getCurrentDateTime());
            this.update(pullVolume);
        }
    }

    /**
     *
     * @Title: findByCopyrightVolumes
     * @Description: 通过供应商卷ID批量获取拉取卷的信息
     * @param copyrightVolumesList
     * @return
     * @author hushengmeng
     */
    @Override
    public List<PullVolume> findByCopyrightVolumes(List<String> copyrightVolumesList) {
        return this.pullVolumeDao.selectByCopyrightVolumeIds(copyrightVolumesList);
    }
}
