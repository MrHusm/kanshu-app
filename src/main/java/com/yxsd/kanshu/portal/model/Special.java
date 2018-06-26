package com.yxsd.kanshu.portal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Special implements Serializable {

	private static final long serialVersionUID = 2957082258966605982L;

	private Long specialId;

    /**
     * 专题类型 1:轮播图 2：长图 3：小图
     */
    private Short type;

    /**
     * 专题名称
     */
    private String name;

    /**
     * 专题内容
     */
    private String content;

    /**
     * 专题位置
     */
    private Integer index;
    
    /**
     * 专题图片
     */
    private List<SpecialImg> specialImgs;

    private Date createDate;

    private Date updateDate;

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<SpecialImg> getSpecialImgs() {
        return specialImgs;
    }

    public void setSpecialImgs(List<SpecialImg> specialImgs) {
        this.specialImgs = specialImgs;
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