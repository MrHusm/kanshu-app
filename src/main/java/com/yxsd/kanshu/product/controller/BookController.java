package com.yxsd.kanshu.product.controller;

import com.alibaba.fastjson.JSON;
import com.yxsd.kanshu.base.contants.Constants;
import com.yxsd.kanshu.base.contants.ErrorCodeEnum;
import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.DateUtil;
import com.yxsd.kanshu.base.utils.JsonResultSender;
import com.yxsd.kanshu.base.utils.ResultSender;
import com.yxsd.kanshu.pay.model.AlipayResponse;
import com.yxsd.kanshu.pay.service.IAlipayResponseService;
import com.yxsd.kanshu.portal.model.DriveBook;
import com.yxsd.kanshu.portal.service.IDriveBookService;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.model.Chapter;
import com.yxsd.kanshu.product.service.IBookService;
import com.yxsd.kanshu.product.service.IChapterService;
import com.yxsd.kanshu.ucenter.model.User;
import com.yxsd.kanshu.ucenter.model.UserAccount;
import com.yxsd.kanshu.ucenter.model.UserPayBook;
import com.yxsd.kanshu.ucenter.model.UserPayChapter;
import com.yxsd.kanshu.ucenter.service.*;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.*;

/**
 * Created by lenovo on 2017/8/12.
 */
