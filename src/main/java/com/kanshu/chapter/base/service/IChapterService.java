package com.kanshu.chapter.base.service;

import com.kanshu.chapter.base.service.IBaseService;
import com.kanshu.kanshu.product.model.Chapter;

import java.util.List;

/**
 * Created by lenovo on 2017/8/7.
 */
public interface IChapterService extends IBaseService<Chapter,Long> {
    /**
     * 根据图书id获取章节
     * @param bookId
     * @param num 章节表序号
     * @return
     */
    public List<Chapter> getChaptersByBookId(Long bookId,Integer num);

    /**
     * 获取章节信息
     * @param chapterId
     * @param type 1:带章节内容 其他：不带章节内容
     * @param num 章节表序号
     * @return
     */
    public Chapter getChapterById(Long chapterId,Integer type,Integer num);

}
