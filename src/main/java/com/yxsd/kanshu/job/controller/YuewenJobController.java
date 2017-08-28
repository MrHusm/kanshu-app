package com.yxsd.kanshu.job.controller;

import com.alibaba.fastjson.JSON;
import com.yxsd.kanshu.base.contants.Constants;
import com.yxsd.kanshu.base.contants.SearchEnum;
import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.*;
import com.yxsd.kanshu.job.model.PullBook;
import com.yxsd.kanshu.job.model.PullChapter;
import com.yxsd.kanshu.job.model.PullVolume;
import com.yxsd.kanshu.job.service.IPullBookService;
import com.yxsd.kanshu.job.service.IPullChapterService;
import com.yxsd.kanshu.job.service.IPullVolumeService;
import com.yxsd.kanshu.job.vo.*;
import com.yxsd.kanshu.product.model.*;
import com.yxsd.kanshu.product.service.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
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
@Controller
@RequestMapping("yuewenJob")
public class YuewenJobController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(YuewenJobController.class);

	private static final String YUEWEN_APPKEY = "yuewen_appkey";
	private static final String YUEWEN_URL_BOOKCATEGORYLIST = "yuewen_url_bookcategorylist";
	private static final String YUEWEN_URL_BOOKLIST = "yuewen_url_booklist";
	private static final String YUEWEN_COPYRIGHT_CODE = "yuewen_copyright_code";
	private static final String YUEWEN_URL_BOOKINFO = "yuewen_url_bookinfo";

    private static final String YUEWEN_URL_VOLUMESINFO = "yuewen_url_volumesInfo";
    private static final String YUEWEN_URL_VOLUMEINFO = "yuewen_url_volumeInfo";
    private static final String YUEWEN_URL_CHAPTERSINFO = "yuewen_url_chaptersInfo";
    private static final String YUEWEN_URL_BOOKCHAPTERSINFO = "yuewen_url_bookChaptersInfo";
    private static final String YUEWEN_URL_CHAPTERINFO = "yuewen_url_chapterInfo";
    private static final String YUEWEN_URL_CHAPTERCONTENTINFO = "yuewen_url_chapterContentInfo";
    private static final String YUEWEN_URL_UPDATE_BOOKS = "yuewen_url_update_books";
	private static final String YUEWEN_IMG_BASEPATH = "yuewen_img_basePath";

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
	@Resource
	private IPullBookService pullBookService;
	@Resource
	private IPullVolumeService pullVolumeService;
	@Resource
	private IPullChapterService pullChapterService;
	@Resource
	private ICategoryService categoryService;
	@Resource
	private IAuthorService authorService;


	/**
	 *
	 * @Title: getCategorys
	 * @Description: 获取分类信息
	 * @author hushengmeng
	 * @throws IOException
	 */
	@RequestMapping(value = "/categorys")
	@ResponseBody
	public void getCategorys(HttpServletRequest request, HttpServletResponse response){
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
		String copyrightCode = ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE);
		//保存分类表
		for(CategoryResp categoryResp : categoryResps){
			Category categoryFirst = this.categoryService.findUniqueByParams("copyrightCode",copyrightCode,"copyrightCategoryId",categoryResp.getFreetype());
			Category categorySec = this.categoryService.findUniqueByParams("copyrightCode",copyrightCode,"copyrightCategoryId",categoryResp.getCategoryid());
			Category categoryThr = this.categoryService.findUniqueByParams("copyrightCode",copyrightCode,"copyrightCategoryId",categoryResp.getSubcategoryid());
			//保存一级分类
			if(categoryFirst == null){
				categoryFirst = new Category();
				categoryFirst.setLevel(1);
				categoryFirst.setPid(0L);
				categoryFirst.setName(categoryResp.getFreetypename());
				categoryFirst.setUpdateDate(new Date());
				categoryFirst.setCreateDate(new Date());
				categoryFirst.setCopyrightCode(copyrightCode);
				categoryFirst.setCopyrightCategoryId(categoryResp.getFreetype().longValue());
				categoryService.save(categoryFirst);
			}
			//保存二级分类
			if(categorySec == null){
				categorySec = new Category();
				categorySec.setLevel(2);
				categorySec.setPid(categoryFirst.getCategoryId());
				categorySec.setName(categoryResp.getCategoryname());
				categorySec.setUpdateDate(new Date());
				categorySec.setCreateDate(new Date());
				categorySec.setCopyrightCode(copyrightCode);
				categorySec.setCopyrightCategoryId(categoryResp.getCategoryid().longValue());
				categoryService.save(categorySec);
			}
			//保存三级分类
			if(categoryThr == null){
				categoryThr = new Category();
				categoryThr.setLevel(3);
				categoryThr.setPid(categorySec.getCategoryId());
				categoryThr.setName(categoryResp.getSubcategoryname());
				categoryThr.setUpdateDate(new Date());
				categoryThr.setCreateDate(new Date());
				categoryThr.setCopyrightCode(copyrightCode);
				categoryThr.setCopyrightCategoryId(categoryResp.getSubcategoryid().longValue());
				categoryService.save(categoryThr);
			}
		}
		logger.info("yuewen getCategorys end!");
	}

	/**
	 *
	 * @Title: pullBooks
	 * @Description: 拉取图书
	 * @return
	 * @author hushengmeng
	 */
	@RequestMapping(value = "/pullBooks")
	@ResponseBody
	public void pullBooks(HttpServletRequest request) {
		logger.info("yuewen pullBooks begin!");
		String appKey = ConfigPropertieUtils.getString(YUEWEN_APPKEY);
		String token = getYueWenToken();
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer pageNo = null;
		//获取已拉取阅文的图书数量，获取本次调度拉取的页数
		PullBook pullBook = new PullBook();
		pullBook.setCopyrightCode(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE));
		int count = pullBookService.findCount(pullBook);
		pageNo = count/pageSize + 1;
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
						pullBookPool = new ThreadPoolExecutor(COREPOOL_SIZE+20, COREPOOL_SIZE+20, THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS,
								new LinkedBlockingQueue<Runnable>(THREAD_POOL_QUEUE_SIZE + 100), new CallerRunsPolicy());
					}
					List<String> tempList = new ArrayList<String>();
					for (String cbid : cbids) {
						if(StringUtils.isNotBlank(cbid)){
							tempList.add(cbid);
						}
					}
					cbids = tempList.toArray(new String[0]);
					//判断是否拉取过
					for (int i = 0; i < cbids.length; i++) {
						logger.info("ifpullBook params:copyrightCode={},copyrightBookId={}", ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE),
								cbids[i]);
						PullBook pullBookFromDB = pullBookService.findUniqueByParams("copyrightCode", ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE),
								"copyrightBookId", cbids[i]);
						if(pullBookFromDB != null){
							cbids[i] = "";
						}
					}

					for (final String cbid : cbids) {
						pullBookPool.execute(new Runnable() {

							@Override
							public void run() {
								try {
									if(StringUtils.isNotBlank(cbid)){
										//调用阅文接口获取书籍信息并保存到数据库
										BookInfoResp bookInfoResp = getBookByYuewen(cbid);
										if(bookInfoResp != null){
											//封装图书数据
											Book book = setBook(bookInfoResp);
											//保存图书
											bookService.save(book);
											//创建搜索索引
											//IndexManager.getManager().createIndex(SearchContants.ID,SearchContants.TABLENAME,setIndexField(book));
											//调用阅文获取书籍卷列表
											List<VolumeInfoResp> volumeInfoResps = getVolumesFromYuewenByBookId(cbid);
											if(volumeInfoResps != null){
												if(volumeInfoResps.size() > 0){
													// 有卷的信息：调用卷的基本信息，获取卷的章节列表，章节的基本信息，章节内容
													addChapterByVolume(volumeInfoResps, book.getBookId(), cbid);
												}else{
													// 没有卷的信息：调用获取书的所有章节列表，章节的基本信息，章节内容
													addChapterByBook(book.getBookId(), cbid);
												}
											}
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
									int pullStatus = 0;
									String pullFailureCause = "拉取异常：" + ExceptionUtils.getRootCauseMessage(e);
									pullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE),
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
								Book beforeBook = bookService.findMasterUniqueByParams("copyrightCode",
										ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), "copyrightBookId", cbid);
								if(beforeBook == null){
									logger.info("yuewen updateBooks cbid={} book null!", cbid);
								}else{
									//更新拉取的书籍信息
									BookInfoResp bookInfoResp = getBookByYuewen(cbid);
									if(bookInfoResp != null){
										//封装图书信息
										Book book = setBook(bookInfoResp);
										book.setBookId(beforeBook.getBookId());
										//修改图书
										bookService.update(book);

										//调用阅文获取书籍卷列表
										List<VolumeInfoResp> volumeInfoResps = getVolumesFromYuewenByBookId(cbid);
										if(volumeInfoResps != null){
											if(volumeInfoResps.size() > 0){
												int ywjtVolumeCount = volumeInfoResps.get(0).getCount();
												//验证卷是否增加
												Volume volume = new Volume();
												volume.setCopyrightCode(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE));
												volume.setCopyrightBookId(Long.parseLong(cbid));
												int ddVolumeCount = volumeService.findCount(volume);
												logger.info("voluem ywjtcount={},ddcount={}", ywjtVolumeCount, ddVolumeCount);
												int addNewVolume = ywjtVolumeCount - ddVolumeCount;
												if(addNewVolume >= 0){
													volumeInfoResps = volumeInfoResps.subList(ddVolumeCount -1, volumeInfoResps.size());
												}
												// 有卷的信息：调用获取卷的章节列表，章节的基本信息，章节内容
												updateChapterByVolume(volumeInfoResps, book.getBookId(), cbid, ddVolumeCount);
											}else{
												// 没有卷的信息：调用获取书的所有章节列表，章节内容
												updateChapterByBook(book.getBookId(), cbid);
											}
										}
										int pullStatus = 2;
										String pullFailureCause = "调用阅文更新书籍接口：重新拉取！";
										pullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE),
												cbid, pullStatus, pullFailureCause);
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
		String copyrightBookIds = request.getParameter("copyrightBookIds");
		String pageSizeStr = request.getParameter("pageSize");

		List<PullBook> pullBooks = null;
		if(StringUtils.isBlank(copyrightBookIds)){
			//自动重新拉取图书
			Map<String, Object> pullBookMap = new HashMap<String, Object>();
			pullBookMap.put("copyrightCode", ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE));
			pullBookMap.put("pullStatus", 0);
			Query query = new Query(Integer.parseInt(pageSizeStr));
			PageFinder<PullBook> pullBooksPage = pullBookService.findPageFinderObjs(pullBookMap, query);
			pullBooks = pullBooksPage.getData();
			//先处理状态拉取失败的作品，然后处理需要重新拉取的作品
			if(pullBooks.size() < Integer.parseInt(pageSizeStr)){
				pullBookMap.put("pullStatus", 2);
				query = new Query(Integer.parseInt(pageSizeStr) - pullBooks.size());
				PageFinder<PullBook> pullBooksPageAgain = pullBookService.findPageFinderObjs(pullBookMap, query);
				pullBooks.addAll(pullBooksPageAgain.getData());
			}
		}else{
			//手动设置重新拉取图书
			copyrightBookIds = StringUtils.replace(copyrightBookIds, "，", ",");
			List<String> cbidList = Arrays.asList(copyrightBookIds.split(","));
			pullBooks = pullBookService.findByCopyrightBookIds(cbidList);
		}
		logger.info("handleFailureBooks count={}", pullBooks.size());
		if(handleFailureBooksPool == null){
			//创建线程池
			handleFailureBooksPool = new ThreadPoolExecutor(COREPOOL_SIZE-3, COREPOOL_SIZE-3, THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>(THREAD_POOL_QUEUE_SIZE + 10000), new CallerRunsPolicy());
		}
		for (PullBook pullBook : pullBooks) {
			final String cbid = String.valueOf(pullBook.getCopyrightBookId());
			handleFailureBooksPool.execute(new Runnable() {

				@Override
				public void run() {

					logger.info("yuewen handleFailureBooks cbid={} begin!", cbid);
					//处理失败的书籍信息
					BookInfoResp bookInfoResp = getBookByYuewen(cbid);
					if(bookInfoResp != null){
						//封装图书信息
						Book book = setBook(bookInfoResp);
						Book beforeBook = bookService.findMasterUniqueByParams("copyrightCode",
								ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), "copyrightBookId", cbid);
						if(beforeBook == null){
							bookService.save(book);
						}else{
							book.setBookId(beforeBook.getBookId());
							bookService.update(book);
						}

						//调用阅文获取书籍卷列表
						List<VolumeInfoResp> volumeInfoResps = getVolumesFromYuewenByBookId(cbid);
						if(volumeInfoResps != null){
							if(volumeInfoResps.size() > 0){
								// 有卷的信息：调用获取卷的章节列表，章节的基本信息，章节内容
								updateChapterByVolume(volumeInfoResps, book.getBookId(), cbid, 1);
							}else{
								// 没有卷的信息：调用获取书的所有章节列表，章节内容
								updateChapterByBook(book.getBookId(), cbid);
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
		String copyrightVolumes = request.getParameter("copyrightVolumes");
		String pageSizeStr = request.getParameter("pageSize");
		List<PullVolume> pullVolumes = null;
		if(StringUtils.isBlank(copyrightVolumes)){
			//自动重新拉取失败卷
			Map<String, Object> pullVolumeMap = new HashMap<String, Object>();
			pullVolumeMap.put("copyrightCode", ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE));
			pullVolumeMap.put("pullStatus", 0);
			Query query = new Query(Integer.parseInt(pageSizeStr));
			PageFinder<PullVolume> pullVolumesPage = pullVolumeService.findPageFinderObjs(pullVolumeMap, query);
			pullVolumes = pullVolumesPage.getData();
		}else{
			//手动设置重新拉取卷
			copyrightVolumes = StringUtils.replace(copyrightVolumes, "，", ",");
			List<String> copyrightVolumesList = Arrays.asList(copyrightVolumes.split(","));
			pullVolumes = pullVolumeService.findByCopyrightVolumes(copyrightVolumesList);
		}

		logger.info("handleFailureVolumes count={}", pullVolumes.size());
		for (PullVolume pullVolume : pullVolumes) {
			String cbid = String.valueOf(pullVolume.getCopyrightBookId());
			String cvid = String.valueOf(pullVolume.getCopyrightVolumeId());
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
				Volume beforeVolume = volumeService.findMasterUniqueByParams("copyrightCode",
						ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), "copyrightBookId", cbid,
						"copyrightVolumeId", volumeInfoResp.getcVID().toString());
				Book book = bookService.findMasterUniqueByParams("copyrightCode",
						ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), "copyrightBookId", cbid);
				if(beforeVolume == null){
					volume.setBookId(book.getBookId());
					volumeService.save(volume);
				}else{
					volume.setBookId(book.getBookId());
					volume.setVolumeId(beforeVolume.getVolumeId());
					volumeService.update(volume);
				}

				pullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE),
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
								//封装章节信息
								Chapter chapter = setChapter(chapterInfoResp, book.getBookId(), volume.getVolumeId(),
										chapterContentResp.getContent(), volumeIndex);
								//查询是否拉取过该章节
								logger.info("copyrightCode="+ ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE) + ",copyrightBookId="+ cbid
										+ ",copyrightChapterId=" + chapterInfoResp.getcCID());
								Chapter beforeChapter = chapterService.findMasterUniqueByParams("copyrightCode",
										ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), "copyrightBookId", cbid,
										"copyrightChapterId", chapterInfoResp.getcCID().toString());
								if(beforeChapter == null){
									chapterService.saveChapter(chapter,book.getBookId().intValue() % Constants.CHAPTR_TABLE_NUM);
								}else{
									chapter.setChapterId(beforeChapter.getChapterId());
									chapterService.updateChapter(chapter,book.getBookId().intValue() % Constants.CHAPTR_TABLE_NUM);
								}
								int chapterPullStatus = 1;
								String chapterPullFailureCause = "";
								pullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), cbid, chapterInfoResp.getcVID().toString(),
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
		String copyrightChapters = request.getParameter("copyrightChapters");
		String pageSizeStr = request.getParameter("pageSize");
		List<PullChapter> pullChapters = null;
		if(StringUtils.isBlank(copyrightChapters)){
			//自动获取创建时间在一天内失败的章节
			Map<String, Object> pullChapterMap = new HashMap<String, Object>();
			pullChapterMap.put("copyrightCode", ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE));
			pullChapterMap.put("pullStatus", 0);
			//pullChapterMap.put("pullFailureCause1", "章节已存在");
			//pullChapterMap.put("pullFailureCause2", "章节不存在");
			Query query = new Query(Integer.parseInt(pageSizeStr));
			PageFinder<PullChapter> pullChaptersPage = pullChapterService.findPageFinderObjs(pullChapterMap, query);
			pullChapters = pullChaptersPage.getData();
		}else{
			//手动设置重新拉取的章节
			copyrightChapters = StringUtils.replace(copyrightChapters, "，", ",");
			List<String> copyrightChaptersList = Arrays.asList(copyrightChapters.split(","));
			pullChapters = pullChapterService.findByCopyrightChapters(copyrightChaptersList);
		}

		logger.info("handleFailureChapter count={}", pullChapters.size());
		for (PullChapter pullChapter : pullChapters) {
			String cbid = String.valueOf(pullChapter.getCopyrightBookId());
			String cvid = String.valueOf(pullChapter.getCopyrightVolumeId());
			String ccid = String.valueOf(pullChapter.getCopyrightChapterId());
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
				Book book = bookService.findMasterUniqueByParams("copyrightCode",
						ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), "copyrightBookId", cbid);
				Volume volume = volumeService.findMasterUniqueByParams("copyrightCode",
						ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), "copyrightBookId", cbid,
						"copyrightVolumeId", cvid);
				//调用分销平台增加或者更新章节接口
				Chapter chapter = setChapter(chapterInfoResp, book.getBookId(), volume == null ? null : volume.getVolumeId(),
						chapterContentResp.getContent(), volumeIndex);
				//查询是否拉取过该章节
				logger.info("beforeChapter params:copyrightBookId={},copyrightChapterId={}", cbid, ccid);
				Chapter beforeChapter = chapterService.findMasterUniqueByParams("copyrightCode",
						ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), "copyrightBookId", cbid,
						"copyrightChapterId", ccid);
				if(beforeChapter == null){
					chapterService.saveChapter(chapter,volume.getBookId().intValue() % Constants.CHAPTR_TABLE_NUM);
				}else{
					chapter.setChapterId(beforeChapter.getChapterId());
					chapterService.updateChapter(chapter,volume.getBookId().intValue() % Constants.CHAPTR_TABLE_NUM);
				}
				int chapterPullStatus = 1;
				String chapterPullFailureCause = "";
				pullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), cbid, cvid,
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
			//调用三次阅文接口，防止网络异常
			boolean flag = true;
			int i = 0;
			while(flag && i < 3){
				i++;
				result = HttpUtils.getContent(bookInfoUrl, "UTF-8");
				if(StringUtils.isBlank(result)){
					if(i == 3){
						logger.error("yuewen_interface_error bookinfo result"+cbid+"={}", result);
						pullStatus = 0;
						pullFailureCause = "调用阅文获取图书接口超时或返回空";
					}
				}else{
					flag = false;
					logger.info("yuewen bookinfo result"+cbid+"={}", result);
				}
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
			pullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), cbid, pullStatus, pullFailureCause);
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
		//调用三次阅文接口，防止网络异常
		boolean flag = true;
		int i = 0;
		while(flag && i < 3) {
			i++;
			result = HttpUtils.getContent(volumesInfoUrl, "UTF-8");
			if(StringUtils.isBlank(result)){
				if(i == 3){
					logger.error("yuewen_interface_error BookVolumesInfos result={}", result);
					pullStatus = 0;
					pullFailureCause = "调用阅文获取书藉卷列表接口超时或返回空";
				}
			}else{
				flag = false;
				logger.info("yuewen BookVolumesInfos result={}", result);
			}
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
        pullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), cbid, pullStatus, pullFailureCause);

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
		//调用三次阅文接口，防止网络异常
		boolean flag = true;
		int i = 0;
		while(flag && i < 3) {
			i++;
			result = HttpUtils.getContent(volumeInfoUrl, "UTF-8");
			if(StringUtils.isBlank(result)){
				if(i == 3){
					logger.error("yuewen_interface_error VolumeInfo result={}", result);
					pullStatus = 0;
					pullFailureCause = "调用阅文获取卷信息接口超时或返回空";
				}
			}else{
				flag = false;
				logger.info("yuewen VolumeInfo result={}", result);
			}
		}
		if(StringUtils.isNotBlank(result)) {
			String returnCode = JSON.parseObject(result).getString("returnCode");
			if ("0".equals(returnCode)) {
				// 调用接口成功
				String resultData = JSON.parseObject(result).getJSONObject("result").getString("resultData");
				volumeInfoResp = JSON.parseObject(resultData, VolumeInfoResp.class);
			} else {
				pullStatus = 0;
				pullFailureCause = "调用阅文获取卷信息接口：" + JSON.parseObject(result).getString("returnMsg");
			}
		}
        pullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE),
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
		//调用三次阅文接口，防止网络异常
		boolean flag = true;
		int i = 0;
		while(flag && i < 3) {
			i++;
			result = HttpUtils.getContent(chaptersInfoUrl, "UTF-8");
			if(StringUtils.isBlank(result)){
				if(i == 3){
					logger.error("yuewen_interface_error VolumeChaptersInfo result={}", result);
					volumePullStatus = 0;
					volumePullFailureCause = "调用阅文获取卷的章节列表接口超时或返回空";
				}
			}else{
				flag = false;
				logger.info("yuewen VolumeChaptersInfo result={}", result);
			}
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

        pullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE),
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
		//调用三次阅文接口，防止网络异常
		boolean flag = true;
		int i = 0;
		while(flag && i < 3) {
			i++;
			result = HttpUtils.getContent(bookChaptersInfoUrl, "UTF-8");
			if(StringUtils.isBlank(result)){
				if(i == 3){
					logger.error("yuewen_interface_error BookChaptersInfo result={}", result);
					pullStatus = 0;
					pullFailureCause = "调用阅文获取书的章节列表接口超时或返回空";
				}
			}else{
				flag = false;
				logger.info("yuewen BookChaptersInfo result={}", result);
			}
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
        pullBookService.saveOrUpdatePullBook(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), cbid, pullStatus, pullFailureCause);
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
		//调用三次阅文接口，防止网络异常
		boolean flag = true;
		int i = 0;
		while(flag && i < 3) {
			i++;
			result = HttpUtils.getContent(chapterInfoUrl, "UTF-8");
			if(StringUtils.isBlank(result)){
				if(i == 3){
					logger.error("yuewen_interface_error ChapterInfo result={}", result);
					pullStatus = 0;
					pullFailureCause = "调用阅文获取章节基本信息接口超时或返回空";
				}
			}else{
				flag = false;
				logger.info("yuewen ChapterInfo result={}", result);
			}
		}
		if(StringUtils.isNotBlank(result)) {
			String returnCode = JSON.parseObject(result).getString("returnCode");
			if ("0".equals(returnCode)) {
				// 调用接口成功
				String resultData = JSON.parseObject(result).getJSONObject("result").getString("resultData");
				chapterInfoResp = JSON.parseObject(resultData, ChapterInfoResp.class);
			} else {
				pullStatus = 0;
				pullFailureCause = "调用阅文获取章节基本信息接口：" + JSON.parseObject(result).getString("returnMsg");
			}
		}
        pullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), cbid, cvid,
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
		//调用三次阅文接口，防止网络异常
		boolean flag = true;
		int i = 0;
		while(flag && i < 3) {
			i++;
			result = HttpUtils.getContent(chapterInfoUrl, "UTF-8");
			if(StringUtils.isBlank(result)){
				if(i == 3){
					logger.error("yuewen_interface_error ChapterContentInfo result={}", result);
					pullStatus = 0;
					pullFailureCause = "调用阅文获取书的章节内容接口超时或返回空";
				}
			}else{
				flag = false;
				logger.info("yuewen ChapterContentInfo success");
				//logger.info("yuewen ChapterContentInfo result={}", result);
			}
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
        //保存失败的记录
        if(pullStatus != 1){
			pullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), cbid, cvid,
					ccid, pullStatus, pullFailureCause);
		}
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
		book.setTitle(bookInfoResp.getTitle());// 是	作品名称
		book.setWordCount(bookInfoResp.getAllwords());// 否	作品字数
		book.setCoverUrl(bookInfoResp.getCoverurl());
		try {
			//下载图书封面
			String ext = bookInfoResp.getCoverurl().substring(bookInfoResp.getCoverurl().lastIndexOf("."));
			String imgName = ConfigPropertieUtils.getString(YUEWEN_IMG_BASEPATH) + ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE) + File.separator + bookInfoResp.getcBID() % 100 + File.separator + bookInfoResp.getcBID()+ext;
			File img = new File(imgName);
			FileUtils.copyURLToFile(new URL(bookInfoResp.getCoverurl()),img);
		} catch (IOException e) {
			logger.error("图书封面下载失败:" + bookInfoResp.getcBID() + ":" + bookInfoResp.getCoverurl());
			e.printStackTrace();
		}
		book.setAuthorName(bookInfoResp.getAuthorname());// 是	作者名称
		book.setAuthorPenname(bookInfoResp.getAuthorname());
		Author author = this.authorService.findUniqueByParams("name",book.getAuthorName());
		if(author == null){
			author = new Author();
			author.setName(bookInfoResp.getAuthorname());
			author.setPenname(bookInfoResp.getAuthorname());
			author.setUpdateDate(new Date());
			author.setCreateDate(new Date());
			authorService.save(author);
		}
		book.setAuthorId(author.getAuthorId());
		book.setIntro(bookInfoResp.getIntro());// 否	作品简介
		book.setShelfStatus(1);
		Category categorySec = this.categoryService.findUniqueByParams("copyrightCategoryId",bookInfoResp.getCategoryid(),"copyrightCode",ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE));
		Category categoryThr = this.categoryService.findUniqueByParams("copyrightCategoryId",bookInfoResp.getSubcategoryid(),"copyrightCode",ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE));
		if(categorySec != null){
			book.setCategorySecId(categorySec.getCategoryId());
			book.setCategorySecName(categorySec.getName());
		}
		if(categoryThr != null){
			book.setCategoryThrId(categoryThr.getCategoryId());
			book.setCategoryThrName(categoryThr.getName());
		}
		book.setKeyword(bookInfoResp.getKeyword());// 否	作品关键词，多个关键词请以英文逗号（,）分隔。
		book.setChargeType(bookInfoResp.getChargetype());
		if(bookInfoResp.getStatus() == 50){
			book.setIsFull(1);// 是	是否完本：1完本，0未完本。
		}else{
			book.setIsFull(0);
		}
		book.setPrice(bookInfoResp.getTotalprice());
		if(bookInfoResp.getVipstatus() == 1){
			book.setIsFree(1);
		}else{
			book.setIsFree(0);
		}
		book.setTag(bookInfoResp.getTag());
		book.setLastChapterUpdateDate(DateUtil.parseStringToDate(bookInfoResp.getUpdatetime()));
		book.setCopyrightCode(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE));// 是	供应商编码
		book.setCopyright(bookInfoResp.getSiteInfo(bookInfoResp.getSite()));// 是 供应商名称
		book.setCopyrightBookId(bookInfoResp.getcBID());// 是	供应商作品id
		if(bookInfoResp.getForm() == -1){
			book.setType(1);
		}else if(bookInfoResp.getForm() == 1){
			book.setType(2);
		}
		book.setUnitPrice(bookInfoResp.getUnitprice());
		if(bookInfoResp.getFile_format() == 1){
			book.setFileFormat(1);
		}else if(bookInfoResp.getFile_format() == 2){
			book.setFileFormat(2);
		}else if(bookInfoResp.getFile_format() == 4){
			book.setFileFormat(3);
		}else if(bookInfoResp.getFile_format() == 7){
			book.setFileFormat(4);
		}
		if(bookInfoResp.getMonthlyallowed() == -1){
			book.setIsMonthly(0);
		}else if(bookInfoResp.getMonthlyallowed() == 1){
			book.setIsMonthly(1);
		}
		book.setMonthlyStartDate(DateUtil.parseStringToDate(bookInfoResp.getMonthlytime()));
		book.setMonthlyEndDate(DateUtil.parseStringToDate(bookInfoResp.getCanclemonthlytime()));
		book.setUpdateDate(new Date());
		book.setCreateDate(new Date());
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
		volume.setBookId(bookId);// 否	保存的作品id
		if(StringUtils.isBlank(volumeInfoResp.getVolumename())){
			volume.setName("第" + volumeIndex +"卷");// 是	卷标题
		}else{
			volume.setName(volumeInfoResp.getVolumename());// 是	卷标题
		}
		volume.setDesc(volumeInfoResp.getVolumedesc());// 否	卷描述
		volume.setIdx(volumeInfoResp.getVolumesort());// 是	卷序号，从0开始按卷的顺序递增
		volume.setCopyrightCode(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE));// 是	供应商编码
		volume.setCopyrightBookId(volumeInfoResp.getcBID());// 否	供应商作品id
		volume.setCopyrightVolumeId(volumeInfoResp.getcVID());// 是	供应商卷id
		volume.setShelfStatus(1);
		volume.setCreateDate(new Date());
		volume.setUpdateDate(new Date());
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
		chapter.setVolumeId(volumeId);// 否	保存的卷id
		chapter.setBookId(bookId);// 否	保存的作品id
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
		chapter.setIdx(Integer.parseInt(chapterSort));// 是	章节序号从0开始按章节顺序递增
		chapter.setPrice(chapterInfoResp.getAmount());// 设置价格
		chapter.setWordCount(chapterInfoResp.getOriginalwords());// 否	章节字数
		chapter.setShelfStatus(1);
		if(chapterInfoResp.getVipflag().intValue() == -1){
			chapter.setIsFree(0);// 否	是否收费：0免费，1收费，默认为免费。
		}else{
			chapter.setIsFree(1);// 否	是否收费：1免费，1收费，默认为免费。
		}
		chapter.setContentMd5(chapterInfoResp.getContent_md5());
		chapter.setCopyrightCode(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE));// 是	供应商编码
		chapter.setCopyrightBookId(chapterInfoResp.getcBID());// 否	供应商作品id
		chapter.setCopyrightVolumeId(chapterInfoResp.getcVID());// 否	供应商卷id
		chapter.setCopyrightChapterId(chapterInfoResp.getcCID());// 是	供应商章节id
