package com.yxsd.kanshu.job.controller;

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

    /**
     * 首页驱动爬虫
     */
    @RequestMapping("climbIndexDrive")
    public void climbIndexDrive() {
        logger.info("开始爬虫起点网站首页驱动");
        String baseUrl = "http://www.qidian.com/";
        try {
            Document doc = Jsoup.connect(baseUrl)
                    .userAgent(USER_AGENT) // 设置 User-Agent
                    .cookie("auth", "token") // 设置 cookie
                    .timeout(10000)           // 设置连接超时时间
                    .get();                 // 使用 POST 方法访问 URL
            //封推前4本
            Element thumb = doc.getElementById("thumb");
            if(thumb != null){
                Elements eleThumbBooks = thumb.children();
                if(eleThumbBooks != null && eleThumbBooks.size() > 0){
                    for(int i = 0; i < eleThumbBooks.size() - 1; i++){
                        String title = eleThumbBooks.get(i).text().trim();
                        logger.info("封推前4本书名："+ title);
                        //saveDrive(title,1,0);
                    }
                }
            }
            //编辑推荐7本
            Elements slides = doc.select(".slideItem");
            if(slides != null && slides.size() > 0){
                for(Element slideItem : slides){
                    String title = slideItem.child(0).child(0).attr("title");
                    logger.info("编辑推荐7本书名："+ title);
                    //saveDrive(title,1,0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("畅销榜爬虫结束");
    }

    /**
     * 将图书保存到驱动表
     * @param title  书名
     * @param type   驱动类型   1：首页驱动 2：搜索榜 3：畅销榜
     * @param score  分数
     */
    public void saveDrive(String title,Integer type,Integer score) {
        if (StringUtils.isNotBlank(title)) {
            List<Book> books = bookService.findListByParams("title", title);
            if (CollectionUtils.isNotEmpty(books)) {
                Book book = books.get(0);
                if (book != null) {
                    DriveBook driveBook = new DriveBook();
                    driveBook.setBookId(book.getBookId());
                    driveBook.setType(type);
                    driveBook.setScore(score);
                    driveBook.setCreateDate(new Date());
                    driveBookService.save(driveBook);
                    logger.info("驱动保存成功书名_"+title+"_type+"+type+"_score"+score);
                }else{
                    logger.info("保存驱动查询图书不存在:"+title);
                }
            }
        }
    }
    public static void main(String[] args) {
        RankClimbController climbController = new RankClimbController();
        climbController.climbIndexDrive();
    }

}
