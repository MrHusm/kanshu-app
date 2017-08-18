package com.yxsd.kanshu.product.model;


import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable{
    private Long bookId;

    /**
     * 书名
     */
    private String title;

    /**
     * 字数
     */
    private Integer wordCount;

    /**
     * 图书封面
     */
    private String coverUrl;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 笔名
     */
    private String authorPenname;

    /**
     * 简介
     */
    private String intro;

    /**
     * 在架状态 0：下架 1：在架
     */
    private Integer shelfStatus;

    /**
     * 二级分类
     */
    private Long categorySecId;

    /**
     * 二级分类名称
     */
    private String categorySecName;

    /**
     * 三级分类
     */
    private Long categoryThrId;

    /**
     * 三级分类名称
     */
    private String categoryThrName;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 计费方式 1:按章 2:按本
     */
    private Integer chargeType;

    /**
     * 是否完结 0：连载 1：完结
     */
    private Integer isFull;

    /**
     * 全本购买价格
     */
    private Integer price;

    /**
     * 是否免费 1：收费 0：免费
     */
    private Integer isFree;

    /**
     * 标签
     */
    private String tag;

    /**
     * 最近一个章节的更新时间
     */
    private Date lastChapterUpdateDate;

    /**
     * 版权方编码
     */
    private String copyrightCode;

    /**
     * 版权方
     */
    private String copyright;

    /**
     * 版权方图书id
     */
    private Long copyrightBookId;

    /**
     * 类型 1：原创 2 出版物
     */
    private Integer type;

    /**
     * 千字价格
     */
    private Integer unitPrice;

    /**
     * 书文件格式 1:txt 2:腾讯精排简版 3:腾讯精排完整 4: 以上三种都支持
     */
    private Integer fileFormat;

    /**
     * 是否允许包月 0：不允许 1：允许
     */
    private Integer isMonthly;

    /**
     * 包月开始时间
     */
    private Date monthlyStartDate;

    /**
     * 包月结束时间
     */
    private Date monthlyEndDate;

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

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorPenname() {
        return authorPenname;
    }

    public void setAuthorPenname(String authorPenname) {
        this.authorPenname = authorPenname;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(Integer shelfStatus) {
        this.shelfStatus = shelfStatus;
    }

    public Long getCategorySecId() {
        return categorySecId;
    }

    public void setCategorySecId(Long categorySecId) {
        this.categorySecId = categorySecId;
    }

    public Long getCategoryThrId() {
        return categoryThrId;
    }

    public void setCategoryThrId(Long categoryThrId) {
        this.categoryThrId = categoryThrId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getIsFull() {
        return isFull;
    }

    public void setIsFull(Integer isFull) {
        this.isFull = isFull;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getLastChapterUpdateDate() {
        return lastChapterUpdateDate;
    }

    public void setLastChapterUpdateDate(Date lastChapterUpdateDate) {
        this.lastChapterUpdateDate = lastChapterUpdateDate;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Long getCopyrightBookId() {
        return copyrightBookId;
    }

    public void setCopyrightBookId(Long copyrightBookId) {
        this.copyrightBookId = copyrightBookId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(Integer fileFormat) {
        this.fileFormat = fileFormat;
    }

    public Integer getIsMonthly() {
        return isMonthly;
    }

    public void setIsMonthly(Integer isMonthly) {
        this.isMonthly = isMonthly;
    }

    public Date getMonthlyStartDate() {
        return monthlyStartDate;
    }

    public void setMonthlyStartDate(Date monthlyStartDate) {
        this.monthlyStartDate = monthlyStartDate;
    }

    public Date getMonthlyEndDate() {
        return monthlyEndDate;
    }

    public void setMonthlyEndDate(Date monthlyEndDate) {
        this.monthlyEndDate = monthlyEndDate;
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

    public String getCopyrightCode() {
        return copyrightCode;
    }

    public void setCopyrightCode(String copyrightCode) {
        this.copyrightCode = copyrightCode;
    }

    public String getCategorySecName() {
        return categorySecName;
    }

    public void setCategorySecName(String categorySecName) {
        this.categorySecName = categorySecName;
    }

    public String getCategoryThrName() {
        return categoryThrName;
    }

    public void setCategoryThrName(String categoryThrName) {
        this.categoryThrName = categoryThrName;
    }
}