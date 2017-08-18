package com.yxsd.kanshu.portal.model;

import java.util.Date;
import java.util.List;

/**
 * @author hushengmeng
 * @date 2017/7/7.
 */
public class HistoryToday {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 日期
     */
    private String day;

    private List<HistoryTodayImg> imgs;

    private Date createDate;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public List<HistoryTodayImg> getImgs() {
        return imgs;
    }

    public void setImgs(List<HistoryTodayImg> imgs) {
        this.imgs = imgs;
    }
}
