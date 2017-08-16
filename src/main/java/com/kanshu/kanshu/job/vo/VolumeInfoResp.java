package com.kanshu.kanshu.job.vo;

import java.io.Serializable;

/**
 * Created by liuhengsi on 2016-5-25.
 */
public class VolumeInfoResp implements Serializable {

    private static final long serialVersionUID = -3651205333707256668L;
    private Long cVID;  //卷ID，主键
    private Long cBID;  //书ID
    private  Integer volumesort; //卷序号
    private  String volumename; //卷名
    private String volumedesc;      //卷描述
    private int count;// 卷的数量

    public Long getcVID() {
        return cVID;
    }

    public void setcVID(Long cVID) {
        this.cVID = cVID;
    }

    public Long getcBID() {
        return cBID;
    }

    public void setcBID(Long cBID) {
        this.cBID = cBID;
    }

    public Integer getVolumesort() {
        return volumesort;
    }

    public void setVolumesort(Integer volumesort) {
        this.volumesort = volumesort;
    }

    public String getVolumedesc() {
        return volumedesc;
    }

    public void setVolumedesc(String volumedesc) {
        this.volumedesc = volumedesc;
    }

    public String getVolumename() {
        return volumename;
    }

    public void setVolumename(String volumename) {
        this.volumename = volumename;
    }

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
