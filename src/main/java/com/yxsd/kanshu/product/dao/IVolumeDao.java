package com.yxsd.kanshu.product.dao;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.product.model.Volume;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IVolumeDao extends IBaseDao<Volume> {

    /**
     *
     * @Title: selectCount
     * @Description: 主库查询卷的数量
     * @param volume
     * @return
     * @author hushengmeng
     */
    public int selectCount(Volume volume);
}
