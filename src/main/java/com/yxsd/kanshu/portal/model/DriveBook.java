package com.yxsd.kanshu.portal.model;

import com.yxsd.kanshu.product.model.Book;

import java.util.Date;

public class DriveBook {
    private Long id;

    /**
     * 图书id
     */
    private Long bookId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 类型 1：限免榜 2：搜索榜 3：畅销榜
     */
    private Integer type;

    /**
     * 排序分数
     */
    private Integer score;
    
    
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
}