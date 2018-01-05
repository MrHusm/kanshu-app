package com.yxsd.kanshu.portal.model;

import java.io.Serializable;
import java.util.Date;

public class SpecialImg implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7965956844449293487L;

	private Long specialImgId;

    /**
     * 图片地址
     */
    private String specialPic;

    /**
     * 跳转路径
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

    public String getSpecialPic() {
        return specialPic;
    }

    public void setSpecialPic(String specialPic) {
        this.specialPic = specialPic;
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
}