package com.kanshu.kanshu.job.service;

import com.kanshu.kanshu.base.service.IBaseService;
import com.kanshu.kanshu.job.model.PullVolume;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IPullVolumeService extends IBaseService<PullVolume,Long> {

    /**
     * 保存或修改拉取卷的信息
     * @param copyright
     * @param cbid
     * @param cvid
     * @param volumePullStatus
     * @param volumePullFailureCause
     */
    public void saveOrUpdatePullVolume(String copyright, String cbid,String cvid ,Integer volumePullStatus, String volumePullFailureCause);
}
