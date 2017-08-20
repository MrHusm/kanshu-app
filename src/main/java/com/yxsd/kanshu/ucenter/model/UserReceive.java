package com.yxsd.kanshu.ucenter.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/20.
 */
public class UserReceive implements Serializable{
    private Long id;

    private Long userId;

    /**
     * 新手礼包vip领取状态 0:未领取 1：已领取
     */
    private Integer vipStatus;

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
}
