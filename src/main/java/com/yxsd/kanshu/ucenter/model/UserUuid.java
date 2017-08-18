package com.yxsd.kanshu.ucenter.model;

import java.util.Date;

public class UserUuid implements java.io.Serializable {
    public static final String TABLE_NAME="user_uuid";

    private Integer id;

    private Date createDate;

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}