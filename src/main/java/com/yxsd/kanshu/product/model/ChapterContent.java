package com.yxsd.kanshu.product.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/15.
 */
public class ChapterContent implements Serializable{

    /**
     * 章节id
     */
    private Long chapterId;

    /**
     * 图书id
     */
    private Long bookId;

    /**
     * 内容
     */
    private String content;

    private Date updateDate;

    private Date createDate;

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
