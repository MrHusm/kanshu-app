package com.kanshu.ucenter.model;

import java.util.Date;

/**
 * @author hushengmeng
 * @date 2017/8/1.
 */
public class UserAccountLog {

    private Long accountLogId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单ID
     */
    private Long orderId;

    private Integer beforeMoney;

    private Integer curMoney;

    /**
     * 花费金额
     */
    private Integer unitMoney;

    private Integer beforeVirtual;

    private Integer curVirtual;

    /**
     * 花费虚拟币
     */
    private Integer unitVirtual;

    /**
     * 消费类型 type>0 收入  type<0 消费
     */
    private Integer type;

    /**
     * 备注
     */
    private String comment;

    private Integer channel;

    private Date createDate;

    /**
     * //1：查询收入  2：查询书籍消费  3：查询其他消费（例如:购买VIP） 4:查询所有消费
     */
    private Integer findType;

    public Long getAccountLogId() {
        return accountLogId;
    }

    public void setAccountLogId(Long accountLogId) {
        this.accountLogId = accountLogId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(Integer beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public Integer getCurMoney() {
        return curMoney;
    }

    public void setCurMoney(Integer curMoney) {
        this.curMoney = curMoney;
    }

    public Integer getUnitMoney() {
        return unitMoney;
    }

    public void setUnitMoney(Integer unitMoney) {
        this.unitMoney = unitMoney;
    }

    public Integer getBeforeVirtual() {
        return beforeVirtual;
    }

    public void setBeforeVirtual(Integer beforeVirtual) {
        this.beforeVirtual = beforeVirtual;
    }

    public Integer getCurVirtual() {
        return curVirtual;
    }

    public void setCurVirtual(Integer curVirtual) {
        this.curVirtual = curVirtual;
    }

    public Integer getUnitVirtual() {
        return unitVirtual;
    }

    public void setUnitVirtual(Integer unitVirtual) {
        this.unitVirtual = unitVirtual;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getFindType() {
        return findType;
    }

    public void setFindType(Integer findType) {
        this.findType = findType;
    }
}
