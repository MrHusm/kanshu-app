package com.kanshu.pay.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hushengmeng
 * @date 2017/8/9.
 */
public class RechargeItem implements Serializable {

    private static final long serialVersionUID = -1665208392379794212L;


    private Long rechargeItemId;

    /**
     * 价格（元）
     */
    private Double price;

    /**
     * 充值金额(钻)
     */
    private Integer money;

    /**
     * 赠送金额(钻)
     */
    private Integer virtual;

    private Date createDate;

    private Date updateDate;

    public Long getRechargeItemId() {
        return rechargeItemId;
    }

    public void setRechargeItemId(Long rechargeItemId) {
        this.rechargeItemId = rechargeItemId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getVirtual() {
        return virtual;
    }

    public void setVirtual(Integer virtual) {
        this.virtual = virtual;
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
