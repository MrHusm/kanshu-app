package com.kanshu.portal.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.portal.dao.IHistoryTodayDao;
import com.kanshu.portal.model.HistoryToday;
import com.kanshu.portal.service.IHistoryTodayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="historyTodayService")
public class HistoryTodayServiceImpl extends BaseServiceImpl<HistoryToday, Long> implements IHistoryTodayService {

    @Resource(name="historyTodayDao")
    private IHistoryTodayDao historyTodayDao;

    @Override
    public IBaseDao<HistoryToday> getBaseDao() {
        return historyTodayDao;
    }
}
