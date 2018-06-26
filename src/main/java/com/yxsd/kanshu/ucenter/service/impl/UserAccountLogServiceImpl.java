package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.service.IUserAccountLogService;
import com.yxsd.kanshu.ucenter.dao.IUserAccountLogDao;
import com.yxsd.kanshu.ucenter.model.UserAccountLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userAccountLogService")
public class UserAccountLogServiceImpl extends BaseServiceImpl<UserAccountLog, Long> implements IUserAccountLogService {

    @Resource(name="userAccountLogDao")
    private IUserAccountLogDao userAccountLogDao;

    @Override
    public IBaseDao<UserAccountLog> getBaseDao() {
        return userAccountLogDao;
    }

    @Override
    public Map<String, Object> getUserTotalMoney(String userId, String channel) {
        return this.userAccountLogDao.getUserTotalMoney(userId, channel);
    }
}
