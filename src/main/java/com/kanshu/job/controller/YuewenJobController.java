package com.kanshu.job.controller;

import com.alibaba.fastjson.JSON;
import com.kanshu.chapter.base.service.IChapterService;
import com.kanshu.job.vo.*;
import com.kanshu.kanshu.base.utils.ConfigPropertieUtils;
import com.kanshu.kanshu.base.utils.DateUtil;
import com.kanshu.kanshu.base.utils.HttpUtils;
import com.kanshu.kanshu.product.model.Book;
import com.kanshu.kanshu.product.model.Chapter;
import com.kanshu.kanshu.product.model.Volume;
import com.kanshu.kanshu.product.service.IBookService;
import com.kanshu.kanshu.product.service.IVolumeService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

/**
 * Description: 阅文拉取调用
 * All Rights Reserved.
 * @version 1.0  2017年8月16日 by hushengmeng
 */
@Transactional
@Service("yuewenJobController")
public class YuewenJobController{
	
	private Logger logger = LoggerFactory.getLogger(YuewenJobController.class);
	
	private static final String YUEWEN_APPKEY = "yuewen_appkey";
	private static final String YUEWEN_URL_BOOKCATEGORYLIST = "yuewen_url_bookcategorylist";
	private static final String YUEWEN_URL_BOOKLIST = "yuewen_url_booklist";
	private static final String YUEWEN_PROVIDER_CODE = "yuewen_provider_code";
	private static final String YUEWEN_URL_BOOKINFO = "yuewen_url_bookinfo";
	
    private static final String YUEWEN_URL_VOLUMESINFO = "yuewen_url_volumesInfo";
    private static final String YUEWEN_URL_VOLUMEINFO = "yuewen_url_volumeInfo";
    private static final String YUEWEN_URL_CHAPTERSINFO = "yuewen_url_chaptersInfo";
    private static final String YUEWEN_URL_BOOKCHAPTERSINFO = "yuewen_url_bookChaptersInfo";
    private static final String YUEWEN_URL_CHAPTERINFO = "yuewen_url_chapterInfo";
    private static final String YUEWEN_URL_CHAPTERCONTENTINFO = "yuewen_url_chapterContentInfo";
    private static final String YUEWEN_URL_UPDATE_BOOKS = "yuewen_url_update_books";
    
    private static final String YUEWEN_PROVIDER_KEY = "yuewen_provider_key";


    private static ThreadPoolExecutor pullBookPool;
    private static ThreadPoolExecutor handleFailureBooksPool;
    private static ThreadPoolExecutor updateBooksPool;
	private static final int COREPOOL_SIZE = 10;
	private static final int THREAD_POOL_KEEP_ALIVE_TIME = 300;
	private static final int THREAD_POOL_QUEUE_SIZE = 200;

	@Resource
	private IBookService bookService;
	@Resource
	private IVolumeService volumeService;
	@Resource
	private IChapterService chapterService;

	/**
	 * 
	 * @Title: getCategorys 
	 * @Description: 获取分类信息 
	 * @author hushengmeng
	 * @throws IOException 
	 */
	public void getCategorys(HttpServletRequest request, HttpServletResponse response) throws IOException{
		logger.info("yuewen getCategorys begin!");
		String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
		String token = getYueWenToken();
		String bookcategorylistUrl = ConfigPropertieUtils.getString(YUEWEN_URL_BOOKCATEGORYLIST);
		bookcategorylistUrl = MessageFormat.format(bookcategorylistUrl, appKey, token);
		logger.info("yuewen getCategorys url={}", bookcategorylistUrl);
		String result = HttpUtils.getContent(bookcategorylistUrl, "UTF-8");
		logger.info("yuewen getCategorys result={}", result);
		String types = JSON.parseObject(result).getJSONObject("result").getString("types");
		List<CategoryResp> categoryResps = JSON.parseArray(types, CategoryResp.class);
		//TODO
		//保存分类表
		logger.info("yuewen getCategorys end!");
	}
	
