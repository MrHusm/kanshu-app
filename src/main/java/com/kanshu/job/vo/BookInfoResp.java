package com.kanshu.job.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName: BookInfoResp 
 * @Description: 图书信息返回对象
 * @author wangdingtai
 * @date 2016年5月24日 下午5:53:05 
 *
 */
public class BookInfoResp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3651205333707256668L;
	private long cBID;// 书号
	private int allwords;// 总字数
	private long authorid;// 作者ID
	private String authorname;// 作家笔名
	private int categoryid;// 二级分类
	private String coverurl;// 封面图
	private String createtime;// 创建时间
	private int status;// 书籍状态 30:连载中 40:暂停中 45:完结申请 50:已完结  (101:封笔 102:undefined 103:精彩纷呈 104:接近尾声 105:新书上传 106:情节展开 107:出版中
	private int subcategoryid;// 三级分类
	private String title;// 小说名
	private int vipstatus;// Vip标志 -1: 公众 1:VIP
	private String viptime;// VIP时间
	private String intro;// 书籍简介
	private String keyword;// 关键字
	private String tag;// 标签
	private String updatetime;// 最新更新
	private int site;// 5:起点男生1：创世2：云起3：起点女生6：阅文集团9：起点文学网
	private long cPID;// 1：创世2：云起3：起点女生5:起点男生9：起点文学网66960: 红袖添香56029：小说阅读网56030：晋江中文网
	public BookInfoResp() {
		super();
	}
	public long getcBID() {
		return cBID;
	}
	public void setcBID(long cBID) {
		this.cBID = cBID;
	}
	public int getAllwords() {
		return allwords;
	}
	public void setAllwords(int allwords) {
		this.allwords = allwords;
	}
	public long getAuthorid() {
		return authorid;
	}
	public void setAuthorid(long authorid) {
		this.authorid = authorid;
	}
	public String getAuthorname() {
		return authorname;
	}
	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public String getCoverurl() {
		return coverurl;
	}
	public void setCoverurl(String coverurl) {
		this.coverurl = coverurl;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSubcategoryid() {
		return subcategoryid;
	}
	public void setSubcategoryid(int subcategoryid) {
		this.subcategoryid = subcategoryid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getVipstatus() {
		return vipstatus;
	}
	public void setVipstatus(int vipstatus) {
		this.vipstatus = vipstatus;
	}
	public String getViptime() {
		return viptime;
	}
	public void setViptime(String viptime) {
		this.viptime = viptime;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public int getSite() {
		return site;
	}
	public void setSite(int site) {
		this.site = site;
	}
	public long getcPID() {
		return cPID;
	}
	public void setcPID(long cPID) {
		this.cPID = cPID;
	}
	@Override
	public String toString() {
		return "BookInfoResp [cBID=" + cBID + ", allwords=" + allwords
				+ ", authorid=" + authorid + ", authorname=" + authorname
				+ ", categoryid=" + categoryid + ", coverurl=" + coverurl
				+ ", createtime=" + createtime + ", status=" + status
				+ ", subcategoryid=" + subcategoryid + ", title=" + title
				+ ", vipstatus=" + vipstatus + ", viptime=" + viptime
				+ ", intro=" + intro + ", keyword=" + keyword + ", tag=" + tag
				+ ", updatetime=" + updatetime + ", site=" + site + ", cPID="
				+ cPID + "]";
	}
	
}
