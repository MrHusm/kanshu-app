package com.yxsd.kanshu.portal.model;

import com.yxsd.kanshu.product.model.Book;

import java.io.Serializable;
import java.util.Date;

public class DriveBook implements Serializable{

    private static final long serialVersionUID = -6853995961040030538L;

    private Long id;

    /**
     * 图书id
     */
    private Long bookId;


    /**
     * 类型 1：首页驱动 2：首页男生最爱 3：首页女生频道
     * 4：首页二次元 5：大家都在搜索 6：书库全站畅销
     * 7：书库完结精选 8：书库重磅新书 9：免费 10：书籍相关图书
     * 11:限章免费
     */
    private Integer type;

    /**
     * 排序分数
     */
    private Integer score;

    /***
     * 1:上线 0：未上线
     */
    private Integer status;

    /**
     * 1:手工配置 0：网站抓取
     */
    private Integer manType;

    /**
     * 限章免费图书章节数
     */
    private Integer num;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;
    
    
    private Book book;

    public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getManType() {
        return manType;
    }

    public void setManType(Integer manType) {
        this.manType = manType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}