package com.kanshu.product.service;

import com.kanshu.base.service.IBaseService;
import com.kanshu.product.model.Chapter;

import java.util.List;

/**
 * Created by lenovo on 2017/8/7.
 */
public interface IChapterService extends IBaseService<Chapter,Long> {
    /**
     * 根据图书id获取章节
     * @param bookId
     * @return
     */
    public List<Chapter> getChaptersByBookId(Long bookId);

    /**
     * 获取包含章节内容的章节信息
     * @param chapterId
     * @return
     */
    public Chapter getChapterWithContentById(Long chapterId);
}
