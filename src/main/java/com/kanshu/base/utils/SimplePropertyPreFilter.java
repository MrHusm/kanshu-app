package com.kanshu.base.utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.util.HashSet;
import java.util.Set;

/**
 * 重写 SimplePropertyPreFilter类 支持fastJson1.1.23以上版本.
 * @author chendixun *
 */

public class SimplePropertyPreFilter implements PropertyPreFilter, ValueFilter {

	private final Class<?> clazz;
	private Set<String> includes = new HashSet<String>();
	private Set<String> excludes = new HashSet<String>();
	//valueFilter要进行html编码处理的字段
	private Set<String> htmlFilterFields = new HashSet<String>();
	//valueFilter要进行换行编码处理的字段
	private Set<String> lineFilterFields = new HashSet<String>();

	public SimplePropertyPreFilter(String... properties) {
		this(null, properties);
	}

	public SimplePropertyPreFilter(Class<?> clazz, String... properties) {
		super();
		this.clazz = clazz;
		for (String item : properties) {
			if (item != null) {
				this.includes.add(item);
			}
		}
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public boolean apply(JSONSerializer serializer, Object source, String name) {

//		if (source instanceof MobileEbook) {
//			return applyObject(serializer, source, name);
//		} else {
			return true;
//		}
	}

	public Set<String> getIncludes() {
		return includes;
	}

	public Set<String> getExcludes() {
		return excludes;
	}

	public void setIncludes(Set<String> includes) {
		this.includes = includes;
	}

	public void setExcludes(Set<String> excludes) {
		this.excludes = excludes;
	}

	public boolean applyObject(JSONSerializer serializer, Object source, String name) {

		if (source == null) {
			return true;
		}

		if (clazz != null && !clazz.isInstance(source)) {
			return true;
		}

		if (this.excludes.contains(name)) {
			return false;
		}

		if (includes.size() == 0 || includes.contains(name)) {
			return true;
		}

		return false;

	}
	
	/**
	 * valueFilter对应的实现方法,过滤内容中的html标签.
	 */
	@Override
	public Object process(Object source, String name, Object value) {
		if (htmlFilterFields.contains(name)) {
			return FormatUtils.formatHtmlData(String.valueOf(value));
		} else if (lineFilterFields.contains(name)) {
			return FormatUtils.formatTextData(String.valueOf(value));
		} else {
			return value;
		}
	}

	public void setHtmlFilterFields(Set<String> htmlFilterFields) {
		this.htmlFilterFields = htmlFilterFields;
	}

	public void setLineFilterFields(Set<String> lineFilterFields) {
		this.lineFilterFields = lineFilterFields;
	}
	
}
