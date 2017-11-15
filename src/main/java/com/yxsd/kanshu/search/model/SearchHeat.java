package com.yxsd.kanshu.search.model;

import java.io.Serializable;
import java.util.Date;

public class SearchHeat implements Serializable{

	private static final long serialVersionUID = -6573264087917908197L;

	private String id;

	private String bookId;

	private Long heatValue;

	private String setSource;

	private Date updateTime;

	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Long getHeatValue() {
		return heatValue;
	}

	public void setHeatValue(Long heatValue) {
		this.heatValue = heatValue;
	}

	public String getSetSource() {
		return setSource;
	}

	public void setSetSource(String setSource) {
		this.setSource = setSource;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
