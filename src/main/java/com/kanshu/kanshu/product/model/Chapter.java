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
    private String name;

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
     * 章节内容
     */
    private String content;

    /**
     * 1：上架 1：下架
     */
    private Integer status;

    /**
     * true:加锁 false：解锁
     */
    private boolean isLock = true;

    private Volume volume;

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

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setIsLock(boolean isLock) {
        this.isLock = isLock;
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
