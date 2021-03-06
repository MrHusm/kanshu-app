package com.yxsd.kanshu.product.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.product.dao.IVolumeDao;
import com.yxsd.kanshu.product.model.Volume;
import com.yxsd.kanshu.product.service.IVolumeService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/7.
 */
@Service(value="volumeService")
public class VolumeServiceImpl extends BaseServiceImpl<Volume, Long> implements IVolumeService {

    @Resource(name="volumeDao")
    private IVolumeDao volumeDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,Volume> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,Volume> slaveRedisTemplate;

    @Override
    public IBaseDao<Volume> getBaseDao() {
        return volumeDao;
    }

    /**
     *
     * @Title: findCount
     * @Description: 查询卷的数量
     * @param volume
     * @return
     * @author hushengmeng
     */
    @Override
    public int findCount(Volume volume){
        return this.volumeDao.selectCount(volume);
    }


}
