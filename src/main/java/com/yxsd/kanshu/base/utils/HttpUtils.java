package com.yxsd.kanshu.base.utils;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

/**
 * http相关工具方法.
 * 
 * @author dangdang
 */
public abstract class HttpUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/** 代理名称. */
	private static final String PROXY_NAME = ConfigPropertieUtils.getString("proxy.name");

	/** 代理端口. */
	private static final String PROXY_PORT = ConfigPropertieUtils.getString("proxy.port");

	/** 代理用户 */
	private static final String PROXY_USERNAME = ConfigPropertieUtils.getString("proxy.userName");

	/** 代理端口. */
	private static final String PROXY_PASSWORD = ConfigPropertieUtils.getString("proxy.password");

	/** get请求. **/
	private static final String GET_METHOD = "get";

	/** post请求. **/
	private static final String POST_METHOD = "post";

	/** 是否使用代理. */
	private static final boolean IS_WITH_PROXY = isWithProxy();

	public static final String UTF8 = "UTF-8";

	public static final String GBK = "GBK";

	/** httpclient对应CONNECTION_TIMEOUT对应的值 */
	private static final Integer CONNECTION_TIMEOUT = ConfigPropertieUtils.getInteger("httpclient.connection.timeout", 5000);

	/** httpclient对应SO_TIMEOUT对应的值 */
	private static final Integer SO_TIMEOUT = ConfigPropertieUtils.getInteger("httpclient.so.timeout", 20000);

	private HttpUtils() {
	}

	private static void setProxy(DefaultHttpClient httpclient) {
		httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, new NTCredentials(PROXY_USERNAME, PROXY_PASSWORD, PROXY_NAME, "dangdang.com"));

		List<String> authpref = new ArrayList<String>();
		authpref.add(AuthPolicy.NTLM);
		httpclient.getParams().setParameter(AuthPNames.PROXY_AUTH_PREF, authpref);
		HttpHost proxy = new HttpHost(PROXY_NAME, 8080);

		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}

	private static void setProxy(HttpClient httpClient) {
		httpClient.getHostConfiguration().setProxy(PROXY_NAME, Integer.valueOf(PROXY_PORT));
		httpClient.getParams().setAuthenticationPreemptive(true);
		httpClient.getState().setProxyCredentials(org.apache.commons.httpclient.auth.AuthScope.ANY,
				new org.apache.commons.httpclient.NTCredentials(PROXY_USERNAME, PROXY_PASSWORD, PROXY_NAME, "dangdang.com"));
	}

	public static byte[] getBytes(final String urlStr) {

		return getBytes(urlStr, IS_WITH_PROXY);
	}

	public static byte[] getBytes(final String urlStr, boolean useProxy) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			if (useProxy) { // 使用代理获取url内容
				setProxy(httpclient);
			}
			URL url = new URL(urlStr);

			int port = url.getPort();
			if (port == -1) {
				port = url.getDefaultPort();
			}
			HttpHost targetHost = new HttpHost(url.getHost(), port, url.getProtocol());

			HttpGet httpget = new HttpGet(url.getFile());

			// 设置连接一个url的连接等待超时时间
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			// 设置读取数据的超时时间
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			HttpResponse response = httpclient.execute(targetHost, httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			return IOUtils.toByteArray(in);
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	public static byte[] getBytes(final String urlStr, boolean useProxy, boolean simulateBrowser) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			if (useProxy) { // 使用代理获取url内容
				setProxy(httpclient);
			}
			URL url = new URL(urlStr);

			int port = url.getPort();
			if (port == -1) {
				port = url.getDefaultPort();
			}
			HttpHost targetHost = new HttpHost(url.getHost(), port, url.getProtocol());

			HttpGet httpget = new HttpGet(url.getFile());

			if (simulateBrowser) {
				httpget.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
				httpget.setHeader("Accept-Encoding", "gzip, deflate");
				httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
				httpget.setHeader("Connection", "keep-alive");
				// httpget.setHeader("Cookie", cookie);
				String host = "";
				if (urlStr.toLowerCase().startsWith("http://")) {
					host = urlStr.substring(7);
				} else {
					host = urlStr;
				}
				int index = host.indexOf("/");
				if (index > 0) {
					host = host.substring(0, index);
				}
				httpget.setHeader("Host", host);
				// httpget.setHeader("refer",
				// "http://www.baidu.com/s?tn=monline_5_dg&bs=httpclient4+MultiThreadedHttpConnectionManager");
				httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
			}

			// 设置连接一个url的连接等待超时时间
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			// 设置读取数据的超时时间
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			HttpResponse response = httpclient.execute(targetHost, httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			return IOUtils.toByteArray(in);
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	/**
	 * 获取url内容.
	 */
	public static String getContent(final String url) {
		byte[] bytes = getBytes(url);
		if (bytes != null) {
			return new String(bytes);
		}
		return "";
	}

	/**
	 * 获取url内容.
	 */
	public static String getContent(final String url, String charset) {
		byte[] bytes = getBytes(url);
		if (bytes != null) {
			try {
				return new String(bytes, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持的编码格式：" + charset, e);
			}
		}
		return "";
	}
	
	/**
	 * 获取url内容.
	 */
	public static String getContent(final String url, String charset, Map<String, String> headerMap) {
		byte[] bytes = getBytes(url, IS_WITH_PROXY, headerMap);
		if (bytes != null) {
			try {
				return new String(bytes, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持的编码格式：" + charset, e);
			}
		}
		return "";
	}
	
	public static byte[] getBytes(final String urlStr, boolean useProxy, Map<String, String> headerMap) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			if (useProxy) { // 使用代理获取url内容
				setProxy(httpclient);
			}
			URL url = new URL(urlStr);

			int port = url.getPort();
			if (port == -1) {
				port = url.getDefaultPort();
			}
			HttpHost targetHost = new HttpHost(url.getHost(), port, url.getProtocol());

			HttpGet httpget = new HttpGet(url.getFile());
			
			for (Entry<String, String> entry : headerMap.entrySet()) {
				httpget.setHeader(entry.getKey(), entry.getValue());
			}
			
			// 设置连接一个url的连接等待超时时间
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			// 设置读取数据的超时时间
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			HttpResponse response = httpclient.execute(targetHost, httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			return IOUtils.toByteArray(in);
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	/**
	 * 获取url内容.
	 * 
	 * @param url
	 * @param charset
	 * @param simulateBrowser
	 *            是否设置相关参数模拟浏览器.
	 * @return
	 */
	public static String getContent(final String url, String charset, boolean simulateBrowser) {
		byte[] bytes = getBytes(url, IS_WITH_PROXY, simulateBrowser);
		if (bytes != null) {
			try {
				return new String(bytes, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持的编码格式：" + charset, e);
			}
		}
		return "";
	}

	public static String getContentByPost(final String url, byte[] data) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		InputStreamRequestEntity requestEntity = new InputStreamRequestEntity(new ByteArrayInputStream(data));
		method.setRequestEntity(requestEntity);
		// 设置连接一个url的连接等待超时时间
		client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
		// 设置读取数据的超时时间
		client.getHttpConnectionManager().getParams().setSoTimeout(SO_TIMEOUT);
		try {
			final int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes, method.getResponseCharSet());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}

	public static String getContentByPost(final String url, byte[] data, Integer connectionTimeout, Integer soTimeout) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		InputStreamRequestEntity requestEntity = new InputStreamRequestEntity(new ByteArrayInputStream(data));
		method.setRequestEntity(requestEntity);
		// 设置连接一个url的连接等待超时时间
		if (connectionTimeout == null) {
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
		} else {
			client.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		}

		// 设置读取数据的超时时间
		if (soTimeout == null) {
			client.getHttpConnectionManager().getParams().setSoTimeout(SO_TIMEOUT);
		} else {
			client.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
		}
		try {
			final int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes, method.getResponseCharSet());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}

	public static String getContent(final String url, String methodStr, Map<String, String> paramsMap, String encode, boolean useProxy, int connectionTimeout,
			int soTimeout) {
		final HttpClient httpClient = new HttpClient();

		if (useProxy) {
			setProxy(httpClient);
		}

		HttpMethodBase method = null;

		if (methodStr.toLowerCase().equals(GET_METHOD)) {
			method = new GetMethod(url);

			if (paramsMap.size() > 0) {
				NameValuePair[] params = getParamsFromMap(paramsMap);
				String queryString = EncodingUtil.formUrlEncode(params, encode);
				method.setQueryString(queryString);
			}
		} else {
			method = new PostMethod(url);
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encode);
			NameValuePair[] params = getParamsFromMap(paramsMap);
			((PostMethod) method).setRequestBody(params);
		}
		
		try {
			// 设置连接一个url的连接等待超时时间
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
			// 设置读取数据的超时时间
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

			final int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes, method.getResponseCharSet());
			}
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			method.releaseConnection();
			// 客户端主动关闭连接
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}

	/**
	 * 通过http方式访问url调用接口.
	 * 
	 * @param url
	 *            接口地址
	 * @param methodStr
	 *            get或post方式
	 * @param paramsMap
	 *            参数列表
	 * @param encode
	 *            参数编码
	 * @param useProxy
	 *            是否使用代理
	 * @return
	 */
	public static String getContent(final String url, String methodStr, Map<String, String> paramsMap, String encode, boolean useProxy) {
		return getContent(url, methodStr, paramsMap, encode, useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT);
	}

	public static String getContentWithOutFormEncode(final String url, String methodStr, Map<String, String> paramsMap, String encode, boolean useProxy,
			int connectionTimeout, int soTimeout) {

		final HttpClient httpClient = new HttpClient();

		// 设置连接一个url的连接等待超时时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		// 设置读取数据的超时时间
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

		if (useProxy) {
			setProxy(httpClient);
		}

		HttpMethodBase method = null;

		method = new GetMethod(url);

		StringBuffer queryString = new StringBuffer();
		Iterator it = paramsMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry m = (Entry) it.next();
			queryString.append(m.getKey()).append("=").append(m.getValue()).append("&");
		}

		method.setQueryString(queryString.toString().substring(0, queryString.length() - 1));

		try {
			final int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes, method.getResponseCharSet());
			}
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			method.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}

	/**
	 * Form- without urlencoding.
	 * 
	 * @param url
	 * @param methodStr
	 * @param paramsMap
	 * @param encode
	 * @param useProxy
	 * @return
	 */
	public static String getContentWithOutFormEncode(final String url, String methodStr, Map<String, String> paramsMap, String encode, boolean useProxy) {
		return getContentWithOutFormEncode(url, methodStr, paramsMap, encode, useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT);
	}

	/**
	 * 从参数map中构建NameValuePair数组.
	 * 
	 * @param paramsMap
	 * @return
	 */
	private static NameValuePair[] getParamsFromMap(Map<String, String> paramsMap) {
		NameValuePair[] params = new NameValuePair[paramsMap.size()];
		int pos = 0;
		Iterator<String> iter = paramsMap.keySet().iterator();
		while (iter.hasNext()) {
			String paramName = iter.next();
			String paramValue = paramsMap.get(paramName);

			params[pos++] = new NameValuePair(paramName, paramValue);
		}

		return params;
	}

	/**
	 * 对Url发起请求.
	 * 
	 * @param url
	 * @throws IOException
	 * @throws HttpException
	 */
	public static void requestUrl(final String url) throws IOException {
		final HttpClient httpClient = new HttpClient();
		final GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		try {
			httpClient.executeMethod(getMethod);
		} finally {
			getMethod.releaseConnection();
		}
	}

	/**
	 * 是否使用代理.
	 */
	private static boolean isWithProxy() {
		return StringUtils.isNotBlank(PROXY_NAME) && StringUtils.isNotBlank(PROXY_PORT);
	}
	
	/**
	 * Description:java 发post请求 
	 * @Version1.0 2015年6月6日 上午10:48:17 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param url
	 * @param param
	 * @return
	 */
	public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	
	public static String sendPostSetEncode(String url, String param,Map<String, String> params) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            for(Entry<String, String> key:params.entrySet()){
            	if( key.getKey().equals("Content-Type")){
            		conn.setRequestProperty("Content-Type", key.getValue());
            	}else if( key.getKey().equals("Charsert")){
            		conn.setRequestProperty("Charsert", key.getValue());
            	}else if(key.getKey().equals("UseCaches")){
            		conn.setUseCaches(Boolean.parseBoolean(key.getValue()));
            	}
            }
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	/**
	 * 
	 * @Description:发送类型为multipart/form-data的表单的请求
	 * @author hushengmeng
	 * @time:2015年10月19日 下午3:19:05
	 * @param url
	 * @param params
	 * @param fileMineType
	 * @param encode
	 * @param retryCount
	 * @return
	 */
	public static String getContentByMultiPost(String url, Map<String, Object> params, String fileMineType, Charset encode, Integer retryCount) {
		int i = 0;
		retryCount = (retryCount == null ? 0 : retryCount);
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		String responseMsg = null;
		while (true) {
			try {
				httpClient = HttpClients.createDefault();
				logger.info("getContentByMultiPost url:{}", url);
				httpPost = new HttpPost(url);
				//初始化请求参数
				logger.info("getContentByMultiPost params:{}", params);
				MultipartEntityBuilder entity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				entity.setCharset(encode);
				ContentType contentType = ContentType.create(ContentType.DEFAULT_TEXT.getMimeType(), encode);
				if (params != null && !params.isEmpty()) {
					Set<Entry<String, Object>> set = params.entrySet();
					Iterator<Entry<String, Object>> it = set.iterator();
					while (it.hasNext()) {
						Entry<String, Object> entry = it.next();
						AbstractContentBody ctxBody = null;
						if (entry.getValue() instanceof File) {
							ctxBody = new FileBody((File) entry.getValue());
						}else if(entry.getValue() instanceof Buffer){
							ctxBody = new InputStreamBody((InputStream) entry.getValue(), ContentType.DEFAULT_BINARY);
						}else {
							ctxBody = new StringBody(entry.getValue()
									.toString(), contentType);
						}
						entity.addPart(entry.getKey(), ctxBody);
					}
					httpPost.setEntity(entity.build());
				}
				
				//发送请求
				HttpResponse response = httpClient.execute(httpPost);
				Integer statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("getContentByMultiPost fail statusCode:{}",
							statusCode);
					i++;
					if (i > retryCount) {
						break;
					}
				} else {
					HttpEntity respEntity = response.getEntity();
					if (respEntity != null) {
						responseMsg = EntityUtils.toString(respEntity);
						if (StringUtils.isEmpty(responseMsg)) {
							logger.error("getContentByMultiPost responseMsg empty!");
						}else{
							logger.info("getContentByMultiPost responseMsg:{}", responseMsg);
						}
					}
					break;
				}
			} catch (Exception e) {
				logger.error("getContentByMultiPost exception=[{}]",
						ExceptionUtils.getFullStackTrace(e));
				i++;
				if (i > retryCount) {
					break;
				}
			} finally {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseMsg;
	}
	
	/**
	 * 
	 * @Description:发送类型为multipart/form-data的表单的请求
	 * @author hushengmeng
	 * @time:2015年10月19日 下午3:19:05
	 * @param url
	 * @param params
	 * @param fileMineType
	 * @param encode
	 * @param retryCount
	 * @return
	 */
	public static String getContentByPost(String url, Map<String, Object> params, Charset encode, Integer retryCount) {
		int i = 0;
		retryCount = (retryCount == null ? 0 : retryCount);
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		String responseMsg = null;
		while (true) {
			try {
				httpClient = HttpClients.createDefault();
				logger.info("getContentByMultiPost url:{}", url);
				httpPost = new HttpPost(url);
				//初始化请求参数
				logger.info("getContentByMultiPost params:{}", params);
				MultipartEntityBuilder entity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				entity.setCharset(encode);
				ContentType contentType = ContentType.create(ContentType.DEFAULT_TEXT.getMimeType(), encode);
				if (params != null && !params.isEmpty()) {
					Set<Entry<String, Object>> set = params.entrySet();
					Iterator<Entry<String, Object>> it = set.iterator();
					while (it.hasNext()) {
						Entry<String, Object> entry = it.next();
						AbstractContentBody ctxBody = null;
						if (entry.getValue() instanceof File) {
							ctxBody = new FileBody((File) entry.getValue());
						}else if(entry.getValue() instanceof Buffer){
							ctxBody = new InputStreamBody((InputStream) entry.getValue(), ContentType.DEFAULT_BINARY);
						}else {
							ctxBody = new StringBody(entry.getValue()
									.toString(), contentType);
						}
						entity.addPart(entry.getKey(), ctxBody);
					}
					httpPost.setEntity(entity.build());
				}
				
				//发送请求
				HttpResponse response = httpClient.execute(httpPost);
				Integer statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("getContentByMultiPost fail statusCode:{}",
							statusCode);
					i++;
					if (i > retryCount) {
						break;
					}
				} else {
					HttpEntity respEntity = response.getEntity();
					if (respEntity != null) {
						responseMsg = EntityUtils.toString(respEntity);
						if (StringUtils.isEmpty(responseMsg)) {
							logger.error("getContentByMultiPost responseMsg empty!");
						}else{
							logger.info("getContentByMultiPost responseMsg:{}", responseMsg);
						}
					}
					break;
				}
			} catch (Exception e) {
				logger.error("getContentByMultiPost exception=[{}]",
						ExceptionUtils.getFullStackTrace(e));
				i++;
				if (i > retryCount) {
					break;
				}
			} finally {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseMsg;
	}

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
	
	public static void main(final String[] args) throws Exception {
	}
}
