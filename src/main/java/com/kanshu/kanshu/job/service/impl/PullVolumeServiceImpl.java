package com.kanshu.kanshu.job.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.job.dao.IPullVolumeDao;
import com.kanshu.kanshu.job.model.PullVolume;
import com.kanshu.kanshu.job.service.IPullVolumeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public void saveOrUpdatePullVolume(String copyright, String cbid, String cvid, Integer volumePullStatus, String volumePullFailureCause) {

    }
}
