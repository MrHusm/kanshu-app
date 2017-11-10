package com.yxsd.kanshu.ucenter.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hushengmeng
 * @date 2017/8/1.
 */
public class UserAccountLog implements Serializable {

    private static final long serialVersionUID = 3384663496721317312L;

    private Long accountLogId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 花费金额
     */
    private Integer unitMoney;


    /**
     * 花费虚拟币
     */
    private Integer unitVirtual;

    /**
     * 消费类型 type>0 收入  type<0 消费
     * 1：支付宝充值 2：微信充值 11：客服赠送 12：签到赠送 -1:单章购买 -2：批量购买 -3：全本购买 -4：VIP消费
     */
    private Integer type;

    /**
     * 消费产品id
     */
    private String productId;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUnitMoney() {
        return unitMoney;
    }

    public void setUnitMoney(Integer unitMoney) {
        this.unitMoney = unitMoney;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
