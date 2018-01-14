package com.yxsd.kanshu.pay.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.pay.dao.IRechargeItemDao;
import com.yxsd.kanshu.pay.model.RechargeItem;
import com.yxsd.kanshu.pay.service.IRechargeItemService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,List<RechargeItem>> listMasterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,List<RechargeItem>> listSlaveRedisTemplate;

    @Override
    public IBaseDao<RechargeItem> getBaseDao() {
        return rechargeItemDao;
    }

    @Override
    public Integer getMaxVirtual(Integer type) {
        String key = RedisKeyConstants.CACHE_RECHARGE_MAX_VIRTUAL_KEY + type;
        Integer maxVirtual = slaveRedisTemplate.opsForValue().get(key);
        if (maxVirtual == null) {
            maxVirtual = this.rechargeItemDao.getMaxVirtual(type);
            if (maxVirtual != null) {
                masterRedisTemplate.opsForValue().set(key, maxVirtual, 1, TimeUnit.DAYS);
            }
        }
        return maxVirtual;
    }

    @Override
    public List<RechargeItem> getRechargeItem(Integer type) {
        String key = RedisKeyConstants.CACHE_RECHARGE_ITEM_LIST_KEY + type;
        List<RechargeItem> rechargeItems  = listSlaveRedisTemplate.opsForValue().get(key);
        if (CollectionUtils.isEmpty(rechargeItems)) {
            rechargeItems = this.findListByParams("type",type);
            if (CollectionUtils.isNotEmpty(rechargeItems)) {
                listMasterRedisTemplate.opsForValue().set(key, rechargeItems, 1, TimeUnit.DAYS);
            }
        }
        return rechargeItems;
    }
}
