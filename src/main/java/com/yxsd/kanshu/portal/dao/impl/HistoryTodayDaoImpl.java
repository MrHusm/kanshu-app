package com.yxsd.kanshu.portal.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.portal.dao.IHistoryTodayDao;
import com.yxsd.kanshu.portal.model.HistoryToday;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="historyTodayDao")
public class HistoryTodayDaoImpl extends BaseDaoImpl<HistoryToday> implements IHistoryTodayDao {
}
