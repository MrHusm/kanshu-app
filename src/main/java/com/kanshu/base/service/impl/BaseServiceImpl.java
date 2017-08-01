package com.kanshu.base.service.impl;


import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.IBaseService;
import com.kanshu.base.utils.LogUtil;
import com.kanshu.base.utils.PageFinder;
import com.kanshu.base.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Description: 公共基础service实现类
 * All Rights Reserved.
 * @param <T>
 * @version 1.0  2017年7月4日 hushengmeng 创建
 */
@Service
public abstract class BaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK> {

	private Class<T> entityClass;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseServiceImpl(){
	 	Type genType = getClass().getGenericSuperclass();  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
        entityClass = (Class) params[0];  
	}
	
	public String getPrefix(){
		return entityClass.getSimpleName()+"Mapper.";
	}
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public abstract IBaseDao<T> getBaseDao();
	
	public T get(final PK id){
		
		return getBaseDao().selectOne(getPrefix()+"selectByPrimaryKey", id);
	}

	@Override
	public int deleteById(PK id) {
		return getBaseDao().delete(getPrefix()+"deleteByPrimaryKey", id);
	}

	@Override
	public int save(T target) {
		return getBaseDao().insert(getPrefix()+"insertSelective", target);
	}
	@Override
	public int save(List<T> target) {
		return getBaseDao().insert(getPrefix()+"insertBatch", target);
	}

	@Override
	public int update(T target) {
		return getBaseDao().update(getPrefix()+"updateByPrimaryKeySelective", target);
	}
	

	@Override
	public List<T> findListByParamsObjs(Object params) {
		return getBaseDao().selectList(getPrefix()+"getAll", convertBeanToMap(params));
	}

	@Override
	public List<T> findListByParams(Object... params) {
		return this.findListByParamsObjs(map(params));
	}
	
	@Override
	public int deleteByParamsObjs(Object params){
		return getBaseDao().delete(getPrefix()+"deleteByMap", convertBeanToMap(params));
	}
	
	@Override
	public int deleteByByParams(Object... params){
		return deleteByParamsObjs(map(params));
	}

	@Override
	public T findUniqueByParams(Object... params) {
		return this.findUniqueByParamsObjs(map(params));
	}

	@Override
	public PageFinder<T> findPageFinderObjs(Object params, Query query) {
		params = convertBeanToMap(params);
		return getBaseDao().getPageFinderObjs(params, query, getPrefix()+"pageCount", getPrefix()+"pageData");
	}

	@Override
	public T findMasterById(PK id) {
		return getBaseDao().selectMasterOne(getPrefix()+"selectByPrimaryKey", id);
	}

	@Override
	public List<T> findMasterListByParamsObjs(Object params) {
		return getBaseDao().selectMasterList(getPrefix()+"getAll", convertBeanToMap(params));
	}

	@Override
	public List<T> findMasterListByParams(Object... params) {
		return this.findMasterListByParamsObjs(map(params));
	}

	@Override
	public T findMasterUniqueByParams(Object... params) {
		return this.findMasterUniqueByParamsObjs(map(params));
	}
	
	@Override
	public T findUniqueByParamsObjs(Object params) {
		return getBaseDao().selectOne(getPrefix()+"getAll", convertBeanToMap(params));
	}

	@Override
	public T findMasterUniqueByParamsObjs(Object params) {
		return getBaseDao().selectMasterOne(getPrefix()+"getAll", convertBeanToMap(params));
	}
	
	@Override
	public PageFinder<T> findMasterPageFinderObjs(Object params, Query query) {
		
		return getBaseDao().getMasterPageFinderObjs(convertBeanToMap(params), query, getPrefix()+"pageCount", getPrefix()+"pageData");
	}
	
	@Override
	public List<T> findByIds(List<PK> ids){
		return getBaseDao().selectList(getPrefix()+"selectByIds", ids);
	}
	
	@Override
	public int deleteByIds(List<PK> ids){
		return getBaseDao().delete(getPrefix()+"deleteByIds", ids);
	}
	
	@Override
	public List<T> findMasterByIds(List<PK> ids){
		return getBaseDao().selectMasterList(getPrefix()+"selectByIds", ids);
	}

	/**
	 * 根据参数构造map，参数必须为偶数个，依次为key1，value1，key2，value2…….
	 * @param datas 参数列表
	 * @return 构造出的map
	 */
	protected Map<String, Object> map(final Object... datas) {
		Assert.isTrue(datas.length % 2 == 0, "参数必须为偶数个");
		final Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < datas.length; i += 2) {
			map.put(datas[i].toString(), datas[i + 1]);
		}
		return map;
	}
	
	/**
	 * 
	 * Description: 将一个 JavaBean 对象转化为一个  Map
	 * @Version1.0 2014年11月15日 下午5:18:22 by 张宪斌（zhangxianbin@dangdang.com）创建
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Map<String, Object> convertBeanToMap(Object bean){ 
		Map<String, Object> returnMap = new HashMap<String, Object>(); 
		if(bean == null){
			return returnMap;
		}
		if(bean instanceof Map){
			return (Map<String, Object>)bean;
		}
        Class type = bean.getClass(); 
        try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type); 
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
			for (int i = 0; i< propertyDescriptors.length; i++) { 
			    PropertyDescriptor descriptor = propertyDescriptors[i]; 
			    String propertyName = descriptor.getName(); 
			    if (!propertyName.equals("class")) { 
			        Method readMethod = descriptor.getReadMethod(); 
			        Object result = readMethod.invoke(bean, new Object[0]); 
			        if (result != null) { 
			        	if(result instanceof String && StringUtils.isEmpty(result.toString())){
			        		continue;
			        	}
			            returnMap.put(propertyName, result); 
			        }
			    } 
			}
		} catch (Exception e) {
			LogUtil.error(logger, e, "convertBeanToMap....fail");
			throw new RuntimeException("将 JavaBean : " + entityClass.getSimpleName() + " 转化为 Map失败！");
		}
        return returnMap; 
    }

	@Override
	public String getEntityClassName() {
		return entityClass.getName();
	} 


}
