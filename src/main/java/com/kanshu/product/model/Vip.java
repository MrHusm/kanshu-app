package com.kanshu.product.model;

import java.util.Date;

/**
 * Created by lenovo on 2017/8/7.
 */
public class Vip {
    private Long vipId;

    /**
     * vip时长
     */
    private Integer days;

    /**
     * vip原价
     */
    private Integer price;

    /**
     * vip折扣价格
     */
    private Integer discountPrice;

    /**
     * 赠送金额
     */
    private Integer virtualMoney;

    private Date createDate;

    private Date updateDate;

    public Long getVipId() {
        return vipId;
    }

    public void setVipId(Long vipId) {
        this.vipId = vipId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getVirtualMoney() {
        return virtualMoney;
    }

    public void setVirtualMoney(Integer virtualMoney) {
        this.virtualMoney = virtualMoney;
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
