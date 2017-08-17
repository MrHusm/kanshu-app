package com.kanshu.kanshu.job.service;

import com.kanshu.kanshu.base.service.IBaseService;
import com.kanshu.kanshu.job.model.PullVolume;

import java.util.List;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IPullVolumeService extends IBaseService<PullVolume,Long> {

    /**
     * 保存或修改拉取卷的信息
     * @param copyrightCode
     * @param copyrightBookId
     * @param copyrightVolumeId
     * @param pullStatus
     * @param pullFailureCause
     */
    public void saveOrUpdatePullVolume(String copyrightCode, String copyrightBookId, String copyrightVolumeId, int pullStatus , String pullFailureCause);

    /**
     *
     * @Title: findByCopyrightVolumes
     * @Description: 通过供应商卷ID批量获取拉取卷的信息
     * @param copyrightVolumesList
     * @return
     * @author hushengmeng
     */
    List<PullVolume> findByCopyrightVolumes(List<String> copyrightVolumesList);
}
