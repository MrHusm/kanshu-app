package com.kanshu.ucenter.model;  public class UserUuid implements java.io.Serializable {
    public static final String TABLE_NAME="user_uuid";

    private Integer id;

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "id = " + this.id + "" ;
    }
}