package com.kanshu.job.vo;

import java.io.Serializable;

/**
 * Created by liuhengsi on 2016-5-25.
 */
public class ChapterInfoResp implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3651205333707256668L;

    private  Long cCID;//章号
    private  Long cBID;//书号
    private  Long cVID;//卷号
    private  Long chaptersort;//序号
    private String chaptertitle;//章节名
    private Integer originalwords;//计费字数
    private Integer vipflag;//VIP类型: -1:非vip  1:vip
    private Integer amount;//价格
    private String updatetime;//更新时间
    private String content_md5;//内容MD5


    public Long getChaptersort() {
        return chaptersort;
    }

    public void setChaptersort(Long chaptersort) {
        this.chaptersort = chaptersort;
    }

    public String getChaptertitle() {
        return chaptertitle;
    }

    public void setChaptertitle(String chaptertitle) {
        this.chaptertitle = chaptertitle;
    }

    public Integer getOriginalwords() {
        return originalwords;
    }

    public void setOriginalwords(Integer originalwords) {
        this.originalwords = originalwords;
    }

    public Integer getVipflag() {
        return vipflag;
    }

    public void setVipflag(Integer vipflag) {
        this.vipflag = vipflag;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getContent_md5() {
        return content_md5;
    }

    public void setContent_md5(String content_md5) {
        this.content_md5 = content_md5;
    }

    public Long getcCID() {
        return cCID;
    }

    public void setcCID(Long cCID) {
        this.cCID = cCID;
    }

    public Long getcBID() {
        return cBID;
    }

    public void setcBID(Long cBID) {
        this.cBID = cBID;
    }

    public Long getcVID() {
        return cVID;
    }

    public void setcVID(Long cVID) {
        this.cVID = cVID;
    }
}