	/**
	 * 
	 * @Title: pullBooks 
	 * @Description: 拉取图书
	 * @return 
	 * @author hushengmeng
	 */
	public void pullBooks(HttpServletRequest request) {
		logger.info("yuewen pullBooks begin!");
		String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
		String token = getYueWenToken();
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer pageNo = null;
//		//获取已拉取阅文的图书数量，获取本次调度拉取的页数
//		DistributePullBook pullBook = new DistributePullBook();
//		pullBook.setProviderCode(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE));
//		int count = distributePullBookService.findCount(pullBook);
//		pageNo = count/pageSize + 1;
		String bookListUrl = ConfigPropertieUtils.getString(YUEWEN_URL_BOOKLIST);
		bookListUrl = MessageFormat.format(bookListUrl, appKey, token, pageNo.toString(), pageSize);
		logger.info("yuewen booklist url={}", bookListUrl);
		String result = HttpUtils.getContent(bookListUrl, "UTF-8");
		logger.info("yuewen booklist result={}", result);
		if(StringUtils.isBlank(result)){
			logger.error("yuewen booklist result empty!");
		}else{
			String returnCode = JSON.parseObject(result).getString("returnCode");
			if("0".equals(returnCode)){
				String[] cbids = JSON.parseObject(result).getJSONObject("result").getJSONArray("cbids").toArray(new String[pageSize]);
				if(cbids != null && cbids.length > 0){
					if(pullBookPool == null){
						//创建线程池
						pullBookPool = new ThreadPoolExecutor(COREPOOL_SIZE, COREPOOL_SIZE, THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS, 
								new LinkedBlockingQueue<Runnable>(THREAD_POOL_QUEUE_SIZE + 100), new CallerRunsPolicy());
					}
					List<String> tempList = new ArrayList<String>();
					for (String cbid : cbids) {
						if(StringUtils.isNotBlank(cbid)){
							tempList.add(cbid);
						}
					}
					cbids = tempList.toArray(new String[0]);
					if(cbids.length != pageSize.intValue()){
						//判断是否拉取过
						for (int i = 0; i < cbids.length; i++) {
							logger.info("ifpullBook params:providerCode={},providerBookId={}", ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
									cbids[i]);
							//DistributePullBook pullBookFromDB = distributePullBookService.findUniqueByParams("providerCode", ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE),
							//		"providerBookId", cbids[i]);
							//if(pullBookFromDB != null){
							//	cbids[i] = "";
							//}
						}
					}
					for (final String cbid : cbids) {
						pullBookPool.execute(new Runnable() {
							
							@Override
							public void run() {
								try {
									if(StringUtils.isNotBlank(cbid)){
										//调用阅文接口获取书籍信息并添加到分销平台
										BookInfoResp bookInfoResp = getBookByYuewen(cbid);
										/**if(bookInfoResp != null){
											//调用分销平台添加书接口
											Book book = setBook(bookInfoResp);
											String addBookResult = bookService.saveBookByHttp(book, ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "addBook");
											if(StringUtils.isBlank(addBookResult)){
												int pullStatus = 0;
												String pullFailureCause = "调用分销平台增加图书接口：返回空！";
												distributePullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE),
														cbid, pullStatus, pullFailureCause);
											}else{
												String code = JSON.parseObject(addBookResult).getJSONObject("status").getString("code");
												if("0".equals(code)){
													Long bookId = JSON.parseObject(addBookResult).getJSONObject("data").getJSONObject("book").getLong("bookId");
													//调用阅文获取书籍卷列表
													List<VolumeInfoResp> volumeInfoResps = getVolumesFromYuewenByBookId(cbid);
													if(volumeInfoResps != null){
														if(volumeInfoResps.size() > 0){
															// 有卷的信息：调用卷的基本信息，获取卷的章节列表，章节的基本信息，章节内容
															addChapterByVolume(volumeInfoResps, bookId, cbid);
														}else{
															// 没有卷的信息：调用获取书的所有章节列表，章节的基本信息，章节内容
															addChapterByBook(bookId, cbid);
														}
													}
												}else{
													int pullStatus = 0;
													String pullFailureCause = "调用分销平台增加图书接口：" + JSON.parseObject(addBookResult).getJSONObject("status")
															.getString("message");
													distributePullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE),
															cbid, pullStatus, pullFailureCause);
												}
											}
										}**/
									}
								} catch (Exception e) {
									int pullStatus = 0;
									String pullFailureCause = "拉取异常：" + ExceptionUtils.getRootCauseMessage(e);
									distributePullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
											cbid, pullStatus, pullFailureCause);
								}
							}
						});
					}
				}
			}else{
				logger.error("yuewen booklist result error!");
			}
		}
		logger.info("yuewen pullBooks end!");
	}
	
	/**
	 * 
	 * @Title: updateBook 
	 * @Description: 更新书籍信息
	 * @author hushengmeng
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "updateBooks")
	@ResponseBody
	public void updateBook(HttpServletRequest request) throws UnsupportedEncodingException{
		logger.info("yuewen updateBooks begin!");
		String timeInterval = request.getParameter("timeInterval");
		String endTime = DateUtil.getCurrentDateTimeToStr2();
		String beginTime = DateUtil.getDateTime(DateUtil.addMinute(DateUtil.parseStringToDate(endTime), Integer.parseInt(timeInterval)));
		String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
		String token = getYueWenToken();
		String url = ConfigPropertieUtils.getString(YUEWEN_URL_UPDATE_BOOKS);
		url = MessageFormat.format(url, StringUtils.replace(beginTime, " ", "%20"), StringUtils.replace(endTime, " ", "%20"), appKey, token);
		logger.info("yuewen updateBooks url={}", url);
		String result = HttpUtils.getContent(url, "UTF-8");
		logger.info("yuewen updateBooks result={}", result);
		if(StringUtils.isBlank(result)){
			logger.info("yuewen updateBooks result empty!");
		}else{
			String returnCode = JSON.parseObject(result).getString("returnCode");
			if("-1".equals(returnCode)){
				logger.info("该时间段无书籍更新！");
			}else if("0".equals(returnCode)){
				String[] cbids = JSON.parseObject(result).getJSONObject("result").getJSONArray("cbids").toArray(new String[]{});
				if(cbids != null && cbids.length > 0){
					if(updateBooksPool == null){
						//创建线程池
						updateBooksPool = new ThreadPoolExecutor(COREPOOL_SIZE + 6, COREPOOL_SIZE + 6, THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS, 
								new LinkedBlockingQueue<Runnable>(THREAD_POOL_QUEUE_SIZE + 10000), new CallerRunsPolicy());
					}
					for (final String cbid : cbids) {
						updateBooksPool.execute(new Runnable() {
							
							@Override
							public void run() {
								logger.info("yuewen updateBooks cbid={} begin!", cbid);
								Book beforeBook = bookService.findMasterUniqueByParams("providerCode",
										ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), "providerBookId", cbid);
								if(beforeBook == null){
									logger.info("yuewen updateBooks cbid={} book null!", cbid);
								}else{
									//更新拉取的书籍信息
									BookInfoResp bookInfoResp = getBookByYuewen(cbid);
									if(bookInfoResp != null){
										//调用分销平台更新书接口
										Book book = setBook(bookInfoResp);
										String updateBookResult = bookService.updateBookByHttp(book, ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "updateBook");
										if(StringUtils.isBlank(updateBookResult)){
											int pullStatus = 0;
											String pullFailureCause = "调用分销平台更新图书接口：返回空！";
											distributePullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
													cbid, pullStatus, pullFailureCause);
										}else{
											String code = JSON.parseObject(updateBookResult).getJSONObject("status").getString("code");
											if("0".equals(code)){
												Long bookId = JSON.parseObject(updateBookResult).getJSONObject("data").getJSONObject("book").getLong("bookId");
												//调用阅文获取书籍卷列表
												List<VolumeInfoResp> volumeInfoResps = getVolumesFromYuewenByBookId(cbid);
												if(volumeInfoResps != null){
													if(volumeInfoResps.size() > 0){
														int ywjtVolumeCount = volumeInfoResps.get(0).getCount();
														//验证卷是否增加
														Volume volume = new Volume();
														volume.setProviderCode(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE));
														volume.setProviderBookId(cbid);
														int ddVolumeCount = volumeService.findCount(volume);
														logger.info("voluem ywjtcount={},ddcount={}", ywjtVolumeCount, ddVolumeCount);
														int addNewVolume = ywjtVolumeCount - ddVolumeCount;
														if(addNewVolume >= 0){
															volumeInfoResps = volumeInfoResps.subList(ddVolumeCount -1, volumeInfoResps.size());
														}
														// 有卷的信息：调用获取卷的章节列表，章节的基本信息，章节内容
														updateChapterByVolume(volumeInfoResps, bookId, cbid, ddVolumeCount);
													}else{
														// 没有卷的信息：调用获取书的所有章节列表，章节内容
														updateChapterByBook(bookId, cbid);
													}
												} 
												int pullStatus = 2;
												String pullFailureCause = "调用阅文更新书籍接口：重新拉取！";
												distributePullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
														cbid, pullStatus, pullFailureCause);
											}else{
												int pullStatus = 0;
												String pullFailureCause = "调用分销平台更新图书接口：" + JSON.parseObject(updateBookResult).getJSONObject("status")
														.getString("message");
												distributePullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
														cbid, pullStatus, pullFailureCause);
											}
										}
									}
								}
								logger.info("yuewen updateBooks cbid={} end!", cbid);
							}
						});
					}
				}else{
					logger.info("yuewen updateBooks result error!");
				}
			}
		}
		logger.info("yuewen updateBooks end!");
	}
	
	/**
	 * 
	 * @Title: handleFailureBooks 
	 * @Description: 处理失败图书 
	 * @author hushengmeng
	 */
	@RequestMapping(value="/handle/failbooks")
	@ResponseBody
	public void handleFailureBooks(HttpServletRequest request){
		String provideBookIds = request.getParameter("provideBookIds");
		String pageSizeStr = request.getParameter("pageSize");
		
		List<DistributePullBook> distributePullBooks = null;
		if(StringUtils.isBlank(provideBookIds)){
			//自动重新拉取图书
			Map<String, Object> pullBookMap = new HashMap<String, Object>();
			pullBookMap.put("providerCode", ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE));
			pullBookMap.put("pullStatus", 0);
			Query query = new Query(Integer.parseInt(pageSizeStr));
			PageFinder<DistributePullBook> distributePullBooksPage = distributePullBookService.findPageFinderObjs(pullBookMap, query);
			distributePullBooks = distributePullBooksPage.getData();
			//先处理状态拉取失败的作品，然后处理需要重新拉取的作品
			if(distributePullBooks.size() < Integer.parseInt(pageSizeStr)){
				pullBookMap.put("pullStatus", 2);
				query = new Query(Integer.parseInt(pageSizeStr) - distributePullBooks.size());
				PageFinder<DistributePullBook> distributePullBooksPageAgain = distributePullBookService.findPageFinderObjs(pullBookMap, query);
				distributePullBooks.addAll(distributePullBooksPageAgain.getData());
			}
		}else{
			//手动设置重新拉取图书
			provideBookIds = StringUtils.replace(provideBookIds, "，", ",");
			List<String> cbidList = Arrays.asList(provideBookIds.split(","));
			distributePullBooks = distributePullBookService.findByProviderBookIds(cbidList);
		}
		logger.info("handleFailureBooks count={}", distributePullBooks.size());
		if(handleFailureBooksPool == null){
			//创建线程池
			handleFailureBooksPool = new ThreadPoolExecutor(COREPOOL_SIZE-3, COREPOOL_SIZE-3, THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS, 
					new LinkedBlockingQueue<Runnable>(THREAD_POOL_QUEUE_SIZE + 10000), new CallerRunsPolicy());
		}
		for (DistributePullBook pullBook : distributePullBooks) {
			final String cbid = pullBook.getProviderBookId();
			handleFailureBooksPool.execute(new Runnable() {

				@Override
				public void run() {
					
					logger.info("yuewen handleFailureBooks cbid={} begin!", cbid);
					//处理失败的书籍信息
					BookInfoResp bookInfoResp = getBookByYuewen(cbid);
					if(bookInfoResp != null){
						//调用分销平台更新书接口
						Book book = setBook(bookInfoResp);
						Book beforeBook = bookService.findMasterUniqueByParams("providerCode", 
								ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), "providerBookId", cbid);
						String bookResult = "";
						if(beforeBook == null){
							bookResult = bookService.saveBookByHttp(book, ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "addBook");
						}else{
							bookResult = bookService.updateBookByHttp(book, ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "updateBook");
						}
						if(StringUtils.isBlank(bookResult)){
							int pullStatus = 0;
							String pullFailureCause = "调用分销平台增加或者更新图书接口：返回空！";
							distributePullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
									cbid, pullStatus, pullFailureCause);
						}else{
							String code = JSON.parseObject(bookResult).getJSONObject("status").getString("code");
							if("0".equals(code)){
								Long bookId = JSON.parseObject(bookResult).getJSONObject("data").getJSONObject("book").getLong("bookId");
								//调用阅文获取书籍卷列表
								List<VolumeInfoResp> volumeInfoResps = getVolumesFromYuewenByBookId(cbid);
								if(volumeInfoResps != null){
									if(volumeInfoResps.size() > 0){
										// 有卷的信息：调用获取卷的章节列表，章节的基本信息，章节内容
										updateChapterByVolume(volumeInfoResps, bookId, cbid, 1);
									}else{
										// 没有卷的信息：调用获取书的所有章节列表，章节内容
										updateChapterByBook(bookId, cbid);
									}
								} 
							}else{
								int pullStatus = 0;
								String pullFailureCause = "调用分销平台增加或者更新图书接口：" + JSON.parseObject(bookResult).getJSONObject("status")
										.getString("message");
								distributePullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
										cbid, pullStatus, pullFailureCause);
							}
						}
					}
					logger.info("yuewen handleFailureBooks cbid={} end!", cbid);
				}
				
			});
		}
	}
	
	/**
	 * 
	 * @Title: handleFailureVolumes 
	 * @Description: 处理失败卷
	 * @author hushengmeng
	 */
	@RequestMapping(value="/handle/failvolumes")
	@ResponseBody
	public void handleFailureVolumes(HttpServletRequest request){
		String provideVolumes = request.getParameter("provideVolumes");
		String pageSizeStr = request.getParameter("pageSize");
		List<DistributePullVolume> distributePullVolumes = null;
		if(StringUtils.isBlank(provideVolumes)){
			//自动重新拉取失败卷
			Map<String, Object> pullVolumeMap = new HashMap<String, Object>();
			pullVolumeMap.put("providerCode", ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE));
			pullVolumeMap.put("pullStatus", 0);
			Query query = new Query(Integer.parseInt(pageSizeStr));
			PageFinder<DistributePullVolume> distributePullVolumesPage = distributePullVolumeService.findPageFinderObjs(pullVolumeMap, query);
			distributePullVolumes = distributePullVolumesPage.getData();
		}else{
			//手动设置重新拉取卷
			provideVolumes = StringUtils.replace(provideVolumes, "，", ",");
			List<String> provideVolumesList = Arrays.asList(provideVolumes.split(","));
			distributePullVolumes = distributePullVolumeService.findByProvideVolumes(provideVolumesList);
		}
		
		logger.info("handleFailureVolumes count={}", distributePullVolumes.size());
		for (DistributePullVolume pullVolume : distributePullVolumes) {
			String cbid = pullVolume.getProviderBookId();
			String cvid = pullVolume.getProviderVolumeId();
			logger.info("yuewen handleFailureVolumes cbid={}, cvid={} begin!", cbid, cvid);
			//处理失败的卷信息
			//获取该图书的所有卷列表
			List<VolumeInfoResp> volumeInfoResps = getVolumesFromYuewenByBookId(cbid);
			//调用阅文获取卷基本信息接口
			VolumeInfoResp volumeInfoResp = getVolumeFromYuewenByVolumeId(cbid, cvid);
			int volumeIndex = 0;
			for (int i = 0; i < volumeInfoResps.size(); i++) {
				if(volumeInfoResp.getcVID().longValue() == volumeInfoResps.get(i).getcVID().longValue()){
					volumeIndex = i+1;
					break;
				}
			}
			if(volumeInfoResp != null && volumeIndex > 0){
				int volumePullStatus = 1;
				String volumePullFailureCause = "";
				//调用分销平台添加卷接口
				Volume volume = setVolume(volumeInfoResp, null, volumeIndex);
				//查询是否拉取过该卷
				Volume beforeVolume = volumeService.findMasterUniqueByParams("providerCode", 
						ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), "providerBookId", cbid,
						"providerVolumeId", volumeInfoResp.getcVID().toString());
				String volumeResult = "";
				if(beforeVolume == null){
					volumeResult = volumeService.saveVolumeByHttp(volume);
				}else{
					volumeResult = volumeService.updateVolumeByHttp(volume);
				}
				if(StringUtils.isBlank(volumeResult)){
					// 记录卷日志
					volumePullStatus = 0;
					volumePullFailureCause = "调用分销平台增加卷或者更新卷接口：返回为空！";
					distributePullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
							cbid, volumeInfoResp.getcVID().toString(), volumePullStatus, volumePullFailureCause);
				}else{
					String volumeCode = JSON.parseObject(volumeResult).getJSONObject("status").getString("code");
					
					if("0".equals(volumeCode)){
						distributePullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
								cbid, volumeInfoResp.getcVID().toString(), volumePullStatus, volumePullFailureCause);
						int chapterPage = 1;
						int chapterPageSize = 10;
						while (true) {
							List<ChapterInfoResp> chapterInfoResps = getChaptersFromYuewenByVolumeId(cbid, volumeInfoResp.getcVID().toString()
									, chapterPage, chapterPageSize);
							if(chapterInfoResps != null && chapterInfoResps.size() > 0){
								for (ChapterInfoResp chapterInfoResp : chapterInfoResps) {
									//调用获取章节内容接口
									ChapterContentResp chapterContentResp = getChapterContentFromYuewenByChapterId(cbid, chapterInfoResp.getcCID().toString(),
											chapterInfoResp.getcVID().toString());
									if(chapterContentResp != null){
										//调用分销平台增加或者更新章节接口
										Long volumeId = JSON.parseObject(volumeResult).getJSONObject("data").getLong("volumeId");
										Chapter chapter = setChapter(chapterInfoResp, null, volumeId,
												chapterContentResp.getContent(), volumeIndex);
										//查询是否拉取过该章节
										logger.info("providerCode="+ ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE) + ",providerBookId="+ cbid 
												+ ",providerChapterId=" + chapterInfoResp.getcCID());
										Chapter beforeChapter = chapterService.findMasterUniqueByParams("providerCode", 
												ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), "providerBookId", cbid,
												"providerChapterId", chapterInfoResp.getcCID().toString());
										String chapterResult = "";
										if(beforeChapter == null){
											chapterResult = chapterService.saveChapterByHttp(chapter, 
													ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "addChapter");
										}else{
											chapterResult = chapterService.updateChapterByHttp(chapter, 
													ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "updateChapter");
										}
										int chapterPullStatus = 1;
										String chapterPullFailureCause = "";
										if(StringUtils.isBlank(chapterResult)){
											chapterPullStatus = 0;
											chapterPullFailureCause = "调用分销平台增加或者更新章节接口：返回为空！";
										}else{
											String chapterCode = JSON.parseObject(chapterResult).getJSONObject("status").getString("code");
											if(!"0".equals(chapterCode)){
												chapterPullStatus = 0;
												chapterPullFailureCause = "调用分销平台增加或者更新章节接口：" + JSON.parseObject(chapterResult).
														getJSONObject("status").getString("message");
											}
										}
										distributePullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, chapterInfoResp.getcVID().toString(), 
												chapterInfoResp.getcCID().toString(), chapterPullStatus, chapterPullFailureCause);
									}
								}
								chapterPage++;
							}
							if(chapterInfoResps == null || chapterInfoResps.size() == 0 
									|| chapterInfoResps.size() != 10){
								break;
							}
						}
					}else{
						// 记录卷日志
						volumePullStatus = 0;
						volumePullFailureCause = "调用分销平台增加卷或者更新卷接口：" + JSON.parseObject(volumeResult).getJSONObject("status").getString("message");
						distributePullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
								cbid, volumeInfoResp.getcVID().toString(), volumePullStatus, volumePullFailureCause);
					}
				}
			}
			logger.info("yuewen handleFailureVolumes cbid={}, cvid={} end!", cbid, cvid);
		}
	}
	
	/**
	 * 
	 * @Title: handleFailureChapter 
	 * @Description: 处理失败的章节
	 * @author hushengmeng
	 */
	@RequestMapping(value = "/handle/failchapters")
	@ResponseBody
	public void handleFailureChapter(HttpServletRequest request){
		String provideChapters = request.getParameter("provideChapters");
		String pageSizeStr = request.getParameter("pageSize");
		List<DistributePullChapter> distributePullChapters = null;
		if(StringUtils.isBlank(provideChapters)){
			//自动获取创建时间在一天内失败的章节
			Map<String, Object> pullChapterMap = new HashMap<String, Object>();
			pullChapterMap.put("providerCode", ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE));
			pullChapterMap.put("pullStatus", 0);
			pullChapterMap.put("pullFailureCause1", "章节已存在");
			pullChapterMap.put("pullFailureCause2", "章节不存在");
			Query query = new Query(Integer.parseInt(pageSizeStr));
			PageFinder<DistributePullChapter> distributePullChaptersPage = distributePullChapterService.findPageFinderObjs(pullChapterMap, query);
			distributePullChapters = distributePullChaptersPage.getData();
		}else{
			//手动设置重新拉取的章节
			provideChapters = StringUtils.replace(provideChapters, "，", ",");
			List<String> provideChaptersList = Arrays.asList(provideChapters.split(","));
			distributePullChapters = distributePullChapterService.findByProvideChapters(provideChaptersList);
		}
		
		logger.info("handleFailureChapter count={}", distributePullChapters.size());
		for (DistributePullChapter pullChapter : distributePullChapters) {
			String cbid = pullChapter.getProviderBookId();
			String cvid = pullChapter.getProviderVolumeId();
			String ccid = pullChapter.getProviderChapterId();
			//获取该图书的所有卷列表
			List<VolumeInfoResp> volumeInfoResps = getVolumesFromYuewenByBookId(cbid);
			int volumeIndex = 0;
			for (int i = 0; i < volumeInfoResps.size(); i++) {
				if(Long.parseLong(cvid) == volumeInfoResps.get(i).getcVID().longValue()){
					volumeIndex = i+1;
					break;
				}
			}
			//获取章节基本信息接口
			ChapterInfoResp chapterInfoResp = getChapterFromYuewenByChapterId(cbid, ccid, cvid);
			//调用获取章节内容接口
			ChapterContentResp chapterContentResp = getChapterContentFromYuewenByChapterId(cbid, ccid, cvid);
			if(chapterInfoResp != null && chapterContentResp != null && volumeIndex > 0){
				//调用分销平台增加或者更新章节接口
				Chapter chapter = setChapter(chapterInfoResp, null, null, 
						chapterContentResp.getContent(), volumeIndex);
				//查询是否拉取过该章节
				logger.info("beforeChapter params:providerBookId={},providerChapterId={}", cbid, ccid);
				Chapter beforeChapter = chapterService.findMasterUniqueByParams("providerCode", 
						ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), "providerBookId", cbid,
						"providerChapterId", ccid);
				String chapterResult = "";
				if(beforeChapter == null){
					chapterResult = chapterService.saveChapterByHttp(chapter, 
							ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "addChapter");
				}else{
					chapterResult = chapterService.updateChapterByHttp(chapter, 
							ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "updateChapter");
				}
				int chapterPullStatus = 1;
				String chapterPullFailureCause = "";
				if(StringUtils.isBlank(chapterResult)){
					chapterPullStatus = 0;
					chapterPullFailureCause = "调用分销平台增加或者更新章节接口：返回为空！";
				}else{
					String chapterCode = JSON.parseObject(chapterResult).getJSONObject("status").getString("code");
					if(!"0".equals(chapterCode)){
						chapterPullStatus = 0;
						chapterPullFailureCause = "调用分销平台增加或者更新章节接口：" + JSON.parseObject(chapterResult).
								getJSONObject("status").getString("message");
					}
				}
				distributePullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, cvid, 
						ccid, chapterPullStatus, chapterPullFailureCause);
			}
		}
		
	}
	
	/**
	 * 
	 * @Title: getBookByYuewen 
	 * @Description: 调用阅文接口获取书籍信息
	 * @param cbid 
	 * @author hushengmeng
	 */
	private BookInfoResp getBookByYuewen(String cbid){
		BookInfoResp bookInfoResp = null;
		if(StringUtils.isBlank(cbid)){
			logger.info("yuewen bookinfo params cbid empty!");
		}else{
			
			int pullStatus = 1;
			String pullFailureCause = "";
			
			String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
			String token = getYueWenToken();
			String bookInfoUrl = ConfigPropertieUtils.getString(YUEWEN_URL_BOOKINFO);
			bookInfoUrl = MessageFormat.format(bookInfoUrl, cbid, appKey, token);
			logger.info("yuewen bookinfo url={}", bookInfoUrl);
			String result="";
			try {
				result = HttpUtils.getContent(bookInfoUrl, "UTF-8");
				logger.info("yuewen bookinfo result={}", result);
			} catch (Exception e) {
				logger.error("yuewen bookinfo interface error!", ExceptionUtils.getFullStackTrace(e));
				pullStatus = 0;
				pullFailureCause = "调用阅文获取图书接口异常";
			}
			if(StringUtils.isNotBlank(result)){
				String returnCode = JSON.parseObject(result).getString("returnCode");
				if("0".equals(returnCode)){
					// 调用接口成功
					String book = JSON.parseObject(result).getJSONObject("result").getString("book");
					bookInfoResp = JSON.parseObject(book, BookInfoResp.class);
				}else{
					String returnMsg = JSON.parseObject(result).getString("returnMsg");
					if(returnMsg.contains("书籍被过滤")){
						pullStatus = 1;
					}else{
						pullStatus = 0;
					}
					pullFailureCause = "调用阅文获取图书接口：" + returnMsg;
				}
			}
			//distributePullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, pullStatus, pullFailureCause);
		}
		return bookInfoResp;
	}

    /**
     *
     * @Title: getVolumesFromYuewenByBookId
     * @Description: 列出书藉卷列表
     * @param cbid
     * @author hushengmeng
     */
    private List<VolumeInfoResp> getVolumesFromYuewenByBookId(String cbid){
    	List<VolumeInfoResp> resultVolumeInfoResps = null;
        if(StringUtils.isBlank(cbid)){
            logger.info("yuewen BookVolumesInfos params cbid empty!");
            return null;
        }
        int pullStatus = 1;
        String pullFailureCause = "";
        String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
        String token = getYueWenToken();
        String volumesInfoUrl = ConfigPropertieUtils.getString(YUEWEN_URL_VOLUMESINFO);
        volumesInfoUrl = MessageFormat.format(volumesInfoUrl, cbid, appKey, token);
        logger.info("yuewen BookVolumesInfos url={}", volumesInfoUrl);
        String result = "";
        try {
			result = HttpUtils.getContent(volumesInfoUrl, "UTF-8");
			logger.info("yuewen BookVolumesInfos result={}", result);
		} catch (Exception e) {
			logger.error("yuewen BookVolumesInfos interface error!", ExceptionUtils.getFullStackTrace(e));
			pullStatus = 0;
			pullFailureCause = "调用阅文获取书藉卷列表接口异常";
		}
        if(StringUtils.isNotBlank(result)){
        	 String returnCode = JSON.parseObject(result).getString("returnCode");
             if("0".equals(returnCode)){
                 // 调用接口成功
                 String resultData = JSON.parseObject(result).getJSONObject("result").getString("resultData");
                 resultVolumeInfoResps = JSON.parseArray(resultData, VolumeInfoResp.class);
                 if(resultVolumeInfoResps != null && resultVolumeInfoResps.size() > 0){
                	 int count = JSON.parseObject(result).getJSONObject("result").getIntValue("pageCount");
                     resultVolumeInfoResps.get(0).setCount(count);
                 }
             }else{
             	pullStatus = 0;
     			pullFailureCause = "调用阅文获取书藉卷列表接口：" + JSON.parseObject(result).getString("returnMsg");
             }
        }
        //distributePullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, pullStatus, pullFailureCause);

        return  resultVolumeInfoResps;
    }

    /**
     *
     * @Title: getVolumeFromYuewenByVolumeId
     * @Description: 获取卷信息
     * @param cbid
     * @author hushengmeng
     */
    private VolumeInfoResp getVolumeFromYuewenByVolumeId(String cbid,String cvid){
        if(StringUtils.isBlank(cbid)){
            logger.info("yuewen VolumeInfo params cbid empty!");
            return null;
        }
        if(StringUtils.isBlank(cvid)){
            logger.info("yuewen VolumeInfo params cvid empty!");
            return null;
        }
        int pullStatus = 1;
        String pullFailureCause = "";
        VolumeInfoResp volumeInfoResp = null;
        String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
        String token = getYueWenToken();
        String volumeInfoUrl = ConfigPropertieUtils.getString(YUEWEN_URL_VOLUMEINFO);
        volumeInfoUrl = MessageFormat.format(volumeInfoUrl, cbid, cvid,appKey, token);
        logger.info("yuewen VolumeInfo url={}", volumeInfoUrl);
        String result = "";
        try {
			result = HttpUtils.getContent(volumeInfoUrl, "UTF-8");
			logger.info("yuewen VolumeInfo result={}", result);
		} catch (Exception e) {
			logger.error("yuewen VolumeInfo interface error!", ExceptionUtils.getFullStackTrace(e));
			pullStatus = 0;
			pullFailureCause = "调用阅文获取卷信息接口异常";
		}
        String returnCode = JSON.parseObject(result).getString("returnCode");
        if("0".equals(returnCode)){
            // 调用接口成功
            String resultData = JSON.parseObject(result).getJSONObject("result").getString("resultData");
            volumeInfoResp = JSON.parseObject(resultData, VolumeInfoResp.class);
        }else{
        	pullStatus = 0;
 			pullFailureCause = "调用阅文获取卷信息接口：" + JSON.parseObject(result).getString("returnMsg");
        }
        distributePullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
				cbid, cvid, pullStatus, pullFailureCause);
        return volumeInfoResp;
    }

    /**
     *
     * @Title: getChaptersFromYuewenByVolumeId
     * @Description: 卷的章节列表
     * @param cbid
     * @author hushengmeng
     */
    private List<ChapterInfoResp> getChaptersFromYuewenByVolumeId(String cbid,String cvid,int pageNo,int pageSize){
        if(StringUtils.isBlank(cbid)){
            logger.info("yuewen VolumeChaptersInfo params cbid empty!");
            return null;
        }
        if(StringUtils.isBlank(cvid)){
            logger.info("yuewen VolumeChaptersInfo params cvid empty!");
            return null;
        }
        List<ChapterInfoResp> resultChapterInfoResps = null;
        int volumePullStatus = 1;
        String volumePullFailureCause = "";
        String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
        String token = getYueWenToken();
        String chaptersInfoUrl = ConfigPropertieUtils.getString(YUEWEN_URL_CHAPTERSINFO);
        chaptersInfoUrl = MessageFormat.format(chaptersInfoUrl, cbid,cvid, appKey, token,pageNo, pageSize);
        logger.info("yuewen VolumeChaptersInfo url={}", chaptersInfoUrl);
        String result = "";
        try {
			result = HttpUtils.getContent(chaptersInfoUrl, "UTF-8");
			logger.info("yuewen VolumeChaptersInfo result={}", result);
		} catch (Exception e) {
			logger.error("yuewen VolumeChaptersInfo interface error!", ExceptionUtils.getFullStackTrace(e));
			volumePullStatus = 0;
			volumePullFailureCause = "调用阅文获取卷的章节列表接口异常";
		}
        if(StringUtils.isNotBlank(result)){
        	String returnCode = JSON.parseObject(result).getString("returnCode");
            if("0".equals(returnCode)){
                // 调用接口成功
                String resultData = JSON.parseObject(result).getJSONObject("result").getString("resultData");
                resultChapterInfoResps = JSON.parseArray(resultData, ChapterInfoResp.class);
            }else{
            	volumePullStatus = 0;
    			volumePullFailureCause = "调用阅文获取卷的章节列表接口：" + JSON.parseObject(result).getString("returnMsg");
            }
        }
        distributePullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
				cbid, cvid, volumePullStatus, volumePullFailureCause);
        return  resultChapterInfoResps;
    }
    /**
     *
     * @Title: getChaptersFromYuewenByBookId
     * @Description: 书的章节列表
     * @param cbid
     * @author hushengmeng
     */
    private List<ChapterInfoResp> getChaptersFromYuewenByBookId(String cbid,int pageNo,int pageSize){
        if(StringUtils.isBlank(cbid)){
            logger.info("yuewen BookChaptersInfo params cbid empty!");
            return null;
        }
        List<ChapterInfoResp> resultChapterInfoResps = null;
        int pullStatus = 0;
        String pullFailureCause = "";
        String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
        String token = getYueWenToken();
        String bookChaptersInfoUrl = ConfigPropertieUtils.getString(YUEWEN_URL_BOOKCHAPTERSINFO);
        bookChaptersInfoUrl = MessageFormat.format(bookChaptersInfoUrl, cbid, appKey, token,pageNo, pageSize);
        logger.info("yuewen BookChaptersInfo url={}", bookChaptersInfoUrl);
        String result = "";
        try {
			result = HttpUtils.getContent(bookChaptersInfoUrl, "UTF-8");
			logger.info("yuewen BookChaptersInfo result={}", result);
		} catch (Exception e) {
			logger.error("yuewen BookChaptersInfo interface error!", ExceptionUtils.getFullStackTrace(e));
			pullStatus = 0;
			pullFailureCause = "调用阅文获取书的章节列表接口异常";
		}
        if(StringUtils.isNotBlank(result)){
        	String returnCode = JSON.parseObject(result).getString("returnCode");
            if("0".equals(returnCode)){
                // 调用接口成功
                String resultData = JSON.parseObject(result).getJSONObject("result").getString("resultData");
                resultChapterInfoResps = JSON.parseArray(resultData, ChapterInfoResp.class);
            }else{
            	pullStatus = 0;
    			pullFailureCause = "调用阅文获取书藉章节列表接口：" + JSON.parseObject(result).getString("returnMsg");
            }
        }
        distributePullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, pullStatus, pullFailureCause);
        return  resultChapterInfoResps;
    }

    /**
     *
     * @Title: getChapterFromYuewenByChapterId
     * @Description: 章节信息
     * @param cbid
     * @author hushengmeng
     */
    private ChapterInfoResp getChapterFromYuewenByChapterId(String cbid,String ccid, String cvid ){
        if(StringUtils.isBlank(cbid)){
            logger.info("yuewen ChapterInfo params cbid empty!");
            return null;
        }
        if(StringUtils.isBlank(ccid)){
            logger.info("yuewen ChapterInfo params ccid empty!");
            return null;
        }
        int pullStatus = 0;
        String pullFailureCause = "";
        ChapterInfoResp chapterInfoResp = null;
        String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
        String token = getYueWenToken();
        String chapterInfoUrl = ConfigPropertieUtils.getString(YUEWEN_URL_CHAPTERINFO);
        chapterInfoUrl = MessageFormat.format(chapterInfoUrl, cbid,ccid ,appKey, token);
        logger.info("yuewen ChapterInfo url={}", chapterInfoUrl);
        String result = "";
        try {
			result = HttpUtils.getContent(chapterInfoUrl, "UTF-8");
			logger.info("yuewen ChapterInfo result={}", result);
		} catch (Exception e) {
			logger.error("yuewen ChapterInfo interface error!", ExceptionUtils.getFullStackTrace(e));
			pullStatus = 0;
			pullFailureCause = "调用阅文获取章节基本信息接口异常";
		}
        String returnCode = JSON.parseObject(result).getString("returnCode");
        if("0".equals(returnCode)){
            // 调用接口成功
            String resultData = JSON.parseObject(result).getJSONObject("result").getString("resultData");
            chapterInfoResp = JSON.parseObject(resultData, ChapterInfoResp.class);
        }else{
        	pullStatus = 0;
			pullFailureCause = "调用阅文获取章节基本信息接口：" + JSON.parseObject(result).getString("returnMsg");
        }
        distributePullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, cvid, 
				ccid, pullStatus, pullFailureCause);
        return chapterInfoResp;
    }


    /**
     *
     * @Title: getChapterContentFromYuewenByChapterId
     * @Description: 章节内容
     * @param cbid
     * @author hushengmeng
     */
    private ChapterContentResp getChapterContentFromYuewenByChapterId(String cbid, String ccid, String cvid ){
        if(StringUtils.isBlank(cbid)){
            logger.info("yuewen ChapterContentInfo params cbid empty!");
            return null;
        }
        if(StringUtils.isBlank(ccid)){
            logger.info("yuewen ChapterContentInfo params ccid empty!");
            return null;
        }
        ChapterContentResp chapterContentResp = null;
        int pullStatus = 1;
        String pullFailureCause = "";
        String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
        String token = getYueWenToken();
        String chapterInfoUrl = ConfigPropertieUtils.getString(YUEWEN_URL_CHAPTERCONTENTINFO);
        chapterInfoUrl = MessageFormat.format(chapterInfoUrl, cbid,ccid ,appKey, token);
        logger.info("yuewen ChapterContentInfo url={}", chapterInfoUrl);
        String result = "";
        try {
			result = HttpUtils.getContent(chapterInfoUrl, "UTF-8");
			logger.info("yuewen ChapterContentInfo result={}", JSON.parseObject(result).getString("returnCode"));
		} catch (Exception e) {
			logger.error("yuewen ChapterContentInfo interface error!", ExceptionUtils.getFullStackTrace(e));
			pullStatus = 0;
			pullFailureCause = "调用阅文获取书的章节内容接口异常";
		}
        if(StringUtils.isNotBlank(result)){
        	String returnCode = JSON.parseObject(result).getString("returnCode");
            if("0".equals(returnCode)){
                // 调用接口成功
                String resultData = JSON.parseObject(result).getJSONObject("result").getString("content");
                chapterContentResp = JSON.parseObject(resultData, ChapterContentResp.class);
            }else{
            	pullStatus = 0;
    			pullFailureCause = "调用阅文获取书藉章节内容接口：" + JSON.parseObject(result).getString("returnMsg");
            }
        }
        distributePullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, cvid, 
				ccid, pullStatus, pullFailureCause);
        return  chapterContentResp;
    }
	/*
	 * token按照文档里的获取方法获取
		token生成规则：
		1.取appkey的最后四位，后面加上yyyyMMdd格式的当日日期，组成字符串
		2.用上述拼起来的字符串生成MD5值（32位 小写）；
		3.取生成的MD5值的前12位
	 */
	private String getYueWenToken() {
		String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
		StringBuffer sbf = new StringBuffer();
		String nowDate = DateUtil.formatDateByFormat(new Date(), "yyyyMMdd");
		sbf.append(appKey.substring(appKey.length()-4,appKey.length()));
		sbf.append(nowDate);
		String md5Str = MD5Utils.getInstance().cell32(sbf.toString());
		return md5Str.substring(0, 12);
	}
	
	/**
	 * 
	 * @Title: setBook 
	 * @Description: 设置book信息
	 * @param bookInfoResp
	 * @return 
	 * @author hushengmeng
	 */
	private Book setBook(BookInfoResp bookInfoResp){
		Book book = new Book();
//		book.setProviderCode(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE));// 是	供应商编码
//		book.setProviderName("阅文集团");// 是 供应商名称
//		book.setProviderBookId(String.valueOf(bookInfoResp.getcBID()));// 是	供应商作品id
//		book.setTitle(bookInfoResp.getTitle());// 是	作品名称
//		book.setDescription(bookInfoResp.getIntro());// 否	作品简介
//		book.setAuthorName(bookInfoResp.getAuthorname());// 是	作者名称
//		if(bookInfoResp.getVipstatus() == 1){
//			book.setIsFree(Book.IS_FREE_NO);
//		}else{
//			book.setIsFree(Book.IS_FREE_YES);
//		}
//		String coverUrl = bookInfoResp.getCoverurl();
//		try {
//			byte[] content = HttpUtils.getBytes(coverUrl);
//			if(content.length >= 1048576){
//				Map<String, Integer> map = ImageUtil.getWidthAndHeight(new ByteArrayInputStream(content));
//				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//				zipWidthHeightImageFile(new ByteArrayInputStream(content), outputStream, map.get("width"), map.get("height"), 0);
//				content = outputStream.toByteArray();
//			}
//			book.setCoverPicContent(Base64Utils.encodeBytes(content, Base64Utils.GZIP));// 是	封面图
//		} catch (Exception e) {
//			logger.error("providerBookId={},封面获取失败！", String.valueOf(bookInfoResp.getcBID()));
//		}
//		book.setKeyWords(bookInfoResp.getKeyword());// 否	作品关键词，多个关键词请以英文逗号（,）分隔。
//		if(bookInfoResp.getStatus() == 50){
//			book.setIsFull(Book.IS_FULL_YES);// 是	是否完本：1完本，0未完本。
//		}else{
//			book.setIsFull(Book.IS_FULL_NO);
//		}
//		book.setWordCnt(bookInfoResp.getAllwords());// 否	作品字数
//		List<String> categoryCodes = new ArrayList<String>();
//		String categoryCode = "";
//		try {
//			categoryCode = cpCategoryRelatedService.findListByParams("cpCode", ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), "cpCategoryId", bookInfoResp.getCategoryid()).get(0).getCategoryCode();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		categoryCodes.add(categoryCode);
//		book.setCategoryCodes(categoryCodes);// 是	当当读书作品分类code码
		return book;
	}
	
	/**
	 * 
	 * @Title: setVolume 
	 * @Description: 设置volume信息
	 * @param volumeInfoResp
	 * @return 
	 * @author hushengmeng
	 */
	private Volume setVolume(VolumeInfoResp volumeInfoResp, Long bookId, int volumeIndex){
		Volume volume = new Volume();
		volume.setProviderCode(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE));// 是	供应商编码
		volume.setBookId(bookId);// 否	当当读书保存的作品id
		volume.setProviderBookId(volumeInfoResp.getcBID().toString());// 否	供应商作品id
		volume.setProviderVolumeId(volumeInfoResp.getcVID().toString());// 是	供应商卷id
		if(StringUtils.isBlank(volumeInfoResp.getVolumename())){
			volume.setTitle("第" + volumeIndex +"卷");// 是	卷标题
		}else{
			volume.setTitle(volumeInfoResp.getVolumename());// 是	卷标题
		}
		volume.setVolumeOrder(volumeInfoResp.getVolumesort());// 是	卷序号，从0开始按卷的顺序递增
		volume.setDescription(volumeInfoResp.getVolumedesc());// 否	卷描述
		return volume;
	}
	
	/**
	 * 
	 * @Title: setChapter 
	 * @Description: 设置volume信息
	 * @param chapterInfoResp
	 * @return 
	 * @author hushengmeng
	 */
	private Chapter setChapter(ChapterInfoResp chapterInfoResp, Long bookId, Long volumeId, String content, Integer index){
		Chapter chapter = new Chapter();
		chapter.setProviderCode(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE));// 是	供应商编码
		chapter.setBookId(bookId);// 否	当当读书保存的作品id。
		chapter.setProviderBookId(chapterInfoResp.getcBID().toString());// 否	供应商作品id
		chapter.setVolumeId(volumeId);// 否	当当读书保存的卷id
		chapter.setProviderVolumeId(chapterInfoResp.getcVID().toString());// 否	供应商卷id
		chapter.setProviderChapterId(chapterInfoResp.getcCID().toString());// 是	供应商章节id
		chapter.setTitle(chapterInfoResp.getChaptertitle());// 是	章节名称
		String chapterSort = chapterInfoResp.getChaptersort().toString();
		if(index != null){
			logger.info("setChapter index={},chapterSort={}", index, chapterSort);
			String sort = "";
			if(chapterSort.length() > 7){
				sort = StringUtils.substring(chapterSort, chapterSort.length() - 7);
			}else{
				sort = StringUtils.leftPad(chapterSort, 7, "0");
			}
			chapterSort = String.valueOf(index) + sort;
			logger.info("setChapter chapterSort={}", chapterSort);
		}
		chapter.setIndexOrder(Integer.parseInt(chapterSort));// 是	章节序号从0开始按章节顺序递增
		content = "<p>" + content + "</p>";
		if(content.contains("\r") && content.contains("\n")){
			content = content.replaceAll("\r", "</p>");
	    	content = content.replaceAll("\n", "<p>");
		}else{
			if(content.contains("\r")){
				content = content.replaceAll("\r", "</p><p>");
			}else{
				content = content.replaceAll("\n", "</p><p>");
			}
		}
    	content = content.replaceAll("\r", "</p>");
    	content = content.replaceAll("\n", "<p>");
		chapter.setContent(content);// 是	章节内容。章节中的各个段落请使用段落标签<p></p>表示
		chapter.setWordCnt(chapterInfoResp.getOriginalwords().longValue());// 否	章节字数
		if(chapterInfoResp.getVipflag().intValue() == -1){
			chapter.setIsFree(Chapter.CHAPTER_FREE_YES.toString());// 否	是否收费：1免费，0收费，默认为免费。
		}else{
			chapter.setIsFree(Chapter.CHAPTER_FREE_NO.toString());// 否	是否收费：1免费，0收费，默认为免费。
		}
		chapter.setPrice(chapterInfoResp.getAmount());// 设置价格
		chapter.setIosPrice(chapterInfoResp.getAmount());// 设置ios价格
		return chapter;
	}
	
	/**
	 * 
	 * @Title: addChapterByVolume 
	 * @Description: 通过卷增加章节
	 * @param volumeInfoResps
	 * @param bookId
	 * @param cbid 
	 * @author hushengmeng
	 */
	private void addChapterByVolume(List<VolumeInfoResp> volumeInfoResps, Long bookId, String cbid){
		int volumeIndex = 1;
		// 有卷的信息：调用卷的基本信息，获取卷的章节列表，章节的基本信息，章节内容
		for (VolumeInfoResp volumeInfoResp : volumeInfoResps) {
			int volumePullStatus = 1;
			String volumePullFailureCause = "";
			//调用分销平台添加卷接口
			Volume volume = setVolume(volumeInfoResp, bookId, volumeIndex++);
			String addVolumeResult = volumeService.saveVolumeByHttp(volume);
			if(StringUtils.isBlank(addVolumeResult)){
				// 记录卷日志
				volumePullStatus = 0;
				volumePullFailureCause = "调用分销平台增加卷接口：返回为空！";
				distributePullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
						cbid, volumeInfoResp.getcVID().toString(), volumePullStatus, volumePullFailureCause);
			}else{
				String volumeCode = JSON.parseObject(addVolumeResult).getJSONObject("status").getString("code");
				
				if("0".equals(volumeCode)){
					distributePullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
							cbid, volumeInfoResp.getcVID().toString(), volumePullStatus, volumePullFailureCause);
					int chapterPage = 1;
					int chapterPageSize = 10;
					while (true) {
						List<ChapterInfoResp> chapterInfoResps = getChaptersFromYuewenByVolumeId(cbid, volumeInfoResp.getcVID().toString()
								, chapterPage, chapterPageSize);
						if(chapterInfoResps != null && chapterInfoResps.size() > 0){
							for (ChapterInfoResp chapterInfoResp : chapterInfoResps) {
								//调用获取章节内容接口
								ChapterContentResp chapterContentResp = getChapterContentFromYuewenByChapterId(cbid, chapterInfoResp.getcCID().toString(),
										chapterInfoResp.getcVID().toString());
								if(chapterContentResp != null){
									//调用分销平台增加章节接口
									Long volumeId = JSON.parseObject(addVolumeResult).getJSONObject("data").getLong("volumeId");
									Chapter chapter = setChapter(chapterInfoResp, bookId, volumeId, 
											chapterContentResp.getContent(), volumeIndex-1);
									String addChapterResult = chapterService.saveChapterByHttp(chapter, 
											ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "addChapter");
									int chapterPullStatus = 1;
									String chapterPullFailureCause = "";
									if(StringUtils.isBlank(addChapterResult)){
										chapterPullStatus = 0;
										chapterPullFailureCause = "调用分销平台增加章节接口：返回为空！";
									}else{
										String chapterCode = JSON.parseObject(addChapterResult).getJSONObject("status").getString("code");
										if(!"0".equals(chapterCode)){
											chapterPullStatus = 0;
											chapterPullFailureCause = "调用分销平台增加章节接口：" + JSON.parseObject(addChapterResult).
													getJSONObject("status").getString("message");
										}
									}
									distributePullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, chapterInfoResp.getcVID().toString(), 
											chapterInfoResp.getcCID().toString(), chapterPullStatus, chapterPullFailureCause);
								}
							}
							chapterPage++;
						}
						if(chapterInfoResps == null || chapterInfoResps.size() == 0 
								|| chapterInfoResps.size() != 10){
							break;
						}
					}
				}else{
					// 记录卷日志
					volumePullStatus = 0;
					volumePullFailureCause = "调用分销平台增加卷接口：" + JSON.parseObject(addVolumeResult).getJSONObject("status").getString("message");
					distributePullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
							cbid, volumeInfoResp.getcVID().toString(), volumePullStatus, volumePullFailureCause);
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: addChapterByBook 
	 * @Description: 通过书添加章节
	 * @param bookId
	 * @param cbid 
	 * @author hushengmeng
	 */
	private void addChapterByBook(Long bookId, String cbid){
		// 没有卷的信息：调用获取书的所有章节列表，章节的基本信息，章节内容
		int chapterPage = 1;
		int chapterPageSize = 10;
		while (true) {
			List<ChapterInfoResp> chapterInfoResps = getChaptersFromYuewenByBookId(cbid, chapterPage, chapterPageSize);
			if(chapterInfoResps != null && chapterInfoResps.size() > 0){
				for (ChapterInfoResp chapterInfoResp : chapterInfoResps) {
					//调用获取章节内容接口
					ChapterContentResp chapterContentResp = getChapterContentFromYuewenByChapterId(cbid, chapterInfoResp.getcCID().toString(),
							chapterInfoResp.getcVID().toString());
					if(chapterContentResp != null){
						//调用分销平台增加章节接口
						Chapter chapter = setChapter(chapterInfoResp, bookId, null, chapterContentResp.getContent(), null);
						String addChapterResult = chapterService.saveChapterByHttp(chapter, 
								ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "addChapter");
						if(StringUtils.isBlank(addChapterResult)){
							int pullStatus = 0;
							String pullFailureCause = "调用分销平台增加章节接口：返回为空！";
							distributePullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, "", 
									chapterInfoResp.getcCID().toString(), pullStatus, pullFailureCause);
						}else{
							String chapterCode = JSON.parseObject(addChapterResult).getJSONObject("status").getString("code");
							if(!"0".equals(chapterCode)){
								int pullStatus = 0;
								String pullFailureCause = "调用分销平台增加章节接口：" + JSON.parseObject(addChapterResult).
										getJSONObject("status").getString("message");
								distributePullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, "", 
										chapterInfoResp.getcCID().toString(), pullStatus, pullFailureCause);
							}
						}
					}
					
					
				}
				chapterPage++;
			}
			if(chapterInfoResps == null || chapterInfoResps.size() == 0 
					|| chapterInfoResps.size() != 10){
				break;
			}
		}
	}
	
	/**
	 * 
	 * @Title: updateChapterByVolume 
	 * @Description: 通过卷更新章节
	 * @param volumeInfoResps
	 * @param bookId
	 * @param cbid 
	 * @author hushengmeng
	 */
	private void updateChapterByVolume(List<VolumeInfoResp> volumeInfoResps, Long bookId, String cbid, int volumeIndex){
		// 有卷的信息：调用卷的基本信息，获取卷的章节列表，章节的基本信息，章节内容
		for (VolumeInfoResp volumeInfoResp : volumeInfoResps) {
			int volumePullStatus = 1;
			String volumePullFailureCause = "";
			//调用分销平台添加卷接口
			Volume volume = setVolume(volumeInfoResp, bookId, volumeIndex++);
			//查询是否拉取过该卷
			Volume beforeVolume = volumeService.findMasterUniqueByParams("providerCode", 
					ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), "providerBookId", cbid,
					"providerVolumeId", volumeInfoResp.getcVID().toString());
			String volumeResult = "";
			if(beforeVolume == null){
				volumeResult = volumeService.saveVolumeByHttp(volume);
			}else{
				volumeResult = volumeService.updateVolumeByHttp(volume);
			}
			if(StringUtils.isBlank(volumeResult)){
				// 记录卷日志
				volumePullStatus = 0;
				volumePullFailureCause = "调用分销平台增加卷或者更新卷接口：返回为空！";
				distributePullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
						cbid, volumeInfoResp.getcVID().toString(), volumePullStatus, volumePullFailureCause);
			}else{
				String volumeCode = JSON.parseObject(volumeResult).getJSONObject("status").getString("code");
				
				if("0".equals(volumeCode)){
					distributePullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
							cbid, volumeInfoResp.getcVID().toString(), volumePullStatus, volumePullFailureCause);
					int chapterPage = 1;
					int chapterPageSize = 10;
					while (true) {
						List<ChapterInfoResp> chapterInfoResps = getChaptersFromYuewenByVolumeId(cbid, volumeInfoResp.getcVID().toString()
								, chapterPage, chapterPageSize);
						if(chapterInfoResps != null && chapterInfoResps.size() > 0){
							for (ChapterInfoResp chapterInfoResp : chapterInfoResps) {
								//调用获取章节内容接口
								ChapterContentResp chapterContentResp = getChapterContentFromYuewenByChapterId(cbid, chapterInfoResp.getcCID().toString(),
										chapterInfoResp.getcVID().toString());
								if(chapterContentResp != null){
									//调用分销平台增加或者更新章节接口
									Long volumeId = JSON.parseObject(volumeResult).getJSONObject("data").getLong("volumeId");
									Chapter chapter = setChapter(chapterInfoResp, bookId, volumeId, 
											chapterContentResp.getContent(), volumeIndex-1);
									//查询是否拉取过该章节
									logger.info("findChapter params=[providerCode="+ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE)+",providerBookId="+
											cbid +", providerChapterId="+ chapterInfoResp.getcCID() +"]");
									
									Chapter beforeChapter = chapterService.findMasterUniqueByParams("providerCode", ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE),
											"providerBookId", cbid, "providerChapterId", chapterInfoResp.getcCID().toString());
									
									String chapterResult = "";
									int chapterPullStatus = 1;
									String chapterPullFailureCause = "";
									if(beforeChapter == null){
										chapterResult = chapterService.saveChapterByHttp(chapter, 
												ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "addChapter");
									}else{
										long yuewenChapterTime = DateUtil.parseStringToDate(chapterInfoResp.getUpdatetime()).getTime();
										long distributeTime = DateUtil.addMinute(beforeChapter.getLastModifyedDate(), -120).getTime();
										logger.info("yuewenChapterTime={}, distributeTime={}", yuewenChapterTime, distributeTime);
										if(yuewenChapterTime >= distributeTime){
											chapterResult = chapterService.updateChapterByHttp(chapter, 
													ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "updateChapter");
										}else{
											chapterResult = "分销平台章节信息已是最新！";
										}
									}
									
									if(StringUtils.isBlank(chapterResult)){
										chapterPullStatus = 0;
										chapterPullFailureCause = "调用分销平台增加或者更新章节接口：返回为空！";
									}else{
										if(!"分销平台章节信息已是最新！".equals(chapterResult)){
											String chapterCode = JSON.parseObject(chapterResult).getJSONObject("status").getString("code");
											if(!"0".equals(chapterCode)){
												chapterPullStatus = 0;
												chapterPullFailureCause = "调用分销平台增加或者更新章节接口：" + JSON.parseObject(chapterResult).
														getJSONObject("status").getString("message");
											}
										}
									}
									distributePullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, chapterInfoResp.getcVID().toString(), 
											chapterInfoResp.getcCID().toString(), chapterPullStatus, chapterPullFailureCause);
								}
							}
							chapterPage++;
						}
						if(chapterInfoResps == null || chapterInfoResps.size() == 0 
								|| chapterInfoResps.size() != 10){
							break;
						}
					}
				}else{
					// 记录卷日志
					volumePullStatus = 0;
					volumePullFailureCause = "调用分销平台增加卷或者更新卷接口：" + JSON.parseObject(volumeResult).getJSONObject("status").getString("message");
					distributePullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), 
							cbid, volumeInfoResp.getcVID().toString(), volumePullStatus, volumePullFailureCause);
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: updateChapterByBook 
	 * @Description: 通过书更新章节
	 * @param bookId
	 * @param cbid 
	 * @author hushengmeng
	 */
	private void updateChapterByBook(Long bookId, String cbid){
		// 没有卷的信息：调用获取书的所有章节列表，章节的基本信息，章节内容
		int chapterPage = 1;
		int chapterPageSize = 10;
		while (true) {
			List<ChapterInfoResp> chapterInfoResps = getChaptersFromYuewenByBookId(cbid, chapterPage, chapterPageSize);
			if(chapterInfoResps != null && chapterInfoResps.size() > 0){
				for (ChapterInfoResp chapterInfoResp : chapterInfoResps) {
					//调用获取章节内容接口
					ChapterContentResp chapterContentResp = getChapterContentFromYuewenByChapterId(cbid, chapterInfoResp.getcCID().toString(),
							chapterInfoResp.getcVID().toString());
					if(chapterContentResp != null){
						//调用分销平台增加章节接口
						Chapter chapter = setChapter(chapterInfoResp, bookId, null, chapterContentResp.getContent(), null);
						//查询是否拉取过该章节
						Chapter beforeChapter = chapterService.findMasterUniqueByParams("providerCode", 
								ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), "providerBookId", cbid,
								"providerChapterId", chapterInfoResp.getcCID().toString());
						String chapterResult = "";
						if(beforeChapter == null){
							chapterResult = chapterService.saveChapterByHttp(chapter, 
									ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "addChapter");
						}else{
							long yuewenChapterTime = DateUtil.parseStringToDate(chapterInfoResp.getUpdatetime()).getTime();
							long distributeTime = DateUtil.addMinute(beforeChapter.getLastModifyedDate(), -120).getTime();
							logger.info("yuewenChapterTime={}, distributeTime={}", yuewenChapterTime, distributeTime);
							if(yuewenChapterTime >= distributeTime){
								chapterResult = chapterService.updateChapterByHttp(chapter, 
										ConfigPropertieUtils.getString(YUEWEN_PROVIDER_KEY), "updateChapter");
							}else{
								chapterResult = "分销平台章节信息已是最新！";
							}
						}
						if(StringUtils.isBlank(chapterResult)){
							int pullStatus = 0;
							String pullFailureCause = "调用分销平台增加或者更新章节接口：返回为空！";
							distributePullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, "", 
									chapterInfoResp.getcCID().toString(), pullStatus, pullFailureCause);
						}else{
							if(!"分销平台章节信息已是最新！".equals(chapterResult)){
								String chapterCode = JSON.parseObject(chapterResult).getJSONObject("status").getString("code");
								if(!"0".equals(chapterCode)){
									int pullStatus = 0;
									String pullFailureCause = "调用分销平台增加或者更新章节接口：" + JSON.parseObject(chapterResult).
											getJSONObject("status").getString("message");
									distributePullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE), cbid, "", 
											chapterInfoResp.getcCID().toString(), pullStatus, pullFailureCause);
								}
							}
						}
					}
					
					
				}
				chapterPage++;
			}
			if(chapterInfoResps == null || chapterInfoResps.size() == 0 
					|| chapterInfoResps.size() != 10){
				break;
			}
		}
	}
	
	/**
	 * 
	 * @Title: updateCopyright 
	 * @Description: 更新版权方信息 
	 * @author hushengmeng
	 */
	@ResponseBody
	@RequestMapping("updateCopyright")
	public void updateCopyright(HttpServletRequest request) {
		String pageSize = request.getParameter("pageSize");
		//查询版权方信息为空的信息
		Map<String, Object> updateCopyrightMap = new HashMap<String, Object>();
		updateCopyrightMap.put("providerCode", ConfigPropertieUtils.getString(YUEWEN_PROVIDER_CODE));
		List<Book> bookList = bookService.findPageCopyright(updateCopyrightMap, Integer.parseInt(pageSize));
		List<Book> books = new ArrayList<Book>();
		for (Book book : bookList) {
			try {
				// 调用获取书籍信息接口
				BookInfoResp bookInfoResp = getBookByYuewen(book.getProviderBookId());
				if(bookInfoResp != null){
					Book updateBook = new Book();
					updateBook.setBookId(book.getBookId());
					updateBook.setCopyright(YueWenSiteEnum.getSite(bookInfoResp.getcPID()));
					books.add(updateBook);
				}
			} catch (Exception e) {
				logger.error("updateCopyright bookId={} error!", book.getBookId());
			}
		}
		if(books.size() > 0){
			// 批量更新作品版权方信息
			bookService.batchUpdate(books);
		}
	}
	
	public static void main(String[] args) throws IOException {
	}
	
	/**  
     * 按设置的宽度高度压缩图片文件
     * @param oldFile  要进行压缩的文件全路径  
     * @param newFile  新文件  
     * @param width  宽度  
     * @param height 高度  
     * @param quality 质量  
     */    
    private static void zipWidthHeightImageFile(InputStream oldFileStream,OutputStream newFileStream, int width, int height,float quality) {    
        try {    
            /** 对服务器上的临时文件进行处理 */    
            Image srcFile = ImageIO.read(oldFileStream);    
              
            String subfix = "jpg";  
  
            BufferedImage buffImg = null;   
            if(subfix.equals("png")){  
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);  
            }else{  
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
            }  
  
            Graphics2D graphics = buffImg.createGraphics();  
            graphics.setBackground(new Color(255,255,255));  
            graphics.setColor(new Color(255,255,255));  
            graphics.fillRect(0, 0, width, height);  
            graphics.drawImage(srcFile.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);    
  
            ImageIO.write(buffImg, subfix, newFileStream);    
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
    }    


	
}