package com.kanshu.kanshu.portal.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.portal.dao.IHistoryTodayImgDao;
import com.kanshu.kanshu.portal.service.IHistoryTodayImgService;
import com.kanshu.kanshu.portal.model.HistoryTodayImg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="historyTodayImgService")
public class HistoryTodayImgServiceImpl extends BaseServiceImpl<HistoryTodayImg, Long> implements IHistoryTodayImgService {

    @Resource(name="historyTodayImgDao")
    private IHistoryTodayImgDao historyTodayImgDao;

    @Override
    public IBaseDao<HistoryTodayImg> getBaseDao() {
        return historyTodayImgDao;
    }
}
