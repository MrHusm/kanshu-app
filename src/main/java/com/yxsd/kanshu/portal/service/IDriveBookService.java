package com.yxsd.kanshu.portal.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.portal.model.DriveBook;

import java.util.List;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IDriveBookService extends IBaseService<DriveBook,Long> {

    /**
     * 根据不同类型获取图书驱动
     * @param type 类型 1：首页驱动 2：首页男生最爱 3：首页女生频道
     * 4：首页二次元 5：大家都在搜索 6：书库全站畅销
     * 7：书库完结精选 8：书库重磅新书 9：限免 10：书籍相关图书
     * @return
     */
    public List<DriveBook> getDriveBooks(Integer type);

    /**
     * 获取不同驱动类型指定图书
     * @param type 类型 1：首页驱动 2：首页男生最爱 3：首页女生频道
     * 4：首页二次元 5：大家都在搜索 6：书库全站畅销
     * 7：书库完结精选 8：书库重磅新书 9：限免 10：书籍相关图书
     * @param bookId
     * @return
     */
    public DriveBook getDriveBookByCondition(Integer type,Long bookId);

    /**
     *
     * Description: 分页查询
     * @Version1.0
     * @param type 类型 1：首页驱动 2：首页男生最爱 3：首页女生频道
     * 4：首页二次元 5：大家都在搜索 6：书库全站畅销
     * 7：书库完结精选 8：书库重磅新书 9：限免 10：书籍相关图书
     * @param query
     * @return
     */
    public PageFinder<DriveBook> findPageWithCondition(Integer type, Query query);
}
