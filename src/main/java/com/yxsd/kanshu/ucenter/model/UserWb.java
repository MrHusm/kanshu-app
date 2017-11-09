package com.yxsd.kanshu.ucenter.model;


import java.util.Date;

/**
 * A data class representing Basic user information element
 */
public class UserWb{

	private static final long serialVersionUID = -332738032648843482L;
	private String id;                      //用户UID
	private String screen_name;            //微博昵称

	private String name;                  //友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)

	private Integer province;                 //省份编码（参考省份编码表）

	private Integer city;                     //城市编码（参考城市编码表）

	private String location;              //地址

	private String description;           //个人描述

	private String url;                   //用户博客地址

	private String profile_image_url;       //自定义图像

	private String profile_url;          //用户的微博统一URL地址

	private String domain;            //用户个性化URL

	private String weihao;				  //微號

	private String gender;                //性别,m--男，f--女,n--未知

	private Integer followers_count;           //粉丝数

	private Integer friends_count;             //关注数

	private Integer statuses_count;            //微博数

	private Integer favourites_count;          //收藏数

	private Date created_at;               //创建时间

	private Integer following;            //保留字段,是否已关注(此特性暂不支持)

	private Integer allow_all_act_msg;       //是否允许所有人给我发私信

	private Integer verified;             //加V标示，是否微博认证用户

	private Integer verified_type;             //认证类型

	private String remark;                //备注信息，在查询用户关系时提供此字段。

	private String status = null;         //用户最新一条微博

	private Integer allow_all_comment;      //是否允许所有人对我的微博进行评论

	private String avatar_large;           //大头像地址

	private String avatar_hd;           //大头像地址

	private Integer follow_me;             //此用户是否关注我

	private Integer online_status;             //用户在线状态

	private Integer bi_followers_count;         //互粉数

	private String lang;                  //用户语言版本

	private String verified_reason;		  //认证原因

	private Date updateDate;

	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
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

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	public String getProfile_url() {
		return profile_url;
	}

	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getWeihao() {
		return weihao;
	}

	public void setWeihao(String weihao) {
		this.weihao = weihao;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(Integer followers_count) {
		this.followers_count = followers_count;
	}

	public Integer getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(Integer friends_count) {
		this.friends_count = friends_count;
	}

	public Integer getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(Integer statuses_count) {
		this.statuses_count = statuses_count;
	}

	public Integer getFavourites_count() {
		return favourites_count;
	}

	public void setFavourites_count(Integer favourites_count) {
		this.favourites_count = favourites_count;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Integer getFollowing() {
		return following;
	}

	public void setFollowing(Integer following) {
		this.following = following;
	}

	public Integer getAllow_all_act_msg() {
		return allow_all_act_msg;
	}

	public void setAllow_all_act_msg(Integer allow_all_act_msg) {
		this.allow_all_act_msg = allow_all_act_msg;
	}

	public Integer getVerified() {
		return verified;
	}

	public void setVerified(Integer verified) {
		this.verified = verified;
	}

	public Integer getVerified_type() {
		return verified_type;
	}

	public void setVerified_type(Integer verified_type) {
		this.verified_type = verified_type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAllow_all_comment() {
		return allow_all_comment;
	}

	public void setAllow_all_comment(Integer allow_all_comment) {
		this.allow_all_comment = allow_all_comment;
	}

	public String getAvatar_large() {
		return avatar_large;
	}

	public void setAvatar_large(String avatar_large) {
		this.avatar_large = avatar_large;
	}

	public String getAvatar_hd() {
		return avatar_hd;
	}

	public void setAvatar_hd(String avatar_hd) {
		this.avatar_hd = avatar_hd;
	}

	public Integer getFollow_me() {
		return follow_me;
	}

	public void setFollow_me(Integer follow_me) {
		this.follow_me = follow_me;
	}

	public Integer getOnline_status() {
		return online_status;
	}

	public void setOnline_status(Integer online_status) {
		this.online_status = online_status;
	}

	public Integer getBi_followers_count() {
		return bi_followers_count;
	}

	public void setBi_followers_count(Integer bi_followers_count) {
		this.bi_followers_count = bi_followers_count;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getVerified_reason() {
		return verified_reason;
	}

	public void setVerified_reason(String verified_reason) {
		this.verified_reason = verified_reason;
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
