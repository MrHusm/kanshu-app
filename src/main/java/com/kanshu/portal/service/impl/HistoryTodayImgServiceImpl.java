package com.kanshu.portal.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.portal.dao.IHistoryTodayImgDao;
import com.kanshu.portal.model.HistoryTodayImg;
import com.kanshu.portal.service.IHistoryTodayImgService;
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
