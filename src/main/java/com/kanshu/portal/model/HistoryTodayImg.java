package com.kanshu.portal.model;

import java.util.Date;

/**
 * @author hushengmeng
 * @date 2017/7/7.
 */
public class HistoryTodayImg {

    private Long id;

    /**
     * history_today 主键
     */
    private Long historyId;

    /**
     * 图片标题
     */
    private String title;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 排序
     */
    private Integer index;

    private Date createDate;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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