//		content = "<p>" + content + "</p>";
//		if(content.contains("\r") && content.contains("\n")){
//			content = content.replaceAll("\r", "</p>");
//	    	content = content.replaceAll("\n", "<p>");
//		}else{
//			if(content.contains("\r")){
//				content = content.replaceAll("\r", "</p><p>");
//			}else{
//				content = content.replaceAll("\n", "</p><p>");
//			}
//		}
//    	content = content.replaceAll("\r", "</p>");
//    	content = content.replaceAll("\n", "<p>");
		chapter.setContent(ZipUtils.gzip(content));// 是	章节内容。章节中的各个段落请使用段落标签<p></p>表示
		chapter.setUpdateDate(DateUtil.parseStringToDate(chapterInfoResp.getUpdatetime()));
		chapter.setCreateDate(new Date());
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
			//封装卷信息
			Volume volume = setVolume(volumeInfoResp, bookId, volumeIndex++);
			//保存卷信息
			volumeService.save(volume);

			pullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE),
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
							Chapter chapter = setChapter(chapterInfoResp, bookId, volume.getVolumeId(),
									chapterContentResp.getContent(), volumeIndex-1);
							//保存章节信息
							chapterService.saveChapter(chapter,bookId.intValue() % Constants.CHAPTR_TABLE_NUM);
