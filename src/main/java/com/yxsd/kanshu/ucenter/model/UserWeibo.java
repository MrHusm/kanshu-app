package com.yxsd.kanshu.ucenter.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lenovo on 2017/8/19.
 */
public class UserWeibo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String weiboId;                //微博用户id

    private String screenName;            //微博昵称

    private String name;                  //友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)

    private Integer province;                 //省份编码（参考省份编码表）

    private Integer city;                     //城市编码（参考城市编码表）

    private String location;              //地址

    private String description;           //个人描述

    private String url;                   //用户博客地址

    private String profileImageUrl;       //自定义图像

    private String profileUrl;          //用户的微博统一URL地址

    private String domain;            //用户个性化URL

    private String gender;                //性别,m--男，f--女,n--未知

    private Integer followersCount;           //粉丝数

    private Integer friendsCount;             //关注数

    private Integer statusesCount;            //微博数

    private Integer favouritesCount;          //收藏数

    private String createdAt;               //创建时间

    private Integer following;            //保留字段,是否已关注(此特性暂不支持)

    private Integer verified;             //加V标示，是否微博认证用户

    private Integer verifiedType;             //认证类型

    private Integer allowAllActMsg;       //是否允许所有人给我发私信

    private Integer allowAllComment;      //是否允许所有人对我的微博进行评论

    private Integer followMe;             //此用户是否关注我

    private String avatarHd;           //大头像地址

    private String avatarLarge;           //大头像地址

    private Integer onlineStatus;             //用户在线状态

    private String status = null;         //用户最新一条微博

    private Integer biFollowersCount;         //互粉数

    private String remark;                //备注信息，在查询用户关系时提供此字段。

    private String lang;                  //用户语言版本

    private String verifiedReason;		  //认证原因

    private String weihao;				  //微號

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

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String userDomain) {
        this.domain = domain;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }

    public Integer getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(Integer statusesCount) {
        this.statusesCount = statusesCount;
    }

    public Integer getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(Integer favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public Integer getVerifiedType() {
        return verifiedType;
    }

    public void setVerifiedType(Integer verifiedType) {
        this.verifiedType = verifiedType;
    }

    public Integer getAllowAllActMsg() {
        return allowAllActMsg;
    }

    public void setAllowAllActMsg(Integer allowAllActMsg) {
        this.allowAllActMsg = allowAllActMsg;
    }

    public Integer getAllowAllComment() {
        return allowAllComment;
    }

    public void setAllowAllComment(Integer allowAllComment) {
        this.allowAllComment = allowAllComment;
    }

    public Integer getFollowMe() {
        return followMe;
    }

    public void setFollowMe(Integer followMe) {
        this.followMe = followMe;
    }

    public String getAvatarLarge() {
        return avatarLarge;
    }

    public void setAvatarLarge(String avatarLarge) {
        this.avatarLarge = avatarLarge;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBiFollowersCount() {
        return biFollowersCount;
    }

    public void setBiFollowersCount(Integer biFollowersCount) {
        this.biFollowersCount = biFollowersCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getVerifiedReason() {
        return verifiedReason;
    }

    public void setVerifiedReason(String verifiedReason) {
        this.verifiedReason = verifiedReason;
    }

    public String getWeihao() {
        return weihao;
    }

    public void setWeihao(String weihao) {
        this.weihao = weihao;
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

    public String getAvatarHd() {
        return avatarHd;
    }

    public void setAvatarHd(String avatarHd) {
        this.avatarHd = avatarHd;
    }
}
