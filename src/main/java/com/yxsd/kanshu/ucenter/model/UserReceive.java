package com.yxsd.kanshu.ucenter.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/20.
 */
public class UserReceive implements Serializable {

    private static final long serialVersionUID = 2610573846866395815L;

    private Long id;

    private Long userId;

    /**
     * 新手礼包vip领取状态 0:未领取 1：已领取
     */
    private Integer vipStatus;

    /**
     * 手机号绑定状态 0：未绑定 1：已绑定 2：已取消绑定
     */
    private Integer telStatus;

    /**
     * QQ号绑定状态 0：未绑定 1：已绑定 2：已取消绑定
     */
    private Integer qqStatus;

    /**
     * 微信绑定状态 0：未绑定 1：已绑定 2：已取消绑定
     */
    private Integer weixinStatus;

    /**
     * 微博绑定状态 0：未绑定 1：已绑定 2：已取消绑定
     */
    private Integer weiboStatus;

    /**
     * 取消领取新手礼包vip的次数
     */
    private Integer cancelVipTimes;

    private Date updateDate;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(Integer vipStatus) {
        this.vipStatus = vipStatus;
    }

    public Integer getCancelVipTimes() {
        return cancelVipTimes;
    }

    public void setCancelVipTimes(Integer cancelVipTimes) {
        this.cancelVipTimes = cancelVipTimes;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getTelStatus() {
        return telStatus;
    }

    public void setTelStatus(Integer telStatus) {
        this.telStatus = telStatus;
    }

    public Integer getQqStatus() {
        return qqStatus;
    }

    public void setQqStatus(Integer qqStatus) {
        this.qqStatus = qqStatus;
    }

    public Integer getWeixinStatus() {
        return weixinStatus;
    }

    public void setWeixinStatus(Integer weixinStatus) {
        this.weixinStatus = weixinStatus;
    }

    public Integer getWeiboStatus() {
        return weiboStatus;
    }

    public void setWeiboStatus(Integer weiboStatus) {
        this.weiboStatus = weiboStatus;
    }
}
