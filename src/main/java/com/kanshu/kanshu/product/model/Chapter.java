package com.kanshu.kanshu.product.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/12.
 */
public class Chapter implements Serializable {

    private Long chapterId;

    /**
     * 卷id
     */
    private Long volumeId;

    /**
     * 图书id
     */
    private Long bookId;

    /**
     * 章节名称
     */
    private String title;

    /**
     * 排序
     */
    private Integer idx;

    /**
     * 章节价格
     */
    private Integer price;

    /**
     * 字数
     */
    private Integer wordCount;

    /**
     * 1：上架 1：下架
     */
    private Integer shelfStatus;

    /**
     * 是否免费 1：收费 0：免费
     */
    private Integer isFree;

    /**
     * 用于对章节内容的一致性进
     */
    private String contentMd5;

    /**
     * 版权方code
     */
    private String copyrightCode;

    /**
     * 版权方图书id
     */
    private Long copyrightBookId;

    /**
     * 版权方卷id
     */
    private Long copyrightVolumeId;

    /**
     * 版权方章节id
     */
    private Long copyrightChapterId;

    /**
     * true:加锁 false：解锁
     */
    private boolean isLock = true;

    private Date createDate;

    private Date updateDate;

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Long volumeId) {
        this.volumeId = volumeId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(Integer shelfStatus) {
        this.shelfStatus = shelfStatus;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public String getContentMd5() {
        return contentMd5;
    }

    public void setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
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

    public String getCopyrightCode() {
        return copyrightCode;
    }

    public void setCopyrightCode(String copyrightCode) {
        this.copyrightCode = copyrightCode;
    }

    public Long getCopyrightBookId() {
        return copyrightBookId;
    }

    public void setCopyrightBookId(Long copyrightBookId) {
        this.copyrightBookId = copyrightBookId;
    }

    public Long getCopyrightVolumeId() {
        return copyrightVolumeId;
    }

    public void setCopyrightVolumeId(Long copyrightVolumeId) {
        this.copyrightVolumeId = copyrightVolumeId;
    }

    public Long getCopyrightChapterId() {
        return copyrightChapterId;
    }

    public void setCopyrightChapterId(Long copyrightChapterId) {
        this.copyrightChapterId = copyrightChapterId;
    }
}
