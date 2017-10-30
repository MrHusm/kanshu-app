package com.yxsd.kanshu.ucenter.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/19.
 */
public class UserQq implements Serializable{
    private Long id;

    private Long userId;

    /**
     * QQ用户唯一标识
     */
    private String openid;

    /**
     * 用户在QQ空间的昵称
     */
    private String nickname;

    /**
     * 大小为30×30像素的QQ空间头像URL
     */
    private String figureurl;

    /**
     * 大小为50×50像素的QQ空间头像URL
     */
    private String figureurl1;

    /**
     * 大小为100×100像素的QQ空间头像URL
     */
    private String figureurl2;

    /**
     * 大小为40×40像素的QQ头像URL
     */
    private String figureurlQq1;

    /**
     * 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有
     */
    private String figureurlQq2;

    /**
     * 性别。 如果获取不到则默认返回"男"
     */
    private String gender;

    /**
     * 标识用户是否为黄钻用户（0：不是；1：是）
     */
    private Integer isYellowVip;

    /**
     * 标识用户是否为黄钻用户（0：不是；1：是）
     */
    private Integer vip;

    /**
     * 黄钻等级
     */
    private Integer yellowVipLevel;

    /**
     * 黄钻等级
     */
    private Integer level;

    /**
     * 标识是否为年费黄钻用户（0：不是； 1：是）
     */
    private Integer isYellowYearVip;

    private Date updateDate;

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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl1() {
        return figureurl1;
    }

    public void setFigureurl1(String figureurl1) {
        this.figureurl1 = figureurl1;
    }

    public String getFigureurl2() {
        return figureurl2;
    }

    public void setFigureurl2(String figureurl2) {
        this.figureurl2 = figureurl2;
    }

    public String getFigureurlQq1() {
        return figureurlQq1;
    }

    public void setFigureurlQq1(String figureurlQq1) {
        this.figureurlQq1 = figureurlQq1;
    }

    public String getFigureurlQq2() {
        return figureurlQq2;
    }

    public void setFigureurlQq2(String figureurlQq2) {
        this.figureurlQq2 = figureurlQq2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getIsYellowVip() {
        return isYellowVip;
    }

    public void setIsYellowVip(Integer isYellowVip) {
        this.isYellowVip = isYellowVip;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getYellowVipLevel() {
        return yellowVipLevel;
    }

    public void setYellowVipLevel(Integer yellowVipLevel) {
        this.yellowVipLevel = yellowVipLevel;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIsYellowYearVip() {
        return isYellowYearVip;
    }

    public void setIsYellowYearVip(Integer isYellowYearVip) {
        this.isYellowYearVip = isYellowYearVip;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
