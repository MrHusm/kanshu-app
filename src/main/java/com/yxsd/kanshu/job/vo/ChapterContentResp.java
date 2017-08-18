package com.yxsd.kanshu.job.vo;

import java.io.Serializable;

/**
 * Created by liuhengsi on 2016-5-26.
 */
public class ChapterContentResp implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3651205333707256668L;

    private  Long cCID;
    private  Long cBID;
    private  String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
