package com.yxsd.kanshu.search.service.impl;

import com.yxsd.kanshu.base.contants.SearchContants;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.job.controller.YuewenJobController;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.service.IBookService;
import com.yxsd.kanshu.search.manager.IndexManager;
import com.yxsd.kanshu.search.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service(value = "indexService")
public class IndexServiceImpl implements IndexService {

	private static final Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

	private static ThreadPoolExecutor createIndexPool;

	private static final int THREAD_POOL_KEEP_ALIVE_TIME = 300;
	private static final int THREAD_POOL_QUEUE_SIZE = 200;

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
			for (int i = start + 1; i < end; i++) {
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

			for (int i = start + 1; i < pageFinder.getPageCount(); i++) {
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
		logger.info("开始全局创建索引");
		if(createIndexPool==null){
			//创建线程池
			createIndexPool = new ThreadPoolExecutor(10, 10, THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>(THREAD_POOL_QUEUE_SIZE + 800), new ThreadPoolExecutor.CallerRunsPolicy());
		}
		List<Book> books = this.bookService.findListByParamsObjs(null);
		for (int i = 0; i< books.size(); i++) {
			final Book book = books.get(i);
			if(i % 1000 == 0){
				System.out.println(books.size());
				logger.info("索引线程池完成的任务数量：" + createIndexPool.getCompletedTaskCount() + ",队列中的数量：" + createIndexPool.getQueue().size());
				if (createIndexPool.getQueue().size() < 600) {
					logger.info("开始新的索引创建");
				} else {
					try {
						logger.info("睡眠10秒等待索引创建结束");
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			createIndexPool.execute(new Runnable() {
				@Override
				public void run() {
					try{
						IndexManager.getManager().createIndex(String.valueOf(book.getBookId()), SearchContants.TABLENAME,
								YuewenJobController.setIndexField(book));
					}catch (Exception e){
						e.printStackTrace();
						logger.info("图书创建索引失败："+book.getTitle());
					}
				}
			});
		}
		logger.info("结束全局创建索引");
	}
}
