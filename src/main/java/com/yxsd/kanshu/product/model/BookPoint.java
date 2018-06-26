package com.yxsd.kanshu.product.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 图书计费点
 */
public class BookPoint implements Serializable{

    private static final long serialVersionUID = -3563047241367973278L;

    private Long id;

    /**
     * 图书id
     */
    private Long bookId;

    /**
     * 计费点
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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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