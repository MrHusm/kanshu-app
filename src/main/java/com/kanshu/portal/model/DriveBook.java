package com.kanshu.portal.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/12.
 */
public class DriveBook implements Serializable{

    private Long id;

    /**
     * 图书id
     */
    private Long bookId;

    /**
     * 类型 1：限免榜 2：搜索榜 3：畅销榜
     */
    private Integer type;

    /**
     * 排序分数
     */
    private Integer score;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
