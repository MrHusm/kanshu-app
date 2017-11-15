package com.yxsd.kanshu.product.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/14.
 */
public class BookExpand implements Serializable{

    private static final long serialVersionUID = 7364367664356766260L;

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
     * 点击量
     */
    private Long clickNum;

    /**
     * 销售量
     */
    private Long saleNum;

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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getClickNum() {
        return clickNum;
    }

    public void setClickNum(Long clickNum) {
        this.clickNum = clickNum;
    }

    public Long getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Long saleNum) {
        this.saleNum = saleNum;
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
