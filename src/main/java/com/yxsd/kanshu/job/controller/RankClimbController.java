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
import java.util.ArrayList;
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
            List<String> childUrls = new ArrayList<String>();
            //编辑推荐文字推荐6本
            Elements editLists = doc.getElementsByAttributeValue("data-eid","qd_A110");
            if(editLists != null && editLists.size() > 0){
                for(Element editList : editLists){
                    String editListUrl = editList.attr("href");
                    //logger.info("编辑推荐文字推荐6本url："+ editListUrl);
                    childUrls.add(editListUrl);
                }
            }
            //编辑推荐图片推荐2本
            Elements editImgLists = doc.getElementsByAttributeValue("data-eid","qd_A172");
            if(editImgLists != null && editImgLists.size() > 0){
                for(Element editImgList : editImgLists){
                    String editImgUrl = editImgList.attr("href");
                    //logger.info("编辑推荐图片推荐2本url："+ editImgUrl);
                    childUrls.add(editImgUrl);
                }
            }
            for(String childUrl : childUrls){
                if(childUrl.indexOf("http:") == -1){
                    childUrl = "http:" + childUrl;
                }
                Document childDoc = Jsoup.connect(childUrl)
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL
                Elements bookEles = childDoc.select(".book-info");
                if(bookEles != null && bookEles.size() > 0){
                    String title = bookEles.get(0).child(0).child(0).text();
                    logger.info("编辑推荐文字和图片推荐书名："+ title);
                    //saveDrive(title,1,0);
                }
            }

            //热门作品左侧的3本封面推荐
            Elements hotEles = doc.getElementsByAttributeValue("data-eid","qd_A121");
            if(hotEles != null && hotEles.size() > 0){
                for(Element hotEle : hotEles){
                    String title = hotEle.child(0).attr("alt");
                    logger.info("热门作品左侧的3本封面推荐书名："+ title);
                    //saveDrive(title,1,0);
                }
            }

            //新书推荐左侧的3本封面推荐
            Elements newEles = doc.getElementsByAttributeValue("data-eid","qd_A138");
            if(newEles != null && newEles.size() > 0){
                for(Element newEle : newEles){
                    String title = newEle.child(0).attr("alt");
                    logger.info("新书推荐左侧的3本封面推荐书名："+ title);
                    //saveDrive(title,1,0);
                }
            }

            //完本精品左侧的3本封面推荐
            Elements finishEles = doc.getElementsByAttributeValue("data-eid","qd_A129");
            if(finishEles != null && finishEles.size() > 0){
                for(Element finishEle : finishEles){
                    String title = finishEle.child(0).attr("alt");
                    logger.info("完本精品左侧的3本封面推荐书名："+ title);
                    //saveDrive(title,1,0);
                }
            }
            //起点女生网
            String girlUrl = "http://www.qdmm.com/";
            Document girlDoc = Jsoup.connect(girlUrl)
                    .userAgent(USER_AGENT) // 设置 User-Agent
                    .cookie("auth", "token") // 设置 cookie
                    .timeout(10000)           // 设置连接超时时间
                    .get();                 // 使用 POST 方法访问 URL
            //起点女生首页封推前4本
            Element girlThumb = girlDoc.getElementById("thumb");
            if(girlThumb != null){
                Elements eleThumbBooks = girlThumb.children();
                if(eleThumbBooks != null && eleThumbBooks.size() > 0){
                    for(int i = 0; i < eleThumbBooks.size() - 1; i++){
                        String title = eleThumbBooks.get(i).text().trim();
                        logger.info("起点女生封推前4本书名："+ title);
                        //saveDrive(title,1,0);
                    }
                }
            }

            //起点女生首页编辑推荐，全部图书（需要注意，封面有7本；中间文字推有6本，最后还有2个小banner推荐）
            Elements girlEditEles = girlDoc.select(".description");
            if(girlEditEles != null && girlEditEles.size() > 0){
                String title = girlEditEles.get(0).child(0).child(0).child(0).text();
                logger.info("起点女生首页编辑推荐图片推荐书名："+ title);
            }


            //起点女生周点击榜的10本书
            //起点女生新书推荐左侧的3本封面推荐
            //起点女生完本精选的5本封面推荐
            //
            //起点首页本周强推17本
            Elements strongEles = doc.getElementsByAttributeValue("data-eid","qd_A103");
            if(strongEles != null && strongEles.size() > 0){
                for(Element strongEle : strongEles){
                    String title = strongEle.text();
                    logger.info("起点首页本周强推17本书名："+ title);
                    //saveDrive(title,1,0);
                }
            }

            //起点女生首页本周强推15本图书
            //http://r.qidian.com/hotsales?style=1&page=1 加这个页面24小时热销榜的数据首页图书排重
            //http://r.qidian.com/mm/hotsales?style=1 加这个页面24小时热销榜的数据



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
