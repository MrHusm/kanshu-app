package com.kanshu.product.model;


import java.util.Date;

public class Book {
    private Long bookId;

    /**
     * 书名
     */
    private String title;

    /**
     * 图书封面
     */
    private String img;

    /**
     * 作者名
     */
    private String author;

    /**
     * 笔名
     */
    private String authorPenname;

    /**
     * 简介
     */
    private String desc;

    /**
     * 在架状态 0：下架 1：在架
     */
    private Short shelfStatus;

    /**
     * 二级分类
     */
    private String categorySec;

    /**
     * 三级分类
     */
    private String categoryThr;

    /**
     * 字数
     */
    private Integer wordCount;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 是否支持全本购买 0：不支持 1：支持
     */
    private Short isSupportFullBuy;

    /**
     * 是否完结 0：连载 1：完结
     */
    private Short isFull;

    /**
     * 全本购买价格
     */
    private Integer price;

    private Date createDate;

    private Date updateDate;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorPenname() {
        return authorPenname;
    }

    public void setAuthorPenname(String authorPenname) {
        this.authorPenname = authorPenname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Short getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(Short shelfStatus) {
        this.shelfStatus = shelfStatus;
    }

    public String getCategorySec() {
        return categorySec;
    }

    public void setCategorySec(String categorySec) {
        this.categorySec = categorySec;
    }

    public String getCategoryThr() {
        return categoryThr;
    }

    public void setCategoryThr(String categoryThr) {
        this.categoryThr = categoryThr;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Short getIsSupportFullBuy() {
        return isSupportFullBuy;
    }

    public void setIsSupportFullBuy(Short isSupportFullBuy) {
        this.isSupportFullBuy = isSupportFullBuy;
    }

    public Short getIsFull() {
        return isFull;
    }

    public void setIsFull(Short isFull) {
        this.isFull = isFull;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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