package com.yxsd.kanshu.ucenter.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/19.
 */
public class VersionInfo implements Serializable{

    private static final long serialVersionUID = 3759547023441293337L;

    private Long id;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 渠道号
     */
    private Integer channel;

    /**
     * 1:强制升级 0:手动升级
     */
    private Integer type;

    /**
     * 更新文案标题
     */
    private String title;

    /**
     * 更新文案
     */
    private String content;

    /**
     * 更新包下载地址
     */
    private String url;

    private Date updateDate;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
