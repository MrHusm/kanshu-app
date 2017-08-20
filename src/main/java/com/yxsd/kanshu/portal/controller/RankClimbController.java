package com.yxsd.kanshu.portal.controller;

import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.portal.model.DriveBook;
import com.yxsd.kanshu.portal.service.IDriveBookService;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.service.IBookService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/8/20.
 */
@Controller
@Scope("prototype")
@RequestMapping("rankClimb")
public class RankClimbController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RankClimbController.class);

    private static final String  USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36";

    @Resource(name="driveBookService")
    IDriveBookService driveBookService;

    @Resource(name="bookService")
    IBookService bookService;

    @RequestMapping("climbData")
    public void climbData() {
        int p = 1;
        logger.info("开始爬虫畅销榜");
        while (true){
            String baseUrl = "http://dushu.qq.com/ranklist.html?rankid=ma_w_a_cr_10000_50_1&p=";
            try {
                logger.info("开始爬虫page："+p);
                Document doc = Jsoup.connect(baseUrl+p)
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL

                Elements eleBooks = doc.select(".book");

                if(eleBooks == null || eleBooks.size() == 0){
                    break;
                }else{
                    int i = 0;
                    for(Element eleBook : eleBooks){
                        i++;
                        Element eleTitle =  eleBook.child(0);
                        String title = eleTitle.attr("title");
                        logger.info("title:"+title);
                        if(StringUtils.isNotBlank(title)){
                            List<Book> books = this.bookService.findListByParams("title",eleTitle.attr("title"));
                            if(CollectionUtils.isNotEmpty(books)){
                                Book book = books.get(0);
                                if(book != null){
                                    DriveBook driveBook = new DriveBook();
                                    driveBook.setBookId(book.getBookId());
                                    driveBook.setType(3);
                                    driveBook.setScore((p-1)*50+i);
                                    driveBook.setCreateDate(new Date());
                                    driveBookService.save(driveBook);
                                }

                            }
                        }
                    }
                }
                p++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("畅销榜爬虫结束");
    }

    public static void main(String[] args) {
        RankClimbController climbController = new RankClimbController();
        climbController.climbData();
    }

}
