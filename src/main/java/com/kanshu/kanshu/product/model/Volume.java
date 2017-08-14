package com.kanshu.kanshu.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/8/12.
 */
public class Volume implements Serializable{

    private Long volumeId;

    /**
     * 卷名称
     */
    private String name;

    /**
     * 书籍ID
     */
    private Long bookId;

    /**
     * 排序
     */
    private Integer idx;

    private List<Chapter> chapters;

    private Date createDate;

    private Date updateDate;

    public Long getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Long volumeId) {
        this.volumeId = volumeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
