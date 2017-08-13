package com.kanshu.portal.service;

import com.kanshu.base.service.IBaseService;
import com.kanshu.portal.model.DriveBook;

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
     * @param type
     * @param bookId
     * @return
     */
    public DriveBook getDriveBookByCondition(Integer type,Long bookId);
}
