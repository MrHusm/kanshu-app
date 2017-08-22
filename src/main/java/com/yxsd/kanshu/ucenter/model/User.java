package com.yxsd.kanshu.ucenter.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 5758035800481847812L;

    private Long userId;
    /**
     * 姓名
     */
    private String name;

    /**
     * 别名
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别 1：男 0：女
     */
    private Integer sex;


    /**
     * 头像
     */
    private String logo;

    /**
     * 电话
     */
    private String tel;

    /**
     * 注册时渠道号
     */
    private Integer channel;

    /**
     * 当前渠道号
     */
    private Integer channelNow;

    /**
     * 邮箱
     */
    private String email;

    /**
     * imei->android_id->serialNunber ->UUID生成的
     */
    private String deviceSerialNo;


    /**
     * Android或IOS或H5
     */
    private String deviceType;

    /**
     * 是否是vip
     */
    private boolean isVip = false;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public Integer getChannelNow() {
        return channelNow;
    }

    public void setChannelNow(Integer channelNow) {
        this.channelNow = channelNow;
    }

    public String getDeviceSerialNo() {
        return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        this.deviceSerialNo = deviceSerialNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
