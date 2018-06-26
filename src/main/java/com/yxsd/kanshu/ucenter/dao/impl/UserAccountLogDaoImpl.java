package com.yxsd.kanshu.ucenter.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.ucenter.dao.IUserAccountLogDao;
import com.yxsd.kanshu.ucenter.model.UserAccountLog;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userAccountLogDao")
public class UserAccountLogDaoImpl extends BaseDaoImpl<UserAccountLog> implements IUserAccountLogDao {
    @Override
    public Map<String, Object> getUserTotalMoney(String userId, String channel) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("channel",channel);
        param.put("userId",userId);
        return (Map<String, Object>) this.getSqlSessionQueryTemplate().selectOne("UserAccountLogMapper.getUserTotalMoney",param);
    }
}
