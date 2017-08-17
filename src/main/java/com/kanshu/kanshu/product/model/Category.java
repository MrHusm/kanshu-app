package com.kanshu.kanshu.product.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/14.
 */
public class Category implements Serializable{


    private Long categoryId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 父ID
     */
    private Long pid;

    /**
     * 版权方分类id
     */
    private Long copyrightCategoryId;

    private Date createDate;

    private Date updateDate;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    public Long getCopyrightCategoryId() {
        return copyrightCategoryId;
    }

    public void setCopyrightCategoryId(Long copyrightCategoryId) {
        this.copyrightCategoryId = copyrightCategoryId;
    }
}