@Controller
@Scope("prototype")
@RequestMapping("book")
public class BookController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Resource(name="bookService")
    IBookService bookService;

    @Resource(name="userService")
    IUserService userService;

    @Resource(name="userAccountService")
    IUserAccountService userAccountService;

    @Resource(name="alipayResponseService")
    IAlipayResponseService alipayResponseService;

    @Resource(name="userAccountLogService")
    IUserAccountLogService userAccountLogService;

    @Resource(name="chapterService")
    IChapterService chapterService;

    @Resource(name="driveBookService")
    IDriveBookService driveBookService;

    @Resource(name="userPayChapterService")
    IUserPayChapterService userPayChapterService;

    @Resource(name="userPayBookService")
    IUserPayBookService userPayBookService;

    /**
     * 获取图书详情
     * @param response
     * @param request
     */
    @RequestMapping("bookDetail")
    public String bookDetail(HttpServletResponse response, HttpServletRequest request, Model model) {
        //入参
        String bookId = request.getParameter("bookId");
        String channel = request.getParameter("channel");
        String userId = request.getParameter("userId");

        if(StringUtils.isBlank(bookId)){
            logger.error("BookController_bookDetail:bookId为空");
            return "error";
        }

        //阅读按钮标识 0：免费试读 1：阅读
        int readBtn = 0;
        User user = this.userService.getUserByUserId(Long.parseLong(userId));
        if(user.isVip()){
            //VIP用户
            readBtn = 1;
        }else{
            DriveBook driveBook = driveBookService.getDriveBookByCondition(1,Long.parseLong(bookId));
            if(driveBook != null){
                //限免图书
                readBtn = 1;
            }else{
                UserPayBook userPayBook = this.userPayBookService.findUniqueByParams("userId",userId,"bookId",bookId,"type",2);
                if(userPayBook != null){
                    //全本购买过
                    readBtn = 1;
                }
            }
        }
        Book book = this.bookService.getBookById(Long.parseLong(bookId));
        String tag = book.getTag();
        List<String> tags = new ArrayList<String>();
        if(StringUtils.isNotBlank(tag)){
            tag = tag.replaceAll("\\d+","");
            tag = tag.replace(":","");
            if(StringUtils.isNotBlank(tag)){
                tags = Arrays.asList(tag.split(","));
            }
        }
        if(book.getWordCount() < 9999){
            model.addAttribute("wordCount",book.getWordCount());
        }else{
            model.addAttribute("wordCount",book.getWordCount() / 10000.0 +"万+");
        }

        int diffDay = DateUtil.diffDate(new Date(),book.getLastChapterUpdateDate());
        if(diffDay <= 0){
            model.addAttribute("updateDay","刚刚");
        }else if(diffDay > 30){
            model.addAttribute("updateDay","1月前");
        }else{
            model.addAttribute("updateDay",diffDay+"天前");
        }

        //作者写的其他书
        List<Book> authorBooks = bookService.findListByParams("authorId",book.getAuthorId());

        //用户还看了其他书
        List<DriveBook> driveBooks = this.driveBookService.getDriveBooks(3);
        if(CollectionUtils.isNotEmpty(driveBooks) && driveBooks.size() > 10){
            driveBooks = driveBooks.subList(0,10);
        }
        List<Chapter> chapters = this.chapterService.getChaptersByBookId(Long.parseLong(bookId),Integer.parseInt(bookId) % Constants.CHAPTR_TABLE_NUM);

        model.addAttribute("tags",tags);
        model.addAttribute("maxChapterIndex",chapters.get(chapters.size()-1).getIdx());
        model.addAttribute("authorBooks",authorBooks);
        model.addAttribute("driveBooks",driveBooks);
        model.addAttribute("readBtn",readBtn);
        model.addAttribute("book",book);
        return "/product/book_detail";
    }

    /**
     * 读完一本书页面跳转
     * @param response
     * @param request
     */
    @RequestMapping("readThrough")
    public String readThrough(HttpServletResponse response, HttpServletRequest request, Model model) {
        //入参
        String bookId = request.getParameter("bookId");

        if(StringUtils.isBlank(bookId)){
            logger.error("BookController_readOverBook:bookId为空");
            return "error";
        }

        Book book = this.bookService.getBookById(Long.parseLong(bookId));

        //作者写的其他书
        List<Book> authorBooks = bookService.findListByParams("authorId",book.getAuthorId());

        //用户还看了其他书
        List<DriveBook> driveBooks = this.driveBookService.getDriveBooks(3);
        if(CollectionUtils.isNotEmpty(driveBooks) && driveBooks.size() > 10){
            driveBooks = driveBooks.subList(0,10);
        }

        model.addAttribute("authorBooks",authorBooks);
        model.addAttribute("driveBooks",driveBooks);
        //model.addAttribute("book",book.getIsFull());
        return "/product/read_through";
    }

    /**
     * 获取章节目录
     * @param response
     * @param request
     */
    @RequestMapping("getBookCatalog")
    public void getBookCatalog(HttpServletResponse response, HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String bookId = request.getParameter("bookId");
        String userId = request.getParameter("userId");

        if(StringUtils.isBlank(bookId) || StringUtils.isBlank(userId)){
            logger.error("BookController_getBookCatalog:bookId或userId为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            User user = this.userService.getUserByUserId(Long.parseLong(userId));
            //获取图书所有章节
            List<Chapter> chapters = this.chapterService.getChaptersByBookId(Long.parseLong(bookId),Integer.parseInt(bookId) % Constants.CHAPTR_TABLE_NUM);
            //获取限免图书
            DriveBook driveBook = this.driveBookService.getDriveBookByCondition(1,Long.parseLong(bookId));

            //批量或整本购买的图书
            List<UserPayBook> userPayBooks = this.userPayBookService.findListByParams("userId",userId,"bookId",bookId);
            //单章购买的图书
            List<UserPayChapter> userPayChapters = this.userPayChapterService.findListByParams("userId",userId,"bookId",bookId);
            List<Long> userPayChapterIds = new ArrayList<Long>();
            if(CollectionUtils.isNotEmpty(userPayChapters)){
                for(UserPayChapter userPayChapter : userPayChapters){
                    userPayChapterIds.add(userPayChapter.getChapterId());
                }
            }
            List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
            for(int i = 0; i < chapters.size(); i++){
                Chapter chapter = chapters.get(i);
                Map<String,Object> chapterMap = new HashMap<String,Object>();
                result.add(chapterMap);
                chapterMap.put("bookId",chapter.getBookId());
                chapterMap.put("chapterId",chapter.getChapterId());
                chapterMap.put("title",chapter.getTitle());
                chapterMap.put("volumeId",chapter.getVolumeId());
                chapterMap.put("price",chapter.getPrice());
                chapterMap.put("idx",chapter.getIdx());
                chapterMap.put("lock",true);
                if(driveBook != null) {
                    //限免图书 解锁
                    chapter.setLock(false);
                    chapterMap.put("lock",false);
                    continue;
                }
                if(chapter.getIsFree() == 0){
                    //免费章节
                    chapter.setLock(false);
                    chapterMap.put("lock",false);
                    continue;
                }

                if(user.isVip()){
                    //用户有新手礼包VIP
                    chapter.setLock(false);
                    chapterMap.put("lock",false);
                    continue;
                }

                if(CollectionUtils.isNotEmpty(userPayBooks)){
                    for(UserPayBook userPayBook : userPayBooks){
                        if(userPayBook.getType() == 2){
                            //全本购买过
                            chapter.setLock(false);
                            chapterMap.put("lock",false);
                            continue;
                        }else if(userPayBook.getType() == 1){
                            if(userPayBook.getStartChapterIdx() < chapter.getIdx() && userPayBook.getEndChapterIdx() > chapter.getIdx()){
                                //批量购买过
                                chapter.setLock(false);
                                chapterMap.put("lock",false);
                                continue;
                            }
                        }
                    }
                }

                if(CollectionUtils.isNotEmpty(userPayChapterIds)){
                    if(userPayChapterIds.contains(chapter.getChapterId())){
                        //单章购买过章节
                        chapter.setLock(false);
                        chapterMap.put("lock",false);
                        continue;
                    }
                }
            }
            sender.put("chapters",result);
            sender.send(response);

        }catch(Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 获取章节内容
     * @param response
     * @param request
     */
    @RequestMapping("getChapterContent")
    public void getChapterContent(HttpServletResponse response, HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String chapterId = request.getParameter("chapterId");
        String userId = request.getParameter("userId");
        String bookId = request.getParameter("bookId");

        if(StringUtils.isBlank(chapterId) || StringUtils.isBlank(userId)|| StringUtils.isBlank(bookId)){
            logger.error("BookController_getChapterContent:chapterId或userId或bookId为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            Chapter chapter = this.chapterService.getChapterById(Long.parseLong(chapterId),1,Integer.parseInt(bookId) % Constants.CHAPTR_TABLE_NUM);

            if(chapter.getIsFree() == 0){
                //免费章节
                chapter.setLock(false);
            }
            if(chapter.isLock()){
                //获取限免图书
                DriveBook driveBook = this.driveBookService.getDriveBookByCondition(1,Long.parseLong(bookId));
                if(driveBook != null) {
                    //限免图书 解锁
                    chapter.setLock(false);
                }
            }

            if(chapter.isLock()){
                //用户有新手礼包
                User user = this.userService.getUserByUserId(Long.parseLong(userId));
                if(user.isVip()){
                    //用户有新手礼包VIP
                    chapter.setLock(false);
                }
            }

            if(chapter.isLock()){
                //批量或整本购买的图书
                List<UserPayBook> userPayBooks = this.userPayBookService.findListByParams("userId",userId,"bookId",bookId);
                if(CollectionUtils.isNotEmpty(userPayBooks)){
                    for(UserPayBook userPayBook : userPayBooks){
                        if(userPayBook.getType() == 2){
                            //全本购买过
                            chapter.setLock(false);
                        }else if(userPayBook.getType() == 1){
                            if(userPayBook.getStartChapterIdx() < chapter.getIdx() && userPayBook.getEndChapterIdx() > chapter.getIdx()){
                                //批量购买过
                                chapter.setLock(false);
                            }
                        }
                    }
                }
            }

            if(chapter.isLock()){
                //单章购买的图书
                List<UserPayChapter> userPayChapters = this.userPayChapterService.findListByParams("userId",userId,"bookId",bookId);
                List<Long> userPayChapterIds = new ArrayList<Long>();
                if(CollectionUtils.isNotEmpty(userPayChapters)){
                    for(UserPayChapter userPayChapter : userPayChapters){
                        userPayChapterIds.add(userPayChapter.getChapterId());
                    }
                }
                if(CollectionUtils.isNotEmpty(userPayChapterIds)){
                    if(userPayChapterIds.contains(chapter.getChapterId())){
                        //单章购买过章节
                        chapter.setLock(false);
                    }
                }
            }

            //收费章节显示用户账号信息
            if(chapter.isLock()){
                UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);
                Book book = this.bookService.getBookById(Long.parseLong(bookId));

                chapter.setContent("");
                //计费方式 1:按章 2:按本
                sender.put("chargeType",book.getChargeType());
                sender.put("money",userAccount.getMoney()+userAccount.getVirtualMoney());
            }
//            else{
//                chapter.setContent(ZipUtils.gunzip(chapter.getContent()));
//            }
            //客户端需解压章节内容
            Map<String,Object> chapterMap = new HashMap<String, Object>();
            chapterMap.put("bookId",chapter.getBookId());
            chapterMap.put("chapterId",chapter.getChapterId());
            chapterMap.put("content",chapter.getContent());
            chapterMap.put("lock",chapter.isLock());
            chapterMap.put("price",chapter.getPrice());
            chapterMap.put("title",chapter.getTitle());
            chapterMap.put("volumeId",chapter.getVolumeId());
            sender.put("chapter",chapterMap);
            sender.send(response);
        }catch(Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }


    /**
     * 单章购买图书章节
     * @param response
     * @param request
     */
    @RequestMapping("buyChapter")
    public void buyChapter(HttpServletResponse response, HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String chapterId = request.getParameter("chapterId");
        String userId = request.getParameter("userId");
        String channel = request.getParameter("channel");
        String bookId = request.getParameter("bookId");

        if(StringUtils.isBlank(chapterId) || StringUtils.isBlank(bookId) || StringUtils.isBlank(userId)){
            logger.error("BookController_buyChapter:chapterId或userId或bookId为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            Chapter chapter = this.chapterService.getChapterById(Long.parseLong(chapterId),0,Integer.parseInt(bookId) % Constants.CHAPTR_TABLE_NUM);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("bookId",bookId);
            map.put("chapterId",chapterId);
            map.put("channel",channel);
            //购买
            int code = this.userService.charge(Long.parseLong(userId),chapter.getPrice(),Constants.CONSUME_TYPE_S1,map);
            if(code == 0){
                sender.put("code",0);
                sender.put("message","购买成功");
            }else if(code == -1){
                //余额不足
                //跳转到充值首页
                //response.sendRedirect("/pay/index.go?userId="+userId);
                sender.put("code",-1);
                sender.put("message","余额不足");
            }
            sender.success(response);
        }catch (Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 批量购买
     * @param response
     * @param request
     */
    @RequestMapping("toBuyBatchChapter")
    public void toBuyBatchChapter(HttpServletResponse response, HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String chapterId = request.getParameter("chapterId");
        String userId = request.getParameter("userId");
        String bookId = request.getParameter("bookId");

        if(StringUtils.isBlank(chapterId) || StringUtils.isBlank(bookId) || StringUtils.isBlank(userId)){
            logger.error("BookController_toBuyBatchChapter:chapterId或userId或bookId为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            Chapter chapter = this.chapterService.getChapterById(Long.parseLong(chapterId), 0,Integer.parseInt(bookId));
            List<Chapter> notBuyChapters = getNotBuyChapters(chapter,Long.parseLong(userId));
            //计算批量购买价格
            int price10 = 0;
            int price50 = 0;
            int price100 = 0;
            int priceAll = 0;
            for(int i = 0; i < notBuyChapters.size(); i++){
                Chapter c = notBuyChapters.get(i);
                if(i < 10){
                    price10 += c.getPrice();
                }
                if(i < 50){
                    price50 += c.getPrice();
                }
                if(i < 100){
                    price100 += c.getPrice();
                }
                priceAll += c.getPrice();
            }
            if(notBuyChapters.size() > 100){
                sender.put("price10",price10);
                sender.put("yh50","9折");
                sender.put("price50",price50);
                sender.put("yhPrice50",(int)(price50 * 0.9));
                sender.put("yh100","8折");
                sender.put("price100",price100);
                sender.put("yhPrice100",(int)(price100 * 0.8));
                sender.put("yhAll","7折");
                sender.put("priceAll",priceAll);
                sender.put("yhPriceAll",(int)(price100 * 0.7));
            }else if(notBuyChapters.size() == 100){
                sender.put("price10",price10);
                sender.put("yh50","9折");
                sender.put("price50",price50);
                sender.put("yhPrice50",(int)(price50 * 0.9));
                sender.put("yh100","8折");
                sender.put("price100",price100);
                sender.put("yhPrice100",(int)(price100 * 0.8));
                sender.put("yhAll","8折");
                sender.put("priceAll",priceAll);
                sender.put("yhPriceAll",(int)(price100 * 0.8));
            }else if(notBuyChapters.size() < 100 && notBuyChapters.size() > 50){
                sender.put("price10",price10);
                sender.put("yh50","9折");
                sender.put("price50",price50);
                sender.put("yhPrice50",(int)(price50 * 0.9));
                sender.put("yhAll","8折");
                sender.put("priceAll",priceAll);
                sender.put("yhPriceAll",(int)(price100 * 0.8));
            }else if(notBuyChapters.size() == 50){
                sender.put("price10",price10);
                sender.put("yh50","9折");
                sender.put("price50",price50);
                sender.put("yhPrice50",(int)(price50 * 0.9));
                sender.put("yhAll","9折");
                sender.put("priceAll",priceAll);
                sender.put("yhPriceAll",(int)(price100 * 0.9));
            }else if(notBuyChapters.size() < 50 && notBuyChapters.size() > 10){
                sender.put("price10",price10);
                sender.put("priceAll",priceAll);
            }else{
                sender.put("priceAll",priceAll);
            }
            UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);
            sender.put("money",userAccount.getMoney()+userAccount.getVirtualMoney());

            Map<String,Object> chapterMap = new HashMap<String, Object>();
            chapterMap.put("bookId",chapter.getBookId());
            chapterMap.put("chapterId",chapter.getChapterId());
            chapterMap.put("title",chapter.getTitle());
            chapterMap.put("volumeId",chapter.getVolumeId());
            sender.put("chapter",chapterMap);
            sender.success(response);
        }catch(Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 批量购买图书章节
     * @param response
     * @param request
     */
    @RequestMapping("buyBatchChapter")
    public void buyBatchChapter(HttpServletResponse response, HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String chapterId = request.getParameter("chapterId");
        String userId = request.getParameter("userId");
        String bookId = request.getParameter("bookId");
        String channel = request.getParameter("channel");
        //-1：购买全部
        String count = request.getParameter("count");

        if(StringUtils.isBlank(chapterId) || StringUtils.isBlank(bookId) || StringUtils.isBlank(userId)|| StringUtils.isBlank(count)){
            logger.error("BookController_buyBatchChapter:chapterId或userId或count或bookId为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            Chapter chapter = this.chapterService.getChapterById(Long.parseLong(chapterId),0,Integer.parseInt(bookId) % Constants.CHAPTR_TABLE_NUM);
            List<Chapter> notBuyChapters = this.getNotBuyChapters(chapter,Long.parseLong(userId));
            int size = 0;
            if("-1".equals(count)){
                size = notBuyChapters.size();
            }
            if(Integer.parseInt(count) > notBuyChapters.size()){
                size = notBuyChapters.size();
            }else{
                size = Integer.parseInt(count);
            }

            //计算价格
            int price = 0;
            for(int i = 0; i < size; i++){
                Chapter c = notBuyChapters.get(i);
                price += c.getPrice();
            }

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("bookId",bookId);
            map.put("startChapterId",notBuyChapters.get(0).getChapterId());
            map.put("startChapterIdx",notBuyChapters.get(0).getIdx());
            map.put("endChapterId",notBuyChapters.get(size-1).getChapterId());
            map.put("endChapterIdx",notBuyChapters.get(size-1).getIdx());
            map.put("channel",channel);
            map.put("num",size);
            //购买
            int code = this.userService.charge(Long.parseLong(userId),price,Constants.CONSUME_TYPE_S2,map);
            if(code == 0){
                sender.put("code",0);
                sender.put("message","购买成功");
            }else if(code == -1){
                //余额不足
                //跳转到充值首页
                //response.sendRedirect("/pay/index.go?userId="+userId);
                sender.put("code",-1);
                sender.put("message","余额不足");
            }
            sender.success(response);
        }catch (Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }

    }

    /**
     * 全本购买一本书
     * @param response
     * @param request
     */
    @RequestMapping("buyBook")
    public void buyBook(HttpServletResponse response, HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String userId = request.getParameter("userId");
        String bookId = request.getParameter("bookId");
        String channel = request.getParameter("channel");

        if(StringUtils.isBlank(bookId) || StringUtils.isBlank(userId)){
            logger.error("BookController_buyBook:userId或count或bookId为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            Book book = this.bookService.getBookById(Long.parseLong(bookId));

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("bookId",bookId);
            map.put("channel",channel);
            //购买
            int code = this.userService.charge(Long.parseLong(userId),book.getPrice(),Constants.CONSUME_TYPE_S3,map);
            if(code == 0){
                sender.put("code",0);
                sender.put("message","购买成功");
            }else if(code == -1){
                //余额不足
                //跳转到充值首页
                //response.sendRedirect("/pay/index.go?userId="+userId);
                sender.put("code",-1);
                sender.put("message","余额不足");
            }
            sender.success(response);
        }catch (Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }

    }

    /**
     * 定时刷充值并购买接口
     * @param response
     * @param request
     */
    @RequestMapping("ajaxBuyResponse")
    public void ajaxBuyResponse(HttpServletResponse response, HttpServletRequest request,Model model) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String userId = request.getParameter("userId");
        //1：支付宝  2：微信
        String payType = request.getParameter("payType");
        //类型 -1:充值并单章购买 -2：充值并批量购买 -3：充值并全本购买
        String type = request.getParameter("type");
        String param = request.getParameter("param");
        //订单号
        String WIDout_trade_no = request.getParameter("WIDout_trade_no");

        try{
            //购买图书
            int code = this.rechargeAndBuyBook(userId,payType,type,WIDout_trade_no,param);
            sender.put("result",code);
        }catch (Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            //系统错误
            sender.put("result",-3);
        }
    }

    /**
     * 充值并购买
     * @param response
     * @param request
     */
    @RequestMapping("buyResponse")
    public String buyResponse(HttpServletResponse response, HttpServletRequest request,Model model) {
        //入参
        String userId = request.getParameter("userId");
        //1：支付宝  2：微信
        String payType = request.getParameter("payType");
        //类型 -1:充值并单章购买 -2：充值并批量购买 -3：充值并全本购买
        String type = request.getParameter("type");
        String param = request.getParameter("param");
        //订单号
        String WIDout_trade_no = request.getParameter("WIDout_trade_no");

        try{
            //购买图书
            int code = this.rechargeAndBuyBook(userId,payType,type,WIDout_trade_no,param);
            model.addAttribute("code",code);
            model.addAttribute("userId",userId);
            model.addAttribute("payType",payType);
            model.addAttribute("type",type);
            model.addAttribute("param",param);
            model.addAttribute("WIDout_trade_no",WIDout_trade_no);
        }catch (Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            //系统错误
            model.addAttribute("code",-3);
        }
        return "/book/buy_response";
    }

    /**
     * 充值并购买
     * @param userId
     * @param payType 类型 -1:充值并单章购买 -2：充值并批量购买 -3：充值并全本购买
     * @param type 1：支付宝  2：微信
     * @param WIDout_trade_no 订单号
     * @param param
     * @return
     */
    public int rechargeAndBuyBook(String userId,String payType,String type,String WIDout_trade_no,String param) {
        boolean rechargeFlag = false;
        if (payType == "1") {
            AlipayResponse alipayResponse = alipayResponseService.findUniqueByParams("outTradeNo", WIDout_trade_no);
            if (alipayResponse != null) {
                rechargeFlag = true;
            }
        } else {
            //微信充值
            //TODO
        }
        if (rechargeFlag) {
            Map<String, Object> map = JSON.parseObject(param, Map.class);
            Integer price = 0;
            if (Constants.CONSUME_TYPE_S1 == Integer.parseInt(type)) {
                //单章购买
                Chapter chapter = this.chapterService.getChapterById(Long.parseLong(map.get("chapterId").toString()), 0, Integer.parseInt(map.get("bookId").toString()) % Constants.CHAPTR_TABLE_NUM);
                price = chapter.getPrice();
            } else if (Constants.CONSUME_TYPE_S2 == Integer.parseInt(type)) {
                //批量购买
                Chapter chapter = this.chapterService.getChapterById(Long.parseLong(map.get("chapterId").toString()), 0, Integer.parseInt(map.get("bookId").toString()) % Constants.CHAPTR_TABLE_NUM);
                List<Chapter> notBuyChapters = this.getNotBuyChapters(chapter, Long.parseLong(userId));
                int size = 0;
                if ("-1".equals(map.get("count").toString())) {
                    size = notBuyChapters.size();
                }
                if (Integer.parseInt(map.get("count").toString()) > notBuyChapters.size()) {
                    size = notBuyChapters.size();
                } else {
                    size = Integer.parseInt(map.get("count").toString());
                }

                //计算价格
                for (int i = 0; i < size; i++) {
                    Chapter c = notBuyChapters.get(i);
                    price += c.getPrice();
                }
            } else if ((Constants.CONSUME_TYPE_S3 == Integer.parseInt(type))) {
                //全本购买
                Book book = this.bookService.getBookById(Long.parseLong(map.get("bookId").toString()));
                price = book.getPrice();
            }
            //购买
            int code = this.userService.charge(Long.parseLong(userId), price, Integer.parseInt(type), map);
            return code;
        }else{
            //充值还未到账
            return -2;
        }
    }

    /**
     * 获取更新章节数据
     * @param response
     * @param request
     */
    @RequestMapping("getUpdatedChapterCount")
    public void getUpdatedChapterCount(HttpServletResponse response, HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String bookId = request.getParameter("bookId");
        String index = request.getParameter("index");

        if(StringUtils.isBlank(bookId) || StringUtils.isBlank(index)){
            logger.error("BookController_getUpdateChapterCount:bookId或index为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            int num = (int)(Long.parseLong(bookId) % Constants.CHAPTR_TABLE_NUM);
            int count = this.chapterService.updatedChapterCount(Long.parseLong(bookId),num,Integer.parseInt(index));

            sender.put("count",count);
            sender.success(response);
        }catch (Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }


    /**
     * 获取后续未购买的章节
     * @param chapter
     * @param userId
     * @return
     */
    private List<Chapter> getNotBuyChapters(Chapter chapter,Long userId){
        //获取该章后续所有章节
        List<Chapter> chapters = this.chapterService.findListByParams("startIdx",chapter.getIdx(),"num",chapter.getBookId().intValue() % Constants.CHAPTR_TABLE_NUM);
        //批量或整本购买的图书
        List<UserPayBook> userPayBooks = this.userPayBookService.findListByParams("userId",userId,"bookId",chapter.getBookId(),"type",1);
        //单章购买的图书
        List<UserPayChapter> userPayChapters = this.userPayChapterService.findListByParams("userId",userId,"bookId",chapter.getBookId());
        List<Long> userPayChapterIds = new ArrayList<Long>();
        if(CollectionUtils.isNotEmpty(userPayChapters)){
            for(UserPayChapter userPayChapter : userPayChapters){
                userPayChapterIds.add(userPayChapter.getChapterId());
            }
        }
        //找出所有未购买的章节
        List<Chapter> notBuyChapters = new ArrayList<Chapter>();
        for(int i = 0; i < chapters.size(); i++){
            Chapter c = chapters.get(i);
            if(c.getIsFree() == 0){
                continue;
            }

            if(userPayChapterIds.contains(c.getChapterId())){
                continue;
            }
            boolean flag = false;
            //是否批量购买过
            for(UserPayBook userPayBook : userPayBooks){
                if(userPayBook.getStartChapterIdx() <= c.getIdx() && userPayBook.getEndChapterIdx() >= c.getIdx()){
                    flag = true;
                    break;
                }
            }
            if(flag){
                continue;
            }
            notBuyChapters.add(c);
        }
        return notBuyChapters;
    }
}
