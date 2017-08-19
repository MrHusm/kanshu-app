package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IVersionInfoDao;
import com.yxsd.kanshu.ucenter.model.VersionInfo;
import com.yxsd.kanshu.ucenter.service.IVersionInfoService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="versionInfoService")
public class VersionInfoServiceImpl extends BaseServiceImpl<VersionInfo, Long> implements IVersionInfoService {

    @Resource(name="versionInfoDao")
    private IVersionInfoDao userAccountDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,VersionInfo> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,VersionInfo> slaveRedisTemplate;

    @Override
    public IBaseDao<VersionInfo> getBaseDao() {
        return userAccountDao;
    }

    @Override
    public VersionInfo getVersionInfoByChannel(Integer channel) {
        String key = RedisKeyConstants.CACHE_VERSION_INFO_CHANNEL_KEY + channel;
        VersionInfo versionInfo = slaveRedisTemplate.opsForValue().get(key);
        if(versionInfo == null){
            versionInfo = this.findUniqueByParams("channel",channel);
            if(versionInfo != null){
                masterRedisTemplate.opsForValue().set(key, versionInfo, 6, TimeUnit.HOURS);
            }
        }
        return versionInfo;
    }
}
