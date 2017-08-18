package com.yxsd.kanshu.job.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.job.dao.IPullVolumeDao;
import com.yxsd.kanshu.job.model.PullVolume;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="pullVolumeDao")
public class PullVolumeDaoImpl extends BaseDaoImpl<PullVolume> implements IPullVolumeDao {

    /**
     *
     * @Title: selectByCopyrightVolumeIds
     * @Description: 通过供应商卷ID批量获取
     * @param copyrightVolumesList
     * @return
     * @author hushengmeng
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PullVolume> selectByCopyrightVolumeIds(List<String> copyrightVolumesList) {
        return (List<PullVolume>) this.getSqlSessionQueryTemplate().selectList("PullVolumeMapper.selectByCopyrightVolumeIds", copyrightVolumesList);
    }
}
