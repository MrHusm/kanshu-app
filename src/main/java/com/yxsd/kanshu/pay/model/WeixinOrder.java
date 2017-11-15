package com.yxsd.kanshu.pay.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/10/5.
 */
public class WeixinOrder implements Serializable {

    private static final long serialVersionUID = -5447498236487725829L;

    private Long wxOrderId;

    private Long userId;

    /**
     * 购买的产品ID
     */
    private Long productId;

    /**
     * 渠道号
     */
    private Integer channel;

    /**
     * 类型 ：2：微信充值 -4：VIP消费
     */
    private Integer type;

    /**
     * 备注
     */
    private String comment;

    /**
     * 随机字符串
     */
    private String nonce_str;

    /**
     * 签名
     */
    private String sign;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 总金额
     */
    private Integer total_fee;

    /**
     * 终端IP
     */
    private String spbill_create_ip;

    /**
     * 交易类型
     */
    private String trade_type;

    /**
     * 场景信息
     */
    private String scene_info;

    private Date createDate;

    private Date updateDate;

    public Long getWxOrderId() {
        return wxOrderId;
    }

    public void setWxOrderId(Long wxOrderId) {
        this.wxOrderId = wxOrderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
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

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getScene_info() {
        return scene_info;
    }

    public void setScene_info(String scene_info) {
        this.scene_info = scene_info;
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
