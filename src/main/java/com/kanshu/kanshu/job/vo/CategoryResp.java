package com.kanshu.kanshu.job.vo;
/**
 * 
 * @ClassName: CategoryResp 
 * @Description: 分类返回信息
 * @author hushengmeng
 * @date 2016年5月25日 下午5:37:05 
 *
 */
public class CategoryResp {
	private Long iDX;// 主键
	private Integer freetype;// 一级分类ID
	private String freetypename;// 一级分类名称
	private Integer categoryid;// 二级分类ID	
	private String categoryname;// 二级分类名称
	private Integer subcategoryid;// 三级分类ID
	private String subcategoryname;// 三年级分类名称
	private Integer status;// 状态 0:无效 1:有效
	private Integer site;
	public CategoryResp() {
		super();
	}
	public Long getiDX() {
		return iDX;
	}
	public void setiDX(Long iDX) {
		this.iDX = iDX;
	}
	public Integer getFreetype() {
		return freetype;
	}
	public void setFreetype(Integer freetype) {
		this.freetype = freetype;
	}
	public String getFreetypename() {
		return freetypename;
	}
	public void setFreetypename(String freetypename) {
		this.freetypename = freetypename;
	}
	public Integer getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public Integer getSubcategoryid() {
		return subcategoryid;
	}
	public void setSubcategoryid(Integer subcategoryid) {
		this.subcategoryid = subcategoryid;
	}
	public String getSubcategoryname() {
		return subcategoryname;
	}
	public void setSubcategoryname(String subcategoryname) {
		this.subcategoryname = subcategoryname;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
