package com.kanshu.pay.model;


import java.util.Date;

/**
 * Created by lenovo on 2017/8/6.
 */
public class AlipayOrder {
    private Long alipayId;

    private Long userId;

    /**
     * 订单号
     */
    private String WIDoutTradeNo;

    /**
     * 商品名称
     */
    private String WIDsubject;

    /**
     * 付款金额
     */
    private Double WIDtotalAmount;

    /**
     * 商品描述
     */
    private String WIDbody;

    /**
     * 渠道号
     */
    private Integer channel;

    private Date createDate;

    private Date updateDate;

    public Long getAlipayId() {
        return alipayId;
    }

    public void setAlipayId(Long alipayId) {
        this.alipayId = alipayId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWIDoutTradeNo() {
        return WIDoutTradeNo;
    }

    public void setWIDoutTradeNo(String WIDoutTradeNo) {
        this.WIDoutTradeNo = WIDoutTradeNo;
    }

    public String getWIDsubject() {
        return WIDsubject;
    }

    public void setWIDsubject(String WIDsubject) {
        this.WIDsubject = WIDsubject;
    }

    public Double getWIDtotalAmount() {
        return WIDtotalAmount;
    }

    public void setWIDtotalAmount(Double WIDtotalAmount) {
        this.WIDtotalAmount = WIDtotalAmount;
    }

    public String getWIDbody() {
        return WIDbody;
    }

    public void setWIDbody(String WIDbody) {
        this.WIDbody = WIDbody;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
