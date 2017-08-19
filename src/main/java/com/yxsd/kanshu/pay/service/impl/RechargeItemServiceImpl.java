package com.yxsd.kanshu.pay.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.pay.dao.IRechargeItemDao;
import com.yxsd.kanshu.pay.model.RechargeItem;
import com.yxsd.kanshu.pay.service.IRechargeItemService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2017/8/6.
 */
@Service(value="rechargeItemService")
public class RechargeItemServiceImpl extends BaseServiceImpl<RechargeItem, Long> implements IRechargeItemService {
    @Resource(name="rechargeItemDao")
    private IRechargeItemDao rechargeItemDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,Integer> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,Integer> slaveRedisTemplate;

    @Override
    public IBaseDao<RechargeItem> getBaseDao() {
        return rechargeItemDao;
    }

    @Override
    public Integer getMaxVirtual() {
        String key = RedisKeyConstants.CACHE_RECHARGE_MAX_VIRTUAL_KEY;
        Integer maxVirtual = slaveRedisTemplate.opsForValue().get(key);
        if (maxVirtual == null) {
            maxVirtual = this.rechargeItemDao.getMaxVirtual();
            if (maxVirtual != null) {
                masterRedisTemplate.opsForValue().set(key, maxVirtual, 2, TimeUnit.DAYS);
            }
        }
        return maxVirtual;
    }
}
