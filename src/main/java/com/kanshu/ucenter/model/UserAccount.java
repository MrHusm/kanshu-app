package com.kanshu.ucenter.model;

import java.util.Date;

/**
 * @author hushengmeng
 * @date 2017/8/1.
 */
public class UserAccount {

    private Long accountId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 账户金额
     */
    private Long money;

    /**
     * 虚拟币金额
     */
    private Long virtualMoney;

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

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getVirtualMoney() {
        return virtualMoney;
    }

    public void setVirtualMoney(Long virtualMoney) {
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
