package com.yxsd.kanshu.search.controller;

import com.yxsd.kanshu.base.contants.SearchContants;
import com.yxsd.kanshu.base.contants.SearchEnum;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.portal.model.DriveBook;
import com.yxsd.kanshu.portal.service.IDriveBookService;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.service.IBookService;
import com.yxsd.kanshu.search.manager.IndexManager;
import com.yxsd.kanshu.search.service.IndexService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author qiong.wang
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("search")
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Resource(name = "bookService")
	IBookService bookService;

	@Resource(name = "indexService")
	IndexService indexService;

	@Resource(name = "driveBookService")
	IDriveBookService driveBookService;

	/**
	 * 搜索首页
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("searchIndex")
	public String searchIndex(HttpServletResponse response, HttpServletRequest request, Model model) {
		String page = request.getParameter("page");
		String type = request.getParameter("type");
		String syn = request.getParameter("syn") == null ? "0" : request.getParameter("syn");
		model.addAttribute("syn", syn);
		model.addAttribute("type", type);
		Query query = new Query();
		if (StringUtils.isNotBlank(page)) {
			query.setPage(Integer.parseInt(page));
		} else {
			query.setPage(1);
		}
		query.setPageSize(20);
		PageFinder<DriveBook> pageFinder = driveBookService.findPageWithCondition(5, query);

		model.addAttribute("pageFinder", pageFinder);
		return "/search/search";
	}

	/**
	 * 创建搜索索引
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("createIndex")
	public String createIndex(HttpServletResponse response, HttpServletRequest request, Model model) {
		String start = request.getParameter("start");
		String pages = request.getParameter("pageSize");

		if (StringUtils.isBlank(start) && StringUtils.isBlank(pages)) {
			indexService.createIndex();
			return "/search/search";
		}
		if (!StringUtils.isBlank(start) && StringUtils.isBlank(pages)) {
			indexService.createIndex(Integer.parseInt(start));
			return "/search/search";
		}

		if (!StringUtils.isBlank(start) && !StringUtils.isBlank(pages)) {
			indexService.createIndex(Integer.parseInt(start), Integer.parseInt(pages));
			return "/search/search";
		}

		String end = request.getParameter("end");

		if (!StringUtils.isBlank(start) && !StringUtils.isBlank(pages) && !StringUtils.isBlank(end)) {
			indexService.createIndex(Integer.parseInt(start), Integer.parseInt(pages), Integer.parseInt(end));
			return "/search/search";
		}

		return "/search/search";

	}

	/**
	 * 搜索
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("search")
	public String search(HttpServletResponse response, HttpServletRequest request, Model model) {
		// 入参
		String searchText = request.getParameter("searchText");
		String field = request.getParameter("fields");
		String pageNo = request.getParameter("pageNo");
		String syn = request.getParameter("syn") == null ? "0" : request.getParameter("syn");
		model.addAttribute("syn", syn);

		// 未传page默认查首页
		if (StringUtils.isBlank(pageNo)) {
			pageNo = "1";
		}
		model.addAttribute("pageNo",Integer.parseInt(pageNo));
		try {
			// 查询为空直接返回
			if (StringUtils.isBlank(searchText)) {
				response.sendRedirect("/search/searchIndex.go?type=1");
				return null;
			}
			if (isMessyCode(searchText)) {
				searchText = new String(searchText.getBytes("ISO-8859-1"), "utf-8");
			}
			model.addAttribute("searchText",searchText);
			logger.info("search被调用，条件为：" + searchText);

			String[] fields = null;
			if (!StringUtils.isBlank(field)) {
				fields = field.split(",");
			} else {
				SearchEnum[] searchEnums = SearchEnum.values();
				fields = new String[searchEnums.length];
				for (int i = 0; i < searchEnums.length; i++) {
					fields[i] = searchEnums[i].getSearchField();
				}
			}
			List<Map<String, String>> maps = IndexManager.getManager().searchIndex(searchText.trim(), fields,
					Integer.parseInt(pageNo));

			if (maps != null && maps.size() > 0) {
				List<Book> books = new ArrayList<Book>();
				for (Map<String, String> map : maps) {
					String tableName = map.get(SearchContants.TABLENAME);
					String id = map.get(SearchContants.ID);
					if (StringUtils.isBlank(id)) {
						continue;
					}
					if (StringUtils.isBlank(tableName)) {
						tableName = SearchContants.BOOK;
					}
					Book book = this.bookService.getBookById(Long.parseLong(id));
					if (book != null) {
						books.add(book);
					}
				}
				model.addAttribute("searchBooks", books);
				return "/search/searchResult";
			} else {
				response.sendRedirect("/search/searchIndex.go?type=1");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("search出错，条件为：" + searchText, e);
			try {
				response.sendRedirect("/search/searchIndex.go?type=1");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		// 返回为空界面
		return null;

	}

	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = ch.length;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {

				if (!isChinese(c)) {
					count = count + 1;
				}
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

}
