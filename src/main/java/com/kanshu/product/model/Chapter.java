package com.kanshu.product.model;

import java.io.Serializable;

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
     * 排序
     */
    private Integer idx;

    /**
     * true:加锁 false：解锁
     */
    private boolean isLock = true;

    private Volume volume;

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
}
