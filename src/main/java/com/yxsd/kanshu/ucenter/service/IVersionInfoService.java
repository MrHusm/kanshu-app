package com.yxsd.kanshu.ucenter.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.ucenter.model.VersionInfo;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IVersionInfoService extends IBaseService<VersionInfo,Long> {

    /**
     * 更加渠道号获取版本信息
     * @param channel
     * @return
     */
    public VersionInfo getVersionInfoByChannel(Integer channel);
}
