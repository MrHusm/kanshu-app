package com.yxsd.kanshu.job.dao;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.job.model.PullBook;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IPullBookDao extends IBaseDao<PullBook> {

    /**
     *
     * @Title: selectCount
     * @Description: 获取数量
     * @param paramsMap
     * @return
     * @author hushengmeng
     */
    Integer selectCount(Map<String, Object> paramsMap);

    /**
     *
     * @Title: selectByCopyrightBookId
     * @Description: 通过供应商图书ID批量获取拉取图书信息
     * @param copyrightBookIds
     * @return
     * @author hushengmeng
     */
    List<PullBook> selectByCopyrightBookId(List<String> copyrightBookIds);
}
