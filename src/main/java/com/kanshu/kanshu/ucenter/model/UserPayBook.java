package com.kanshu.kanshu.ucenter.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hushengmeng
 * @date 2017/8/1.
 */
public class UserPayBook implements Serializable {

    private static final long serialVersionUID = 7398183464112663077L;

    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 图书ID
     */
    private Long bookId;

    /**
     * 1：批量购买 2：全本购买
     */
    private Integer type;

    /**
     * 开始章节id
     */
    private Long startChapterId;

    /**
     * 开始章节序号
     */
    private Integer startChapterIdx;

    /**
     * 结束章节id
     */
    private Long endChapterId;

    /**
     * 结束章节序号
     */
    private Integer endChapterIdx;

    private Date createDate;

    private Date updateDate;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getStartChapterId() {
        return startChapterId;
    }

    public void setStartChapterId(Long startChapterId) {
        this.startChapterId = startChapterId;
    }

    public Integer getStartChapterIdx() {
        return startChapterIdx;
    }

    public void setStartChapterIdx(Integer startChapterIdx) {
        this.startChapterIdx = startChapterIdx;
    }

    public Long getEndChapterId() {
        return endChapterId;
    }

    public void setEndChapterId(Long endChapterId) {
        this.endChapterId = endChapterId;
    }

    public Integer getEndChapterIdx() {
        return endChapterIdx;
    }

    public void setEndChapterIdx(Integer endChapterIdx) {
        this.endChapterIdx = endChapterIdx;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
