package com.yxsd.kanshu.search.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yxsd.kanshu.base.contants.SearchContants;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.job.controller.YuewenJobController;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.service.IBookService;
import com.yxsd.kanshu.search.manager.IndexManager;
import com.yxsd.kanshu.search.service.IndexService;
import com.yxsd.kanshu.search.task.CreateIndexTask;

@Service(value = "indexService")
public class IndexServiceImpl implements IndexService {

	private static final Logger logger = LoggerFactory.getLogger(CreateIndexTask.class);

	@Resource(name = "bookService")
	private IBookService bookService;

	@Override
	public void createIndex(int start, int pageSize, int end) {
		logger.info("开始创建索引");

		Query query = new Query();
		query.setPage(start);
		query.setPageSize(pageSize);
		PageFinder<Book> pageFinder = bookService.findPageFinderObjs(null, query);
		if (pageFinder != null && pageFinder.getData() != null && pageFinder.getData().size() > 0) {
			if (!createIndex(pageFinder)) {
				logger.info("创建索引失败，停止创建,current pageno=" + pageFinder.getPageNo());
				return;
			}

		} else {
			return;
		}

		int total = pageFinder.getRowCount();

		if (total > pageFinder.getPageSize() && pageFinder.getPageNo() < end) {

			for (int i = 2; i < end; i++) {
				query.setPage(i);
				query.setPageSize(1000);
				pageFinder = bookService.findPageFinderObjs(null, query);
				if (!createIndex(pageFinder)) {
					logger.info("创建索引失败，停止创建,current pageno=" + pageFinder.getPageNo());
					return;
				}
			}

		}
	}

	@Override
	public void createIndex(int start, int end) {
		createIndex(start, 1000, end);
	}

	@Override
	public void createIndex(int start) {
		logger.info("开始创建索引");

		Query query = new Query();
		query.setPage(start);
		query.setPageSize(1000);
		PageFinder<Book> pageFinder = bookService.findPageFinderObjs(null, query);
		if (pageFinder != null && pageFinder.getData() != null && pageFinder.getData().size() > 0) {
			if (!createIndex(pageFinder)) {
				logger.info("创建索引失败，停止创建,current pageno=" + pageFinder.getPageNo());
				return;
			}

		} else {
			return;
		}

		int total = pageFinder.getRowCount();

		if (total > pageFinder.getPageSize()) {

			for (int i = 2; i < pageFinder.getPageCount(); i++) {
				query.setPage(i);
				query.setPageSize(1000);
				pageFinder = bookService.findPageFinderObjs(null, query);
				if (!createIndex(pageFinder)) {
					logger.info("创建索引失败，停止创建,current pageno=" + pageFinder.getPageNo());
					return;
				}
			}

		}
	}

	private boolean createIndex(PageFinder<Book> pageFinder) {
		if (pageFinder != null && pageFinder.getData() != null && pageFinder.getData().size() > 0) {
			try {
				logger.info("开始创建索引,start=" + pageFinder.getPageNo() + "页");
				for (Book book : pageFinder.getData()) {
					IndexManager.getManager().createIndex(String.valueOf(book.getBookId()), SearchContants.TABLENAME,
							YuewenJobController.setIndexField(book));
				}
				logger.info("结束创建索引,创建成功,start=" + pageFinder.getPageNo() + "页");
			} catch (Exception e) {
				logger.error("结束创建索引,创建失败,start=" + pageFinder.getPageNo() + "页", e);
				return false;

			}

		}
		return true;
	}

	@Override
	public void createIndex() {
		logger.info("开始创建索引");

		Query query = new Query();
		query.setPage(1);
		query.setPageSize(1000);
		PageFinder<Book> pageFinder = bookService.findPageFinderObjs(null, query);
		if (pageFinder != null && pageFinder.getData() != null && pageFinder.getData().size() > 0) {
			if (!createIndex(pageFinder)) {
				logger.info("创建索引失败，停止创建,current pageno=" + pageFinder.getPageNo());
				return;
			}

		} else {
			return;
		}

		int total = pageFinder.getRowCount();

		if (total > pageFinder.getPageSize()) {

			for (int i = 2; i < pageFinder.getPageCount(); i++) {
				query.setPage(i);
				query.setPageSize(1000);
				pageFinder = bookService.findPageFinderObjs(null, query);
				if (!createIndex(pageFinder)) {
					logger.info("创建索引失败，停止创建,current pageno=" + pageFinder.getPageNo());
					return;
				}
			}

		}
	}

}
