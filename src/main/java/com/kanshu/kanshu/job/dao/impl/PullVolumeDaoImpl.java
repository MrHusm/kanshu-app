package com.kanshu.kanshu.job.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.job.dao.IPullVolumeDao;
import com.kanshu.kanshu.job.model.PullVolume;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="pullVolumeDao")
public class PullVolumeDaoImpl extends BaseDaoImpl<PullVolume> implements IPullVolumeDao {
}
