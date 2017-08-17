package com.kanshu.kanshu.job.dao.impl;

import com.kanshu.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.kanshu.job.dao.IPullBookDao;
import com.kanshu.kanshu.job.model.PullBook;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/8/6.
 */
@Repository(value="pullBookDao")
public class PullBookDaoImpl extends BaseDaoImpl<PullBook> implements IPullBookDao {

    /**
     *
     * @Title: selectCount
     * @Description: 获取数量
     * @param paramsMap
     * @return
     * @author hushengmeng
     */
    @Override
    public Integer selectCount(Map<String, Object> paramsMap){
        return (Integer) this.getSqlSessionQueryTemplate().selectOne("PullBookMapper.pageCount", paramsMap);
    }

    /**
     *
     * @Title: selectByCopyrightBookId
     * @Description: 通过供应商图书ID批量获取拉取图书信息
     * @param copyrightBookIds
     * @return
     * @author hushengmeng
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PullBook> selectByCopyrightBookId(List<String> copyrightBookIds){
        return (List<PullBook>) this.getSqlSessionQueryTemplate().selectList("PullBookMapper.selectByCopyrightBookId", copyrightBookIds);
    }
}
