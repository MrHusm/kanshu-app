package com.yxsd.kanshu.ucenter.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hushengmeng
 * @date 2017/8/8.
 */
public class UserVip implements Serializable {

    private static final long serialVersionUID = 4503398509288649748L;

    private Long userVipId;

    private Long userId;

    private Integer channel;

    private Date endDate;

    private Date createDate;

    private Date updateDate;

    public Long getUserVipId() {
        return userVipId;
    }

    public void setUserVipId(Long userVipId) {
        this.userVipId = userVipId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