//							int chapterPullStatus = 1;
//							String chapterPullFailureCause = "";
//							pullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), cbid, chapterInfoResp.getcVID().toString(),
//									chapterInfoResp.getcCID().toString(), chapterPullStatus, chapterPullFailureCause);
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
						//保存章节信息
						chapterService.saveChapter(chapter,bookId.intValue() % Constants.CHAPTR_TABLE_NUM);
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
			//封装卷信息
			Volume volume = setVolume(volumeInfoResp, bookId, volumeIndex++);
			//查询是否拉取过该卷
			Volume beforeVolume = volumeService.findMasterUniqueByParams("copyrightCode",
					ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), "copyrightBookId", cbid,
					"copyrightVolumeId", volumeInfoResp.getcVID().toString());
			if(beforeVolume == null){
				volumeService.save(volume);
			}else{
				volume.setVolumeId(beforeVolume.getVolumeId());
				volumeService.update(volume);
			}

			pullVolumeService.saveOrUpdatePullVolume(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE),
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
							//封装章节信息
							Chapter chapter = setChapter(chapterInfoResp, bookId, volume.getVolumeId(),
									chapterContentResp.getContent(), volumeIndex-1);
							//查询是否拉取过该章节
							logger.info("findChapter params=[copyrightCode="+ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE)+",copyrightBookId="+
									cbid +", copyrightChapterId="+ chapterInfoResp.getcCID() +"]");

							Chapter beforeChapter = chapterService.findMasterUniqueByParams("copyrightCode", ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE),
									"copyrightBookId", cbid, "copyrightChapterId", chapterInfoResp.getcCID().toString());

							int chapterPullStatus = 1;
							String chapterPullFailureCause = "";
							if(beforeChapter == null){
								chapterService.saveChapter(chapter,bookId.intValue() % Constants.CHAPTR_TABLE_NUM);
							}else{
								chapter.setChapterId(beforeChapter.getChapterId());
								chapterService.updateChapter(chapter,bookId.intValue() % Constants.CHAPTR_TABLE_NUM);
							}
							pullChapterService.saveOrUpdatePullChapter(ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), cbid, chapterInfoResp.getcVID().toString(),
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
						//封装章节信息
						Chapter chapter = setChapter(chapterInfoResp, bookId, null, chapterContentResp.getContent(), null);
						//查询是否拉取过该章节
						Chapter beforeChapter = chapterService.findMasterUniqueByParams("copyrightCode",
								ConfigPropertieUtils.getString(YUEWEN_COPYRIGHT_CODE), "copyrightBookId", cbid,
								"copyrightChapterId", chapterInfoResp.getcCID().toString());
						if(beforeChapter == null){
							chapterService.saveChapter(chapter,bookId.intValue() % Constants.CHAPTR_TABLE_NUM);
						}else{
							chapter.setChapterId(beforeChapter.getChapterId());
							chapterService.updateChapter(chapter,bookId.intValue() % Constants.CHAPTR_TABLE_NUM);
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

	public static void main(String[] args) throws IOException {
	}

	/**
     * 按设置的宽度高度压缩图片文件
     * @param oldFileStream  要进行压缩的文件全路径
     * @param newFileStream  新文件
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

	/**
	 * 设置图书搜索索引字段
	 * @param book
	 * @return
	 */
	public static Map<String,String> setIndexField(Book book){
		Map<String,String> fieldMap = new HashMap<String,String>();
		fieldMap.put(SearchEnum.title.getSearchField(),book.getTitle());
		fieldMap.put(SearchEnum.author_name.getSearchField(),book.getAuthorName());
		fieldMap.put(SearchEnum.author_penname.getSearchField(),book.getAuthorPenname());
		fieldMap.put(SearchEnum.category_sec_name.getSearchField(),book.getCategorySecName());
		fieldMap.put(SearchEnum.category_thr_name.getSearchField(),book.getCategoryThrName());
		fieldMap.put(SearchEnum.intro.getSearchField(),book.getIntro());
		return fieldMap;
	}

}