/**
 * Description: LogUtil.java
 * All Rights Reserved.
 * @version 1.0  2014-6-12 上午10:23:43  by 王星皓（wangxinghao@dangdang.com）创建
 */
package com.yxsd.kanshu.base.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * Description: 日志记录工具类 All Rights Reserved.
 *
 * @version 1.0  2017年7月4日 hushengmeng 创建
 */
public final class LogUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);

	/**
	 * 
	 * Description: info 日志消息记录
	 * 
	 * @Version1.0 2014- 6-4 下午11:32:06 by 王星皓（ wangxinghao@dangdang.com）创建
	 * @param logger
	 *            日志对象
	 * @param message
	 *            记录日志的消息 例如:<code>xxxxxx{0}xxxxx{1} </code>
	 * @param args
	 *            具体消息参数 用于替代{0}{1}
	 */
	public static void info(Logger logger, String message, Object... args) {
		try {
			logger.info(LogUtil.messageFormat(message, args));
		} catch (Exception e) {
			LOGGER.error(
					"Log日志工具类info(Logger logger,String message,String... args)发生异常:",
					e);
		}
	}

	/**
	 * 
	 * Description: warn 日志消息记录
	 * 
	 * @Version1.0 2014- 6-4 下午11:32:44 by 王星皓（ wangxinghao@dangdang.com）创建
	 * @param logger
	 *            日志对象
	 * @param message
	 *            记录日志的消息 例如:<code>xxxxxx{0}xxxxx{1} </code>
	 * @param args
	 *            具体消息参数 用于替代{0}{1}
	 */
	public static void warn(Logger logger, String message, Object... args) {
		try {
			logger.warn(LogUtil.messageFormat(message, args));
		} catch (Exception e) {
			LOGGER.error(
					"Log日志工具类warn(Logger logger,String message,String... args)发生异常:",
					e);
		}
	}

	/**
	 * 
	 * Description: warn 日志消息记录
	 * 
	 * @Version1.0 2014- 6-4 下午11:33:16 by 王星皓（ wangxinghao@dangdang.com）创建
	 * @param logger
	 *            日志对象
	 * @param exception
	 *            具体的异常信息
	 * @param message
	 *            记录日志的消息 例如:<code>xxxxxx{0}xxxxx{1} </code>
	 * @param args
	 *            具体消息参数 用于替代{0}{1}
	 */
	public static void warn(Logger logger, Exception exception, String message,
			Object... args) {
		try {
			logger.warn(LogUtil.messageFormat(message, args), exception);
		} catch (Exception e) {
			LOGGER.error(
					"Log日志工具类warn(Logger logger,Exception exception,String message,String... args)发生异常:",
					e);
		}
	}

	/**
	 * 
	 * Description: debug 日志消息记录
	 * 
	 * @Version1.0 2014- 6-4 下午11:33:56 by 王星皓（ wangxinghao@dangdang.com）创建
	 * @param logger
	 *            日志对象
	 * @param message
	 *            记录日志的消息 例如:<code>xxxxxx{0}xxxxx{1} </code>
	 * @param args
	 *            具体消息参数 用于替代{0}{1}
	 */
	public static void debug(Logger logger, String message, Object... args) {
		try {
			logger.debug(LogUtil.messageFormat(message, args));
		} catch (Exception e) {
			LOGGER.error(
					"Log日志工具类debug(Logger logger,String message,String... args)发生异常:",
					e);
		}
	}

	/**
	 * 
	 * Description: error 日志消息记录
	 * 
	 * @Version1.0 2014- 6-4 下午11:35:32 by 王星皓（ wangxinghao@dangdang.com）创建
	 * @param logger
	 *            日志对象
	 * @param exception
	 *            具体的异常信息
	 * @param message
	 *            记录日志的消息 例如:<code>xxxxxx{0}xxxxx{1} </code>
	 * @param args
	 *            具体消息参数 用于替代{0}{1}
	 */
	public static void error(Logger logger, Exception exception,
			String message, Object... args) {
		try {
			logger.error(LogUtil.messageFormat(message, args), exception);
		} catch (Exception e) {
			LOGGER.error(
					"Log日志工具类error(Logger logger,Exception exception,String message,String... args)发生异常:",
					e);
		}
	}

	/**
	 * 
	 * Description: error 日志消息记录
	 * 
	 * @Version1.0 2014- 6-4 下午11:35:03 by 王星皓（ wangxinghao@dangdang.com）创建
	 * @param logger
	 *            日志对象
	 * @param message
	 *            记录日志的消息 例如:<code>xxxxxx{0}xxxxx{1} </code>
	 * @param args
	 *            具体消息参数 用于替代{0}{1}
	 */
	public static void error(Logger logger, String message, Object... args) {
		try {
			logger.error(LogUtil.messageFormat(message, args));
		} catch (Exception e) {
			LOGGER.error(
					"Log日志工具类error(Logger logger,String message,String... args)发生异常:",
					e);
		}
	}

	/**
	 * 格式化消息信息
	 * 
	 * @param message
	 * @param args
	 * @return消息信息 x
	 */
	private static String messageFormat(String message, Object... args) {
		try {
			if (StringUtils.isEmpty(message)) {
				return "";
			}
			String str = MessageFormat.format(message, args);
			return str;
		} catch (Exception e) {
			LOGGER.error(
					"Log日志工具类messageFormat(String message,Object... args)发生异常:",
					e);
		}
		return "";
	}

}
