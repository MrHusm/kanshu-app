package com.yxsd.kanshu.portal.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.portal.dao.IHistoryTodayDao;
import com.yxsd.kanshu.portal.service.IHistoryTodayService;
import com.yxsd.kanshu.portal.model.HistoryToday;
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
