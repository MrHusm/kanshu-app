package com.yxsd.kanshu.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/8/12.
 */
public class Volume implements Serializable{

    private static final long serialVersionUID = -1096775560767223779L;

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

    /**
     * 卷描述
     */
    private String desc;

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
     * 在架状态 0：下架 1：在架
     */
    private Integer shelfStatus;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public Integer getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(Integer shelfStatus) {
        this.shelfStatus = shelfStatus;
    }
}
