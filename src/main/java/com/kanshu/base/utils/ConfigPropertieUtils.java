package com.kanshu.base.utils;
import java.util.Properties;

/**
 * hushengmeng
 */
public class ConfigPropertieUtils {   
	public static Properties prop = new Properties();
    
    public static String getString(final String key) {
		return prop.getProperty(key);
	}

	public static String getString(final String key, final String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}
	
	public static Integer getInteger(final String key, final Integer defaultValue) {
		Integer returnValue;
		
		String value = prop.getProperty(key);
		
		if(value != null && !value.trim().equals("")){
			try {
				returnValue = Integer.parseInt(value);
			} catch (Exception e) {
				returnValue = defaultValue;
			}
		}else{
			returnValue = defaultValue;
		}
		
		return returnValue;
	}
} 
