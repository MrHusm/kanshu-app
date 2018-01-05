package com.yxsd.kanshu.portal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Special implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2957082258966605982L;

	private Long specialId;

    /**
     * 专题类型
     */
    private Short specialType;

    /**
     * 专题名称
     */
    private String specialName;

    /**
     * 专题内容
     */
    private String specialContent;

    /**
     * 专题位置
     */
    private Short specialIndex;
    
    /**
     * 专题图片
     */
    private List<SpecialImg> specialImgs;

    public List<SpecialImg> getSpecialImgs() {
		return specialImgs;
	}

	public void setSpecialImgs(List<SpecialImg> specialImgs) {
		this.specialImgs = specialImgs;
	}

	private Date createDate;

    private Date updateDate;

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
    }

    public Short getSpecialType() {
        return specialType;
    }

    public void setSpecialType(Short specialType) {
        this.specialType = specialType;
    }

    public String getSpecialName() {
        return specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public String getSpecialContent() {
        return specialContent;
    }

    public void setSpecialContent(String specialContent) {
        this.specialContent = specialContent;
    }

    public Short getSpecialIndex() {
        return specialIndex;
    }

    public void setSpecialIndex(Short specialIndex) {
        this.specialIndex = specialIndex;
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