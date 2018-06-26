package com.yxsd.kanshu.product.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 图书默认计费点
 */
public class BookChannelPoint implements Serializable{

    private static final long serialVersionUID = -8013954689707938823L;

    private Long id;

    /**
     * 渠道列表
     */
    private String channels;

    /**
     * 默认开始计费的章节位置
     */
    private Integer num;

    private Date createDate;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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