package com.yxsd.chapter.base.dao;


import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;


/**
 * 
 * Description: dao公共接口
 * All Rights Reserved.
 * @param <T>
 * @version 1.0  2017年7月4日 下午6:20:09  by hushengmeng 创建
 */
public interface IBaseDao<T> {
	/**
	 * 从库查询
	 */
	public T selectOne(String statement);
	/**
	 * 从库查询
	 */
	public T selectOne(String statement, Object parameter);
	/**
	 * 从库查询
	 */	
	public Map<?, ?> selectMap(String statement, String mapKey);
	/**
	 * 从库查询
	 */	
	public Map<?, ?> selectMap(String statement, Object parameter, String mapKey);
	/**
	 * 从库查询
	 */	
	public Map<?, ?> selectMap(String statement, Object parameter,
							   String mapKey, RowBounds rowBounds);
	/**
	 * 从库查询
	 */	
	public List<T> selectList(String statement);
	/**
	 * 从库查询
	 */	
	public List<T> selectList(String statement, Object parameter);
	/**
	 * 从库查询
	 */	
	public List<T> selectList(String statement, Object parameter,
							  RowBounds rowBounds);
	/**
	 * 从库查询
	 */	
	public void select(String statement, ResultHandler handler);
	/**
	 * 从库查询
	 */	
	public void select(String statement, Object parameter, ResultHandler handler);
	/**
	 * 从库查询
	 */	
	public void select(String statement, Object parameter, RowBounds rowBounds,
					   ResultHandler handler);
	/**
	 * 主库插入
	 */	
	public int insert(String statement);
	/**
	 * 主库插入
	 */	
	public int insert(String statement, Object parameter);
	/**
	 * 主库更新
	 */	
	public int update(String statement);
	/**
	 * 主库更新
	 */	
	public int update(String statement, Object parameter);
	/**
	 * 主库删除
	 */	
	public int delete(String statement);
	/**
	 * 主库删除
	 */	
	public int delete(String statement, Object parameter);
	/**
	 * 主库查询(查询及时信息)
	 */	
	public List<T> selectMasterList(String statement, Object parameter);
	/**
	 * 主库查询(查询及时信息)
	 */	
	public List<T> selectMasterList(String statement);
	/**
	 * 主库查询(查询及时信息)
	 */	
	public T selectMasterOne(String statement, Object parameter);
	/**
	 * 查询分页（从库）
	 * 
	 * @param params
	 *            参数对象
	 * @param query
	 *            查询query
	 * @param countSql
	 *            总记录数的查询sqlid 返回结果是int类型
	 * @param dataSql
	 *            结果集的查询sqlid
	 * @return
	 */	
	public PageFinder<T> getPageFinderObjs(Object params, Query query, String countSql, String dataSql);
	
	/**
	 * 查询分页（主库）
	 * 
	 * @param params
	 *            参数对象
	 * @param query
	 *            查询query
	 * @param countSql
	 *            总记录数的查询sqlid 返回结果是int类型
	 * @param dataSql
	 *            结果集的查询sqlid
	 * @return
	 */	
	public PageFinder<T> getMasterPageFinderObjs(Object params, Query query, String countSql, String dataSql);

}
