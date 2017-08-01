package com.kanshu.portal.dao.impl;

import com.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.portal.dao.IHistoryTodayDao;
import com.kanshu.portal.model.HistoryToday;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="historyTodayDao")
public class HistoryTodayDaoImpl extends BaseDaoImpl<HistoryToday> implements IHistoryTodayDao {
}
