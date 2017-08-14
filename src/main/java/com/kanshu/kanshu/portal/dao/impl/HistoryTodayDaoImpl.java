package com.kanshu.kanshu.portal.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.portal.dao.IHistoryTodayDao;
import com.kanshu.kanshu.portal.model.HistoryToday;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="historyTodayDao")
public class HistoryTodayDaoImpl extends BaseDaoImpl<HistoryToday> implements IHistoryTodayDao {
}
