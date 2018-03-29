package com.yxsd.kanshu.product.model;

import java.io.Serializable;
import java.util.Date;

public class CategoryImg implements Serializable{

    private static final long serialVersionUID = 8012562754884019760L;

    private Long id;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 图片地址
     */
    private String imgUrl;

    private Date createDate;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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