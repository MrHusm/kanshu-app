package com.yxsd.kanshu.portal.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.portal.model.DriveBook;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IDriveBookService extends IBaseService<DriveBook,Long> {

    /**
     * 根据不同类型获取图书驱动
     * @param type 类型 1：限免榜 2：搜索榜 3：畅销榜
     * @return
     */
    public List<DriveBook> getDriveBooks(Integer type);

    /**
     * 获取不同驱动类型指定图书
     * @param type 1：限免榜 2：搜索榜 3：畅销榜
     * @param bookId
     * @return
     */
    public DriveBook getDriveBookByCondition(Integer type,Long bookId);

    /**
     *
     * Description: 分页查询
     * @Version1.0
     * @param params
     * @param query
     * @return
     */
    public PageFinder<T> findPage(Object params, Query query);
}
