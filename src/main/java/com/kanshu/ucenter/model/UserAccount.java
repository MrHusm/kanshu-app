package com.kanshu.ucenter.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hushengmeng
 * @date 2017/8/1.
 */
public class UserAccount implements Serializable {

    private static final long serialVersionUID = -5174579865745867846L;

    private Long accountId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 账户金额
     */
    private Integer money;

    /**
     * 虚拟币金额
     */
    private Integer virtualMoney;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
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
