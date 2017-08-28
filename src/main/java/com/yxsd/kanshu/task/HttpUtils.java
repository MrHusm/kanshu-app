
package com.yxsd.kanshu.task;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import sun.net.www.protocol.http.HttpURLConnection;
/**
 * 
 * @author qiong.wang
 *
 */
public class HttpUtils {

	/**
	 * 得到请求的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isBlank(ip)) {
			ip = request.getHeader("Host");
		}
		if (StringUtils.isBlank(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isBlank(ip)) {
			ip = "0.0.0.0";
		}
		return ip;
	}

	/**
	 * 得到请求的根目录
	 * 
	 * @param request
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		return basePath;
	}

	/**
	 * 得到结构目录
	 * 
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request) {
		String path = request.getContextPath();
		return path;
	}

	/**
	 * 发送 get请求
	 */
	public static String get(String urlPatch) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlPatch); // 创建URL对象
			// 返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000); // 设置连接超时为5秒
			conn.setRequestMethod("GET"); // 设定请求方式
			conn.connect(); // 建立到远程对象的实际连接
			// 返回打开连接读取的输入流
			DataInputStream dis = new DataInputStream(conn.getInputStream());
			// 判断是否正常响应数据
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("执行获取http请求失败,地址：" + urlPatch);
			} else {
				// 得到输入流
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while (-1 != (len = dis.read(buffer))) {
					baos.write(buffer, 0, len);
					baos.flush();
				}
				return baos.toString("utf-8");

			}
		} catch (Exception e) {
			throw new RuntimeException("执行获取http请求失败,地址：" + urlPatch);
		} finally {
			if (conn != null) {
				conn.disconnect(); // 中断连接
			}
		}
	}

}
