package com.yxsd.kanshu.portal.model;

import java.io.Serializable;
import java.util.Date;

public class SpecialImg implements Serializable {

	private static final long serialVersionUID = -7965956844449293487L;

	private Long specialImgId;

    /**
     * 跳转类型 1：图书详情页 2：链接 3：充值页
     */
	private Integer type;

    /**
     * 跳转目标
     */
    private String target;

    /**
     * 跳转页面标题
     */
    private String title;

    /**
     * 图片地址
     */
    private String imgUrl;

    private Long specialId;

    private Date createDate;

    private Date updateDate;

    public Long getSpecialImgId() {
        return specialImgId;
    }

    public void setSpecialImgId(Long specialImgId) {
        this.specialImgId = specialImgId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}