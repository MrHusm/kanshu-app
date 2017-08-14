package com.kanshu.kanshu.product.controller;

import com.kanshu.chapter.base.service.IChapterService;
import com.kanshu.kanshu.base.contants.ErrorCodeEnum;
import com.kanshu.kanshu.base.controller.BaseController;
import com.kanshu.kanshu.base.utils.JsonResultSender;
import com.kanshu.kanshu.base.utils.ResultSender;
import com.kanshu.kanshu.portal.model.DriveBook;
import com.kanshu.kanshu.portal.service.IDriveBookService;
import com.kanshu.kanshu.product.model.Book;
import com.kanshu.kanshu.product.model.Chapter;
import com.kanshu.kanshu.product.service.IBookService;
import com.kanshu.kanshu.ucenter.model.UserAccount;
import com.kanshu.kanshu.ucenter.model.UserAccountLog;
import com.kanshu.kanshu.ucenter.model.UserPayBook;
import com.kanshu.kanshu.ucenter.model.UserPayChapter;
import com.kanshu.kanshu.ucenter.service.IUserAccountLogService;
import com.kanshu.kanshu.ucenter.service.IUserAccountService;
import com.kanshu.kanshu.ucenter.service.IUserPayBookService;
import com.kanshu.kanshu.ucenter.service.IUserPayChapterService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Resource(name="userAccountService")
    IUserAccountService userAccountService;

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
     * 获取章节目录
     * @param response
     * @param request
     */
    @RequestMapping("bookDetail")
    public String bookDetail(HttpServletResponse response, HttpServletRequest request, Model model) {
        //入参
        String bookId = request.getParameter("bookId");
        String channel = request.getParameter("channel");

        if(StringUtils.isBlank(bookId)){
            logger.error("BookController_bookDetail:bookId为空");
            return "error";
        }
        Book book = this.bookService.getBookById(Long.parseLong(bookId));


        model.addAttribute("book",book);
        return "/product/book_detail";
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
            //获取图书所有章节
            List<Chapter> chapters = this.chapterService.getChaptersByBookId(Long.parseLong(bookId),Integer.parseInt(bookId) % 100);
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

            for(int i = 0; i < chapters.size(); i++){
                Chapter chapter = chapters.get(i);
                if(driveBook != null) {
                    //限免图书 解锁
                    chapter.setLock(false);
                    continue;
                }
                //用户有新手礼包
                //TODO

                if(i < 30){
                    //前30章免费
                    chapter.setLock(false);
                    continue;
                }

                if(CollectionUtils.isNotEmpty(userPayBooks)){
                    for(UserPayBook userPayBook : userPayBooks){
                        if(userPayBook.getType() == 2){
                            //全本购买过
                            chapter.setLock(false);
                            continue;
                        }else if(userPayBook.getType() == 1){
                            if(userPayBook.getStartChapterIdx() < chapter.getIdx() && userPayBook.getEndChapterIdx() > chapter.getIdx()){
                                //批量购买过
                                chapter.setLock(false);
                                continue;
                            }
                        }
                    }
                }

                if(CollectionUtils.isNotEmpty(userPayChapterIds)){
                    if(userPayChapterIds.contains(chapter.getChapterId())){
                        //单章购买过章节
                        chapter.setLock(false);
                        continue;
                    }
                }
            }
            sender.put("chapters",chapters);
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
            Chapter chapter = this.chapterService.getChapterById(Long.parseLong(chapterId),1,Integer.parseInt(bookId) % 100);

            if(chapter.getIdx() < 30){
                //前30章免费
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
                //TODO
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
            if(chapter.isLock()){
                if(chapter.getContent().length() > 100){
                    chapter.setContent(chapter.getContent().substring(0,100));
                }
            }

            //收费章节显示用户账号信息
            if(chapter.isLock()){
                UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);
                sender.put("userAccount",userAccount);
            }

            sender.put("chapter",chapter);
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
            Chapter chapter = this.chapterService.getChapterById(Long.parseLong(chapterId),0,Integer.parseInt(bookId) % 100);
            UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);
            if((userAccount.getMoney() + userAccount.getVirtualMoney()) >= chapter.getPrice()){
                UserPayChapter userPayChapter = new UserPayChapter();
                userPayChapter.setOrderNo(Long.toHexString(System.currentTimeMillis()));
                userPayChapter.setBookId(chapter.getBookId());
                userPayChapter.setChapterId(Long.parseLong(chapterId));
                userPayChapter.setUserId(Long.parseLong(userId));
                userPayChapter.setUpdateDate(new Date());
                userPayChapter.setCreateDate(new Date());
                //保存章节购买数据
                userPayChapterService.save(userPayChapter);

                UserAccountLog userAccountLog = new UserAccountLog();
                userAccountLog.setUserId(Long.parseLong(userId));
                userAccountLog.setChannel(Integer.parseInt(channel));
                userAccountLog.setOrderNo(userPayChapter.getOrderNo());
                userAccountLog.setType(-1);
                userAccountLog.setComment("");
                userAccountLog.setCreateDate(new Date());

                if(userAccount.getVirtualMoney() >= chapter.getPrice()){
                    userAccount.setVirtualMoney(userAccount.getVirtualMoney() - chapter.getPrice());

                    userAccountLog.setUnitMoney(0);
                    userAccountLog.setUnitVirtual(chapter.getPrice());
                }else{
                    userAccountLog.setUnitMoney(chapter.getPrice() - userAccount.getVirtualMoney());
                    userAccountLog.setUnitVirtual(userAccount.getVirtualMoney());

                    userAccount.setMoney(userAccount.getMoney() - (chapter.getPrice() - userAccount.getVirtualMoney()));
                    userAccount.setVirtualMoney(0);
                }
                userAccount.setUpdateDate(new Date());
                //修改账户数据
                userAccountService.update(userAccount);
                //保存账户日志数据
                userAccountLogService.save(userAccountLog);
                sender.success(response);
            }else{
                //跳转到充值首页
                response.sendRedirect("/pay/index.go?userId"+userId);
            }

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
            Chapter chapter = BookController.this.chapterService.getChapterById(Long.parseLong(chapterId), 0,Integer.parseInt(bookId));
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
            sender.put("price10",price10);
            sender.put("price50",price50);
            sender.put("price100",price100);
            sender.put("priceAll",priceAll);
            sender.put("chapter",chapter);
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
            Chapter chapter = this.chapterService.getChapterById(Long.parseLong(chapterId),0,Integer.parseInt(bookId) % 100);
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

            UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);
            if((userAccount.getMoney() + userAccount.getVirtualMoney()) >= price){
                UserPayBook userPayBook = new UserPayBook();
                userPayBook.setBookId(chapter.getBookId());
                userPayBook.setOrderNo(Long.toHexString(System.currentTimeMillis()));
                userPayBook.setStartChapterId(notBuyChapters.get(0).getChapterId());
                userPayBook.setStartChapterIdx(notBuyChapters.get(0).getIdx());
                userPayBook.setEndChapterIdx(notBuyChapters.get(size-1).getIdx());
                userPayBook.setEndChapterId(notBuyChapters.get(size-1).getChapterId());
                userPayBook.setType(1);
                userPayBook.setUserId(Long.parseLong(userId));
                userPayBook.setCreateDate(new Date());
                userPayBook.setUpdateDate(new Date());

                //保存批量购买数据
                userPayBookService.save(userPayBook);

                UserAccountLog userAccountLog = new UserAccountLog();
                userAccountLog.setUserId(Long.parseLong(userId));
                userAccountLog.setChannel(Integer.parseInt(channel));
                userAccountLog.setOrderNo(userPayBook.getOrderNo());
                userAccountLog.setType(-2);
                userAccountLog.setComment("");
                userAccountLog.setCreateDate(new Date());

                if(userAccount.getVirtualMoney() >= price){
                    userAccount.setVirtualMoney(userAccount.getVirtualMoney() - price);

                    userAccountLog.setUnitMoney(0);
                    userAccountLog.setUnitVirtual(price);
                }else{
                    userAccountLog.setUnitMoney(price - userAccount.getVirtualMoney());
                    userAccountLog.setUnitVirtual(userAccount.getVirtualMoney());

                    userAccount.setMoney(userAccount.getMoney() - (price - userAccount.getVirtualMoney()));
                    userAccount.setVirtualMoney(0);
                }
                userAccount.setUpdateDate(new Date());
                //修改账户数据
                userAccountService.update(userAccount);
                //保存账户日志数据
                userAccountLogService.save(userAccountLog);
                sender.success(response);
            }else{
                //跳转到充值首页
                response.sendRedirect("/pay/index.go?userId"+userId);
            }

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

            UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);
            if((userAccount.getMoney() + userAccount.getVirtualMoney()) >= book.getPrice()){
                UserPayBook userPayBook = new UserPayBook();
                userPayBook.setBookId(book.getBookId());
                userPayBook.setOrderNo(Long.toHexString(System.currentTimeMillis()));
                userPayBook.setType(2);
                userPayBook.setUserId(Long.parseLong(userId));
                userPayBook.setCreateDate(new Date());
                userPayBook.setUpdateDate(new Date());

                //保存批量购买数据
                userPayBookService.save(userPayBook);

                UserAccountLog userAccountLog = new UserAccountLog();
                userAccountLog.setUserId(Long.parseLong(userId));
                userAccountLog.setChannel(Integer.parseInt(channel));
                userAccountLog.setOrderNo(userPayBook.getOrderNo());
                userAccountLog.setType(-3);
                userAccountLog.setComment("");
                userAccountLog.setCreateDate(new Date());

                if(userAccount.getVirtualMoney() >= book.getPrice()){
                    userAccount.setVirtualMoney(userAccount.getVirtualMoney() - book.getPrice());

                    userAccountLog.setUnitMoney(0);
                    userAccountLog.setUnitVirtual(book.getPrice());
                }else{
                    userAccountLog.setUnitMoney(book.getPrice() - userAccount.getVirtualMoney());
                    userAccountLog.setUnitVirtual(userAccount.getVirtualMoney());

                    userAccount.setMoney(userAccount.getMoney() - (book.getPrice() - userAccount.getVirtualMoney()));
                    userAccount.setVirtualMoney(0);
                }
                userAccount.setUpdateDate(new Date());
                //修改账户数据
                userAccountService.update(userAccount);
                //保存账户日志数据
                userAccountLogService.save(userAccountLog);
                sender.success(response);
            }else{
                //跳转到充值首页
                response.sendRedirect("/pay/index.go?userId"+userId);
            }
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
        List<Chapter> chapters = BookController.this.chapterService.findListByParams("startIdx",chapter.getIdx());
        //批量或整本购买的图书
        List<UserPayBook> userPayBooks = BookController.this.userPayBookService.findListByParams("userId",userId,"bookId",chapter.getBookId(),"type",1);
        //单章购买的图书
        List<UserPayChapter> userPayChapters = BookController.this.userPayChapterService.findListByParams("userId",userId,"bookId",chapter.getBookId());
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
