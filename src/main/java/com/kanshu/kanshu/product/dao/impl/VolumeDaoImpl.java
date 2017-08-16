package com.kanshu.kanshu.product.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.product.dao.IVolumeDao;
import com.kanshu.kanshu.product.model.Volume;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="volumeDao")
public class VolumeDaoImpl extends BaseDaoImpl<Volume> implements IVolumeDao {
}
