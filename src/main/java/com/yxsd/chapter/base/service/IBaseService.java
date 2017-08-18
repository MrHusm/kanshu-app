package com.yxsd.chapter.base.service;

import com.yxsd.chapter.base.dao.IBaseDao;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Description: 公共基础service接口
 * All Rights Reserved.
 * @param <T>
 * @version 1.0  2017年7月4日 hushengmeng 创建
 */
public interface IBaseService<T, PK extends Serializable> {
	
	
	public IBaseDao<T> getBaseDao();
	
	
	/**
	 * 
	 * Description: 根据id查询实体
	 * @Version1.0 2014年11月14日 下午4:44:10 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param id
	 * @return
	 */
	public T get(final PK id);
	
	/**
	 * 
	 * Description: 从库根据ids查询实体
	 * @param ids
	 * @return
	 */
	public List<T> findByIds(List<PK> ids);
	
	/**
	 * 
	 * Description:根据id删除实体 
	 * @Version1.0 2014年11月14日 下午4:45:59 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param id
	 * @return
	 */
	public int deleteById(final PK id);
	
	/**
	 * 
	 * Description: 根据ids删除实体
	 * @param ids
	 * @return
	 */
	public int deleteByIds(List<PK> ids);
	
	/**
	 * 
	 * Description: 根据params删除实体
	 * @param ids
	 * @return
	 */
	public int deleteByParamsObjs(Object params) ;
	
	/**
	 * 
	 * Description: 根据params删除实体
	 * @param ids
	 * @return
	 */
	public int deleteByByParams(Object... params); 
	
	
	/**
	 * 
	 * Description: 保存实体
	 * @Version1.0 2014年11月14日 下午5:42:25 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param target
	 * @return
	 */
	public int save(T target);
	
	/**
	 * 
	 * Description: 批量保存实体
	 * @Version1.0 2014年12月3日 上午11:59:44 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param target
	 * @return
	 */
	public int save(List<T> target);
	
	/**
	 * 
	 * Description: 根据id更新实体
	 * @Version1.0 2014年11月14日 下午5:43:26 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param target
	 * @return
	 */
	public int update(T target);
	
	/**
	 * 
	 * Description: 根据条件集合map查询实体
	 * @Version1.0 2014年11月14日 下午5:51:45 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @return
	 */
	public List<T> findListByParamsObjs(Object params);
	
	/**
	 * 
	 * Description: 根据条件集合map查询实体唯一结果
	 * @Version1.0 2014年11月14日 下午6:54:51 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @return
	 */
	public T findUniqueByParamsObjs(Object params);
	
	/**
	 * 
	 * Description: 根据属性查询实体
	 * @Version1.0 2014年11月14日 下午6:00:04 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @return
	 */
	public List<T> findListByParams(Object... params);
	
	/**
	 * 
	 * Description: 根据属性查询唯一结果
	 * @Version1.0 2014年11月14日 下午6:03:06 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @return
	 */
	public T findUniqueByParams(Object... params);
	
	/**
	 * 
	 * Description: 分页查询
	 * @Version1.0 2014年11月14日 下午6:16:54 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @param query
	 * @return
	 */
	public PageFinder<T> findPageFinderObjs(Object params, Query query);
	
	/**
	 * 
	 * Description: 主库查询
	 * @Version1.0 2014年11月14日 下午6:28:24 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param id
	 * @return
	 */
	public T findMasterById(final PK id);
	
	/**
	 * 
	 * Description: 主库根据ids查询实体
	 * @param ids
	 * @return
	 */
	public List<T> findMasterByIds(List<PK> ids);
	
	/**
	 * 
	 * Description: 主库查询
	 * @Version1.0 2014年11月14日 下午6:29:28 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @return
	 */
	public List<T> findMasterListByParamsObjs(Object params);
	
	/**
	 * 
	 * Description: 主库查询
	 * @Version1.0 2014年11月14日 下午6:53:49 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @return
	 */
	public T findMasterUniqueByParamsObjs(Object params);
	
	/**
	 * 
	 * Description: 主库查询
	 * @Version1.0 2014年11月14日 下午6:30:00 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @return
	 */
	public List<T> findMasterListByParams(Object... params);
	
	/**
	 * 
	 * Description: 主库查询
	 * @Version1.0 2014年11月14日 下午6:30:41 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @return
	 */
	public T findMasterUniqueByParams(Object... params);
	
	/**
	 * 
	 * Description: 主库分页查询
	 * @Version1.0 2014年11月14日 下午6:16:54 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @param query
	 * @return
	 */
	public PageFinder<T> findMasterPageFinderObjs(Object params, Query query);
	
	public String getEntityClassName();
	
	public String getPrefix();
	
}
