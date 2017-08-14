package com.kanshu.kanshu.pay.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/6.
 */
public class PayBefore implements Serializable {

    private static final long serialVersionUID = -367764852964831963L;

    private Long id;

    private Long userId;

    private String param;

    /**
     * 回跳地址
     */
    private String returnUrl;

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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
