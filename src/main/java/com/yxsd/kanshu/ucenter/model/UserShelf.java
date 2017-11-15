package com.yxsd.kanshu.ucenter.model;

import com.yxsd.kanshu.product.model.Book;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/19.
 */
public class UserShelf implements Serializable{

    private static final long serialVersionUID = -6449455004005019683L;

    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 图书id
     */
    private Long bookId;

    /**
     * 类型 0：浏览历史 1：加入书架
     */
    private Integer type;

    /**
     * 自动购买标识 0：不自动购买 1：自动购买
     */
    private Integer autoBuy;

    /**
     * 当前阅读章节id
     */
    private Long chapterId;
    /**
     * 当前阅读章节序号
     */
    private Integer idx;

    /**
     * 最大章节id
     */
    private Long maxChapterId;

    /**
     * 最大章节序号
     */
    private Integer maxChapterIdx;

    /**
     * 更新的章节数
     */
    private Integer updatedChapterCount;

    private Book book;

    private Date updateDate;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getMaxChapterIdx() {
        return maxChapterIdx;
    }

    public void setMaxChapterIdx(Integer maxChapterIdx) {
        this.maxChapterIdx = maxChapterIdx;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getUpdatedChapterCount() {
        return updatedChapterCount;
    }

    public void setUpdatedChapterCount(Integer updatedChapterCount) {
        this.updatedChapterCount = updatedChapterCount;
    }

    public Long getMaxChapterId() {
        return maxChapterId;
    }

    public void setMaxChapterId(Long maxChapterId) {
        this.maxChapterId = maxChapterId;
    }

    public Integer getAutoBuy() {
        return autoBuy;
    }

    public void setAutoBuy(Integer autoBuy) {
        this.autoBuy = autoBuy;
    }
}
