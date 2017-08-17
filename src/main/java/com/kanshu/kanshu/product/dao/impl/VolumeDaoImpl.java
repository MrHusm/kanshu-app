package com.kanshu.kanshu.product.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.base.utils.BeanUtils;
import com.kanshu.kanshu.product.dao.IVolumeDao;
import com.kanshu.kanshu.product.model.Volume;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="volumeDao")
public class VolumeDaoImpl extends BaseDaoImpl<Volume> implements IVolumeDao {

    /**
     *
     * @Title: selectCount
     * @Description: 主库查询卷的数量
     * @param volume
     * @return
     * @author hushengmeng
     */
    @Override
    public int selectCount(Volume volume){
        return Integer.parseInt(String.valueOf(getSqlSessionTemplate().selectOne("VolumeMapper.pageCount", BeanUtils.convertBeanToMap(volume))));
    }
}
