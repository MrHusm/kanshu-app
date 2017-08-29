package com.yxsd.kanshu.search.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yxsd.kanshu.base.contants.SearchContants;
import com.yxsd.kanshu.base.contants.SearchEnum;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.service.IBookService;
import com.yxsd.kanshu.search.manager.IndexManager;

/**
 * 
 * @author qiong.wang
 *
 */
@Controller
@RequestMapping("search")
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Resource(name = "bookService")
	IBookService bookService;

	@RequestMapping("searchIndex")
	public String searchIndex(HttpServletResponse response, HttpServletRequest request, Model model) {

		return "search";
	}

	@RequestMapping("search")
	public String search(HttpServletResponse response, HttpServletRequest request, Model model) {

		// 入参
		String searchText = request.getParameter("searchText");
		String field = request.getParameter("fields");

		// 查询为空直接返回
		if (StringUtils.isBlank(searchText)) {
			return "/search/searchNotResult";
		}

		logger.info("search被调用，条件为：" + searchText);

		try {
			String[] fields = null;
			if (StringUtils.isBlank(field)) {
				fields = field.split(",");
			} else {
				SearchEnum[] searchEnums = SearchEnum.values();
				fields = new String[searchEnums.length];

				for (int i = 0; i < searchEnums.length; i++) {
					fields[i] = searchEnums[i].getSearchField();
				}
			}

			List<Map<String, String>> maps = IndexManager.getManager().searchIndex(searchText.trim(), fields);

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

					Book book = this.bookService.get(Long.parseLong(id));
					if (book != null) {
						books.add(book);
					}

				}
				model.addAttribute("searchBooks", books);

				return "/search/searchResult";

			} else {
				return "/search/searchNotResult";
			}
		} catch (Exception e) {
			logger.error("search出错，条件为：" + searchText, e);
		}
		// 返回为空界面
		return "/search/searchNotResult";

	}
}
