package com.kanshu.product.controller;

import com.kanshu.base.contants.ErrorCodeEnum;
import com.kanshu.base.controller.BaseController;
import com.kanshu.base.utils.JsonResultSender;
import com.kanshu.base.utils.ResultSender;
import com.kanshu.portal.model.DriveBook;
import com.kanshu.portal.service.IDriveBookService;
import com.kanshu.product.model.Chapter;
import com.kanshu.product.service.IBookService;
import com.kanshu.product.service.IChapterService;
import com.kanshu.ucenter.model.UserPayBook;
import com.kanshu.ucenter.model.UserPayChapter;
import com.kanshu.ucenter.service.IUserPayBookService;
import com.kanshu.ucenter.service.IUserPayChapterService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
            List<Chapter> chapters = this.chapterService.getChaptersByBookId(Long.parseLong(bookId));
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
            Chapter chapter = this.chapterService.getChapterWithContentById(Long.parseLong(chapterId));

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
            sender.put("chapter",chapter);
            sender.send(response);
        }catch(Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }
}
