package com.yxsd.kanshu.search.model;

import java.io.Serializable;
import java.util.Date;

public class SearchWord implements Serializable{

    private static final long serialVersionUID = -4351044090606661990L;

    private Long id;

    private String searchWords;

    private String searchHotWords;

    private Date createDate;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchWords() {
        return searchWords;
    }

    public void setSearchWords(String searchWords) {
        this.searchWords = searchWords;
    }

    public String getSearchHotWords() {
        return searchHotWords;
    }

    public void setSearchHotWords(String searchHotWords) {
        this.searchHotWords = searchHotWords;
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