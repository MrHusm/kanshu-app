package com.yxsd.kanshu.base.utils;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式化工具类.
 * 
 * @author yangming
 *
 */
public abstract class FormatUtils {

	private static final Logger log = LoggerFactory.getLogger(FormatUtils.class);

	// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>// }
	public static final String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

	// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>// }
	public static final String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

	// 定义HTML标签的正则表达式
	public static final String regEx_html = "[\\w]*<[^>]+>[\\w]*";

	// 定义br标签的正则表达式
	public static final String regEx_br = "<br\\s*/?>";

	private FormatUtils() {
	}

	public static String formatPrice(String priceStr) {
		double price = Double.valueOf(priceStr);
		DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance();
		df.applyPattern("0.00");
		return df.format(price);
	}

	/**
	 * 获取HTML的内容,将其中的BR输出为换行符.
	 * 
	 * @param htmlData
	 * @return
	 */
	public static String formatHtmlData(String htmlData) {
		if (StringUtils.isEmpty(htmlData)) {
			return htmlData;
		}
		if (htmlData.indexOf("&") >= 0 || htmlData.indexOf("<") >= 0 || htmlData.indexOf(">") >= 0) {
			// 转变BR为换行符
			htmlData = htmlData.replaceAll("<br\\s*/?>","\\\\r\\\\n");
			// 处理内容中的HTML标签
			return html2Str(htmlData);
		} else {
			return htmlData;
		}
	}

	/**
	 * 处理文本中的换行符. Description:
	 * 
	 * @Version1.0 2014-7-18 下午3:57:10 by 吉磊（jilei@dangdang.com）创建
	 * @param html
	 * @return
	 */
	public static String formatTextData(String textData) {
		if (StringUtils.isEmpty(textData)) {
			return textData;
		}
		return html2Str(textData.replaceAll("[\r|\n]", ""));
	}

	/**
	 * 过滤掉HTML标签获取纯文本内容
	 * 
	 * @param inputString
	 * @return
	 */
	public static String Html2Text(String inputString) {
		if (StringUtils.isBlank(inputString)) {
			return "";
		}

		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		Pattern p_script;
		Matcher m_script;
		Pattern p_style;
		Matcher m_style;
		Pattern p_html;
		Matcher m_html;

		try {
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			// 替换“&nbsp;”
			textStr = htmlStr.replaceAll("&nbsp;", " ");

		} catch (Exception e) {
			log.error("FormatUtils_Html2Text_method_invoke_error:", e);
		}
		return textStr.trim();
	}

	private static String html2Str(String html) {
		try {
			Document doc = Jsoup.parse(html);
			return doc.body().text();
		} catch (Exception ex) {
			log.error("转换html内容出现错误", ex);
			return null;
		}
	}
	
	public static String filterStr( final String str){
		if ( StringUtils.isBlank(str)){
			return str;
		}
		Matcher m = Pattern.compile("([0-9a-zA-Z\\u4e00-\\u9fa5]+)").matcher(str);
        StringBuffer sub=new StringBuffer();
        while (m.find()) {
        	sub.append(m.group(0));
        }
        return sub.toString();
	}
	
}
