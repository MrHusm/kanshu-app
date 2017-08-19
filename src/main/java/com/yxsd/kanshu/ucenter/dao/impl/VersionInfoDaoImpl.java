package com.yxsd.kanshu.ucenter.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.ucenter.dao.IVersionInfoDao;
import com.yxsd.kanshu.ucenter.model.VersionInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="versionInfoDao")
public class VersionInfoDaoImpl extends BaseDaoImpl<VersionInfo> implements IVersionInfoDao {
}
