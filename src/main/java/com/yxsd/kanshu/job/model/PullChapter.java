package com.yxsd.kanshu.job.model;

import com.yxsd.kanshu.base.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/16.
 */
public class PullChapter implements Serializable{

    private Long id;

    /**
     * 版权方code
     */
    private String copyrightCode;

    /**
     * 版权方图书id
     */
    private Long copyrightBookId;

    /**
     * 版权方卷id
     */
    private Long copyrightVolumeId;

    /**
     * 版权方章节id
     */
    private Long copyrightChapterId;

    /**
     * 拉取状态 1：拉取成功 0：拉取失败
     */
    private Integer pullStatus;

    /**
     * 失败原因
     */
    private String pullFailureCause;

    private Date createDate;

    private Date updateDate;

    public PullChapter() {
        super();
    }

    public PullChapter(String copyrightCode, String copyrightBookId,
                                 String copyrightVolumeId, String copyrightChapterId,
                                 Integer pullStatus, String pullFailureCause) {
        super();
        this.copyrightCode = copyrightCode;
        this.copyrightBookId = Long.parseLong(copyrightBookId);
        this.copyrightVolumeId = Long.parseLong(copyrightVolumeId);
        this.copyrightChapterId = Long.parseLong(copyrightChapterId);
        this.pullStatus = pullStatus;
        this.pullFailureCause = pullFailureCause;
        this.createDate = DateUtil.getCurrentDateTime();
        this.updateDate = DateUtil.getCurrentDateTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCopyrightCode() {
        return copyrightCode;
    }

    public void setCopyrightCode(String copyrightCode) {
        this.copyrightCode = copyrightCode;
    }

    public Long getCopyrightBookId() {
        return copyrightBookId;
    }

    public void setCopyrightBookId(Long copyrightBookId) {
        this.copyrightBookId = copyrightBookId;
    }

    public Integer getPullStatus() {
        return pullStatus;
    }

    public void setPullStatus(Integer pullStatus) {
        this.pullStatus = pullStatus;
    }

    public String getPullFailureCause() {
        return pullFailureCause;
    }

    public void setPullFailureCause(String pullFailureCause) {
        this.pullFailureCause = pullFailureCause;
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

    public Long getCopyrightVolumeId() {
        return copyrightVolumeId;
    }

    public void setCopyrightVolumeId(Long copyrightVolumeId) {
        this.copyrightVolumeId = copyrightVolumeId;
    }

    public Long getCopyrightChapterId() {
        return copyrightChapterId;
    }

    public void setCopyrightChapterId(Long copyrightChapterId) {
        this.copyrightChapterId = copyrightChapterId;
    }
}
