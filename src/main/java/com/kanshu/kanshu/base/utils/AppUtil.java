package com.kanshu.kanshu.base.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AppUtil {
	private static ThreadLocal<HttpServletRequest> requestMap = new ThreadLocal<HttpServletRequest>();

	private static ThreadLocal<HttpServletResponse> responseMap = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return requestMap.get();
	}

	public static HttpServletResponse getResponse() {
		return responseMap.get();
	}

	public static void setHttp(HttpServletRequest request,
			HttpServletResponse response) {
		requestMap.set(request);
		responseMap.set(response);
	}

	public static HttpSession getSession() {
		return requestMap.get().getSession();
	}

//	public static User getUser() {
//		return (User) getSession().getAttribute("user");
//	}

	public static boolean isEn() {
		boolean flag = false;
		Object isEn = getSession().getAttribute("isEn");
		if (isEn != null)
			flag = Boolean.parseBoolean(isEn.toString());
		return flag;
	}

	public static void removeAll() {
		requestMap.remove();
		responseMap.remove();
	}
	
	public static Object getObjectFromApplication(ServletContext servletContext,
			String beanName) {
			ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			return application.getBean(beanName);
	} 
}
