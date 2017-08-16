package com.kanshu.kanshu.job.service;

import com.kanshu.kanshu.base.service.IBaseService;
import com.kanshu.kanshu.job.model.PullBook;

/**
 * Created by lenovo on 2017/8/6.
 */
public interface IPullBookService extends IBaseService<PullBook,Long> {

    /**
     * 保存或修改拉取图书的信息
     * @param copyright
     * @param cbid
     * @param pullStatus
     * @param pullFailureCause
     */
    public void saveOrUpdatePullBook(String copyright,String cbid,Integer pullStatus,String pullFailureCause);
}
