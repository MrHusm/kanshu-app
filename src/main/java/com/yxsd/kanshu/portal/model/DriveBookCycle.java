package com.yxsd.kanshu.portal.model;

import java.io.Serializable;
import java.util.Date;

public class DriveBookCycle implements Serializable{

    private static final long serialVersionUID = -6489753739209220504L;

    private Long id;

    /**
     * 图书id
     */
    private Long bookId;

    /**
     * 图书名称
     */
    private String bookName;


    /**
     * 类型 1：首页驱动 2：首页男生最爱 3：首页女生频道
     * 4：首页二次元 5：大家都在搜索 6：书库全站畅销
     * 7：书库完结精选 8：书库重磅新书 9：免费 10：书籍相关图书
     */
    private Integer type;


    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 排序分数
     */
    private Integer score;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}