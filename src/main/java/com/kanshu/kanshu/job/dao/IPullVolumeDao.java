package com.kanshu.kanshu.job.dao;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.job.model.PullVolume;

import java.util.List;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IPullVolumeDao extends IBaseDao<PullVolume> {

    /**
     *
     * @Title: selectByCopyrightVolumeIds
     * @Description: 通过供应商卷ID批量获取
     * @param copyrightVolumesList
     * @return
     * @author hushengmeng
     */
    List<PullVolume> selectByCopyrightVolumeIds(List<String> copyrightVolumesList);
}
