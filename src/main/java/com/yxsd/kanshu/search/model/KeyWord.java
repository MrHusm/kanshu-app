package com.yxsd.kanshu.search.model;

import java.io.Serializable;
import java.util.Date;

public class KeyWord implements Serializable{

	private static final long serialVersionUID = -6332305497734131105L;

	private String id;

	private String word;

	private int score;

	private String setSource;

	private Date updateTime;

	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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
