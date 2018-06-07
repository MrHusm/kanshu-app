package com.yxsd.kanshu.job.controller;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
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
import org.springframework.data.redis.core.RedisTemplate;
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

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,DriveBook> masterRedisTemplate;

    /**
     * 首页驱动爬虫
     */
    @RequestMapping("climbIndexDrive")
    public void climbIndexDrive() {
        logger.info("开始爬虫首页驱动");
        Document doc = null;
        try {
            String baseUrl = "http://www.qidian.com/";
            doc = Jsoup.connect(baseUrl)
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
                        logger.info("爬虫首页驱动封推前4本书名："+ title);
                        saveDrive(title,1,0);
                    }
                }else{
                    logger.info("爬虫首页驱动封推前4本未获取到数据");
                }
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动封推前4本异常");
            e.printStackTrace();
        }
        try{
            //编辑推荐7本
            Elements slides = doc.select(".slideItem");
            if(slides != null && slides.size() > 0){
                for(Element slideItem : slides){
                    String title = slideItem.child(0).child(0).attr("title");
                    logger.info("首页驱动编辑推荐7本书名："+ title);
                    saveDrive(title,1,0);
                }
            }else{
                logger.info("爬虫首页驱动编辑推荐7本未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动编辑推荐7本异常");
            e.printStackTrace();
        }

        try{
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
                    logger.info("首页驱动编辑推荐文字和图片推荐书名："+ title);
                    saveDrive(title,1,0);
                }
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动编辑推荐文字和图片推荐异常");
            e.printStackTrace();
        }

        try{
            //热门作品左侧的3本封面推荐
            Elements hotEles = doc.getElementsByAttributeValue("data-eid","qd_A121");
            if(hotEles != null && hotEles.size() > 0){
                for(Element hotEle : hotEles){
                    String title = hotEle.child(0).attr("alt");
                    logger.info("首页驱动热门作品左侧的3本封面推荐书名："+ title);
                    saveDrive(title,1,0);
                }
            }else{
                logger.info("爬虫首页驱动热门作品左侧的3本封面推荐未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动热门作品左侧的3本封面推荐异常");
            e.printStackTrace();
        }

        try{
            //新书推荐左侧的3本封面推荐
            Elements newEles = doc.getElementsByAttributeValue("data-eid","qd_A138");
            if(newEles != null && newEles.size() > 0){
                for(Element newEle : newEles){
                    String title = newEle.child(0).attr("alt");
                    logger.info("首页驱动新书推荐左侧的3本封面推荐书名："+ title);
                    saveDrive(title,1,0);
                }
            }else{
                logger.info("爬虫首页驱动新书推荐左侧的3本封面推荐未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动新书推荐左侧的3本封面推荐异常");
            e.printStackTrace();
        }

        try{
            //完本精品左侧的3本封面推荐
            Elements finishEles = doc.getElementsByAttributeValue("data-eid","qd_A129");
            if(finishEles != null && finishEles.size() > 0){
                for(Element finishEle : finishEles){
                    String title = finishEle.child(0).attr("alt");
                    logger.info("首页驱动完本精品左侧的3本封面推荐书名："+ title);
                    saveDrive(title,1,0);
                }
            }else{
                logger.info("爬虫首页驱动完本精品左侧的3本封面推荐未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动完本精品左侧的3本封面推荐异常");
            e.printStackTrace();
        }

        Document girlDoc = null;
        try{
            //起点女生网
            String girlUrl = "http://www.qdmm.com/";
            girlDoc = Jsoup.connect(girlUrl)
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
                        logger.info("首页驱动起点女生封推前4本书名："+ title);
                        saveDrive(title,1,0);
                    }
                }
            }else{
                logger.info("爬虫首页驱动起点女生封推前4本未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动起点女生封推前4本异常");
            e.printStackTrace();
        }

        try{
            //起点女生首页编辑推荐，全部图书（需要注意，封面有7本；中间文字推有6本，最后还有2个小banner推荐）
            Elements girlEditEles = girlDoc.select(".description");
            if(girlEditEles != null && girlEditEles.size() > 0){
                String title = girlEditEles.get(0).child(0).child(0).child(0).text();
                logger.info("首页驱动起点女生首页编辑推荐图片推荐书名："+ title);
            }else{
                logger.info("爬虫首页驱动起点女生首页编辑推荐图片推荐未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动起点女生首页编辑推荐图片推荐异常");
            e.printStackTrace();
        }


        try{
            //起点女生周点击榜的10本书
            Elements girlClickEles = girlDoc.getElementsByAttributeValue("data-eid","qd_A147");
            if(girlClickEles != null && girlClickEles.size() > 0){
                for(Element girlClickEle : girlClickEles){
                    String title = girlClickEle.text();
                    logger.info("首页驱动起点女生周点击榜的10本书名："+ title);
                    saveDrive(title,1,0);
                }
            }else{
                logger.info("爬虫首页驱动起点女生周点击榜的10本未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动起点女生周点击榜的10本异常");
            e.printStackTrace();
        }

        try{
            //起点女生新书推荐左侧的3本封面推荐
            Elements girlNewEles = girlDoc.getElementsByAttributeValue("data-eid","qd_A138");
            if(girlNewEles != null && girlNewEles.size() > 0){
                for(Element girlNewEle : girlNewEles){
                    String title = girlNewEle.child(0).attr("alt");
                    logger.info("首页驱动起点女生新书推荐左侧的3本封面推荐书名："+ title);
                    saveDrive(title,1,0);
                }
            }else{
                logger.info("爬虫首页驱动起点女生新书推荐左侧的3本封面推荐未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动起点女生新书推荐左侧的3本封面推荐异常");
            e.printStackTrace();
        }

        try{
            //起点女生完本精品的5本封面推荐
            Elements girlFullEles = girlDoc.select(".fin-list > ul li");
            if(girlFullEles != null && girlFullEles.size() > 0){
                for(Element girlFullEle : girlFullEles){
                    String title = girlFullEle.child(0).attr("alt");
                    logger.info("首页驱动起点女生完本精品的5本封面推荐书名："+ title);
                }
            }else{
                logger.info("爬虫首页驱动起点女生完本精品的5本封面推荐未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动起点女生完本精品的5本封面推荐异常");
            e.printStackTrace();
        }

        try{
            //起点首页本周强推17本
            Elements strongEles = doc.getElementsByAttributeValue("data-eid","qd_A103");
            if(strongEles != null && strongEles.size() > 0){
                for(Element strongEle : strongEles){
                    String title = strongEle.text();
                    logger.info("首页驱动起点首页本周强推17本书名："+ title);
                    saveDrive(title,1,0);
                }
            }else{
                logger.info("爬虫首页驱动起点首页本周强推17本未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动起点首页本周强推17本异常");
            e.printStackTrace();
        }

        try{
            //起点女生首页本周强推15本图书
            Elements girlStrongEles = girlDoc.getElementsByAttributeValue("data-eid","qd_A103");
            if(girlStrongEles != null && girlStrongEles.size() > 0){
                for(Element girlStrongEle : girlStrongEles){
                    String title = girlStrongEle.text();
                    logger.info("首页驱动起点女生首页本周强推15本图书书名："+ title);
                    saveDrive(title,1,0);
                }
            }else{
                logger.error("爬虫首页驱动起点女生首页本周强推15本图书未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动起点女生首页本周强推15本图书异常");
            e.printStackTrace();
        }

        //http://r.qidian.com/hotsales?style=1&page=1 加这个页面24小时热销榜的数据首页图书排重
        //起点24小时热销榜
        Document saleDoc = null;
        try{
            String saleUrl = "http://r.qidian.com/hotsales?style=1&page=1";
            saleDoc = Jsoup.connect(saleUrl)
                    .userAgent(USER_AGENT) // 设置 User-Agent
                    .cookie("auth", "token") // 设置 cookie
                    .timeout(10000)           // 设置连接超时时间
                    .get();                 // 使用 POST 方法访问 URL
            Elements saleEles = saleDoc.getElementsByAttributeValue("data-eid","qd_C40");
            if(saleEles != null && saleEles.size() > 0){
                for(Element saleEle : saleEles){
                    String title = saleEle.text();
                    logger.info("首页驱动24小时热销榜20本书名："+ title);
                    saveDrive(title,1,0);
                }
            }else{
                logger.info("爬虫首页驱动24小时热销榜20本未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动24小时热销榜20本异常");
            e.printStackTrace();
        }

        //http://r.qidian.com/mm/hotsales?style=1 加这个页面24小时热销榜的数据
        //起点女生24小时热销榜
        Document girlSaleDoc = null;
        try{
            String girlSaleUrl = "http://r.qidian.com/mm/hotsales?style=1";
            girlSaleDoc = Jsoup.connect(girlSaleUrl)
                    .userAgent(USER_AGENT) // 设置 User-Agent
                    .cookie("auth", "token") // 设置 cookie
                    .timeout(10000)           // 设置连接超时时间
                    .get();                 // 使用 POST 方法访问 URL
            Elements girlSaleEles = girlSaleDoc.getElementsByAttributeValue("data-eid","qd_C40");
            if(girlSaleEles != null && girlSaleEles.size() > 0){
                for(Element girlSaleEle : girlSaleEles){
                    String title = girlSaleEle.text();
                    logger.info("首页驱动起点女生24小时热销榜20本书名："+ title);
                    saveDrive(title,1,0);
                }
            }else{
                logger.info("爬虫首页驱动起点女生24小时热销榜20本未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动起点女生24小时热销榜20本异常");
            e.printStackTrace();
        }

        //上线驱动
        onlineDrive(1);

        logger.info("结束爬虫首页驱动");

    }

    /**
     * 首页二次元驱动爬虫
     */
    @RequestMapping("climbSecDrive")
    public void climbSecDrive() {
        logger.info("开始爬虫首页二次元驱动爬虫");
        Document doc = null;
        try {
            //http://a.qidian.com/?chanId=12&orderId=&page=1&style=1&pageSize=20&siteid=1&hiddenField=0
            //这个链接里选中二次元的分类，按照人气值排序
            doc = Jsoup.connect("http://a.qidian.com/?chanId=12&orderId=&page=1&style=1&pageSize=20&siteid=1&hiddenField=0")
                    .userAgent(USER_AGENT) // 设置 User-Agent
                    .cookie("auth", "token") // 设置 cookie
                    .timeout(10000)           // 设置连接超时时间
                    .get();                 // 使用 POST 方法访问 URL
            Elements eles = doc.getElementsByAttributeValue("data-eid","qd_B58");
            if(eles != null && eles.size() > 0){
                for(Element ele : eles){
                    String title = ele.text();
                    logger.info("首页二次元第一个链接20本书名："+ title);
                    saveDrive(title,4,0);
                }
            }else{
                logger.info("爬虫首页驱动二次元第一个链接20本未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动二次元第一个链接20本异常");
            e.printStackTrace();
        }

        Document doc2 = null;
        try{
            //https://www.readnovel.com/all?pageNum=1&pageSize=10&gender=2&catId=30055&isFinish=-1&isVip=-1&size=-1&updT=-1&orderBy=0
            //这个链接N次元分类
            doc2 = Jsoup.connect("https://www.readnovel.com/all?pageNum=1&pageSize=10&gender=2&catId=30055&isFinish=-1&isVip=-1&size=-1&updT=-1&orderBy=0")
                    .userAgent(USER_AGENT) // 设置 User-Agent
                    .cookie("auth", "token") // 设置 cookie
                    .timeout(10000)           // 设置连接超时时间
                    .get();                 // 使用 POST 方法访问 URL

            Elements eles2 = doc2.select(".book-info");
            if(eles2 != null && eles2.size() > 0){
                for(Element ele : eles2){
                    String title = ele.child(0).child(0).text();
                    logger.info("首页二次元第二个链接10本书名："+ title);
                    saveDrive(title,4,0);
                }
            }else{
                logger.error("爬虫首页驱动二次元第二个链接10本未获取到数据");
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动二次元第二个链接10本异常");
            e.printStackTrace();
        }

        try{
            //http://chuangshi.qq.com/bk/2cy/so3/
            //这个链接二次元分类
            String baseUrl = "http://chuangshi.qq.com/bk/2cy/so3/p/%d.html";
            for(int i = 1; i < 5; i++) {
                Document doc3 = Jsoup.connect(String.format(baseUrl, i))
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL

                Elements eles3 = doc3.select(".green");
                if (eles3 != null && eles3.size() > 0) {
                    for (Element ele : eles3) {
                        String title = ele.text();
                        logger.info("首页二次元第三个链接100本书名：i_" + i + "_title_" + title);
                        saveDrive(title,4,0);
                    }
                }else{
                    logger.error("爬虫首页驱动二次元第二个链接10本未获取到数据_i="+i);
                }
            }
        }catch(Exception e){
            logger.error("爬虫首页驱动二次元第二个链接10本异常");
            e.printStackTrace();
        }
        //上线驱动
        onlineDrive(4);
        logger.info("结束爬虫首页二次元驱动爬虫");
    }


    /**
     * 首页男生驱动爬虫
     */
    @RequestMapping("climbBoyDrive")
    public void climbBoyDrive() {
        logger.info("开始爬虫首页男生驱动爬虫");
        try {
            //http://a.qidian.com/?page=1&style=1&pageSize=20&siteid=1&hiddenField=0
            //这个链接选择男生→全部，按照人气值排序
            String baseUrl = "http://a.qidian.com/?page=1&style=1&pageSize=20&siteid=1&hiddenField=0&page=%d";
            for(int i = 1; i < 40; i++){
                Document doc = Jsoup.connect(String.format(baseUrl,i))
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL
                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_B58");
                if(elements != null && elements.size() > 0){
                    for(Element ele : elements){
                        String title = ele.text();
                        logger.info("首页男生驱动20本书名：i_"+i+"_title_"+ title);
                        saveDrive(title,2,0);
                    }
                }else{
                    logger.info("爬虫首页男生驱动未获取到数据i="+i);
                }
            }
        }catch (Exception e){
            logger.error("爬虫首页男生驱动异常");
            e.printStackTrace();
        }
        //上线驱动
        onlineDrive(2);
        logger.info("结束爬虫首页男生驱动");
    }

    /**
     * 首页女生驱动爬虫
     */
    @RequestMapping("climbGirlDrive")
    public void climbGirlDrive() {
        logger.info("开始爬虫首页女生驱动爬虫");
        try {
            //http://a.qidian.com/mm?orderId=&style=1&pageSize=20&siteid=0&hiddenField=0&page=1
            //这个链接选择女生→全部，按照人气值排序
            String baseUrl = "http://a.qidian.com/mm?orderId=&style=1&pageSize=20&siteid=0&hiddenField=0&page=%d";
            for(int i = 1; i < 40; i++){
                Document doc = Jsoup.connect(String.format(baseUrl,i))
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL
                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_B58");
                if(elements != null && elements.size() > 0){
                    for(Element ele : elements){
                        String title = ele.text();
                        logger.info("首页女生驱动20本书名：i_"+i+"_title_"+ title);
                        saveDrive(title,3,0);
                    }
                }else{
                    logger.info("爬虫首页女生驱动未获取到数据i="+i);
                }
            }
        }catch (Exception e){
            logger.error("爬虫首页女生驱动异常");
            e.printStackTrace();
        }
        //上线驱动
        onlineDrive(3);
        logger.info("结束爬虫首页女生驱动爬虫");
    }

    /**
     * 全站畅销驱动爬虫
     */
    @RequestMapping("climbSaleDrive")
    public void climbSaleDrive() {
        logger.info("开始爬虫全站畅销驱动");
        try {
            //http://r.qidian.com/yuepiao?style=1
            //http://r.qidian.com/mm/yuepiao?style=1
            //http://r.qidian.com/vipreward?style=1
            //http://r.qidian.com/mm/subscr?style=1
            //https://www.readnovel.com/rank/hotsales?period=2&pageNum=1
            String baseUrl1 = "http://r.qidian.com/yuepiao?style=1&page=%d";
            for(int i = 1; i < 20; i++){
                Document doc = Jsoup.connect(String.format(baseUrl1,i))
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL
                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
                if(elements != null && elements.size() > 0){
                    for(Element ele : elements){
                        String title = ele.text();
                        logger.info("全站畅销驱动第一个链接20本书名：i_"+i+"_title_"+ title);
                        saveDrive(title,6,0);
                    }
                }else{
                    logger.info("爬虫全站畅销驱动第一个链接未获取到数据i="+i);
                }
            }
        }catch (Exception e){
            logger.error("爬虫全站畅销驱动第一个链接异常");
            e.printStackTrace();
        }

        try{
            String baseUrl2 = "http://r.qidian.com/mm/yuepiao?style=1&page=%d";
            for(int i = 1; i < 20; i++){
                Document doc = Jsoup.connect(String.format(baseUrl2,i))
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL
                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
                if(elements != null && elements.size() > 0){
                    for(Element ele : elements){
                        String title = ele.text();
                        logger.info("全站畅销驱动第二个链接20本书名：i_"+i+"_title_"+ title);
                        saveDrive(title,6,0);
                    }
                }else{
                    logger.info("爬虫全站畅销驱动第二个链接未获取到数据i="+i);
                }
            }
        }catch (Exception e){
            logger.error("爬虫全站畅销驱动第二个链接异常");
            e.printStackTrace();
        }

        try{
            String baseUrl3 = "http://r.qidian.com/vipreward?style=1&page=%d";
            for(int i = 1; i < 6; i++){
                Document doc = Jsoup.connect(String.format(baseUrl3,i))
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL
                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
                if(elements != null && elements.size() > 0){
                    for(Element ele : elements){
                        String title = ele.text();
                        logger.info("全站畅销驱动第三个链接20本书名：i_"+i+"_title_"+ title);
                        saveDrive(title,6,0);
                    }
                }else{
                    logger.info("爬虫全站畅销驱动第三个链接未获取到数据i="+i);
                }
            }
        }catch (Exception e){
            logger.error("爬虫全站畅销驱动第三个链接异常");
            e.printStackTrace();
        }

        try{
            String baseUrl4 = "http://r.qidian.com/mm/subscr?style=1&page=%d";
            for(int i = 1; i < 20; i++){
                Document doc = Jsoup.connect(String.format(baseUrl4,i))
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL
                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
                if(elements != null && elements.size() > 0){
                    for(Element ele : elements){
                        String title = ele.text();
                        logger.info("全站畅销驱动第四个链接20本书名：i_"+i+"_title_"+ title);
                        saveDrive(title,6,0);
                    }
                }else{
                    logger.info("爬虫全站畅销驱动第四个链接未获取到数据i="+i);
                }
            }
        }catch (Exception e){
            logger.error("爬虫全站畅销驱动第四个链接异常");
            e.printStackTrace();
        }

        try{
            String baseUrl5 = "https://www.readnovel.com/rank/hotsales?period=2&pageNum=%d";
            for(int i = 1; i < 20; i++){
                Document doc = Jsoup.connect(String.format(baseUrl5,i))
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL
                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
                if(elements != null && elements.size() > 0){
                    for(Element ele : elements){
                        String title = ele.text();
                        logger.info("全站畅销驱动第五个链接20本书名：i_"+i+"_title_"+ title);
                        saveDrive(title,6,0);
                    }
                }else{
                    logger.info("爬虫全站畅销驱动第五个链接未获取到数据i="+i);
                }
            }
        }catch (Exception e){
            logger.error("爬虫全站畅销驱动第五个链接异常");
            e.printStackTrace();
        }
        //上线驱动
        onlineDrive(6);
        logger.info("结束爬虫全站畅销驱动");
    }

    /**
     * 完结精选驱动爬虫
     */
    @RequestMapping("climbFullDrive")
    public void climbFullDrive() {
        logger.info("开始爬虫完结精选驱动");
        try {
//            http://r.qidian.com/fin?style=1
//            http://fin.qidian.com/mm
//            https://www.readnovel.com/finish
            String baseUrl1 = "http://r.qidian.com/fin?style=1&page=%d";
            for (int i = 1; i < 20; i++) {
                Document doc = Jsoup.connect(String.format(baseUrl1,i))
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL
                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
                if(elements != null && elements.size() > 0){
                    for(Element ele : elements){
                        String title = ele.text();
                        logger.info("完结精选驱动第一个链接20本书名：i_"+i+"_title_"+ title);
                        saveDrive(title,7,0);
                    }
                }else{
                    logger.error("爬虫完结精选驱动第一个链接未获取到数据i="+i);
                }
            }
        }catch (Exception e){
            logger.error("爬虫完结精选驱动第一个链接异常");
            e.printStackTrace();
        }

        try{
            String baseUrl2 = "http://fin.qidian.com/mm?action=hidden&orderId=&style=1&pageSize=20&siteid=0&hiddenField=2&page=%d";
            for (int i = 1; i < 20; i++) {
                Document doc = Jsoup.connect(String.format(baseUrl2,i))
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL
                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_B58");
                if(elements != null && elements.size() > 0){
                    for(Element ele : elements){
                        String title = ele.text();
                        logger.info("完结精选驱动第二个链接20本书名：i_"+i+"_title_"+ title);
                        saveDrive(title,7,0);
                    }
                }else{
                    logger.error("爬虫完结精选驱动第二个链接未获取到数据i="+i);
                }
            }
        }catch (Exception e){
            logger.error("爬虫完结精选驱动第二个链接异常");
            e.printStackTrace();
        }

        try{
            String baseUrl3 = "https://www.readnovel.com/finish?pageSize=10&gender=2&catId=-1&isFinish=1&isVip=-1&size=-1&updT=-1&orderBy=0&pageNum=%d";
            for (int i = 1; i < 20; i++) {
                Document doc = Jsoup.connect(String.format(baseUrl3,i))
                        .userAgent(USER_AGENT) // 设置 User-Agent
                        .cookie("auth", "token") // 设置 cookie
                        .timeout(10000)           // 设置连接超时时间
                        .get();                 // 使用 POST 方法访问 URL
                Elements eles = doc.select(".book-info");
                if(eles != null && eles.size() > 0){
                    for(Element ele : eles){
                        String title = ele.child(0).child(0).text();
                        logger.info("完结精选驱动第三个链接10本书名：i_"+i+"_title_"+ title);
                        saveDrive(title,7,0);
                    }
                }else{
                    logger.error("爬虫完结精选驱动第三个链接未获取到数据i="+i);
                }
            }
        }catch (Exception e){
            logger.error("爬虫完结精选驱动第三个链接异常");
            e.printStackTrace();
        }
        //上线驱动
        onlineDrive(7);
        logger.info("结束爬虫完结精选驱动");

    }

    /**
     * 重磅新书驱动爬虫
     */
    @RequestMapping("climbNewDrive")
    public void climbNewDrive() {
        logger.info("开始爬虫重磅新书驱动");
//        http://r.qidian.com/signnewbook?style=1
//        http://r.qidian.com/mm/signnewbook?style=1
//        http://r.qidian.com/pubnewbook?style=1
//        http://r.qidian.com/mm/pubnewbook?style=1
//        http://r.qidian.com/newsign?style=1
//        http://r.qidian.com/mm/newsign?style=1
//        http://r.qidian.com/newauthor?style=1
//        http://r.qidian.com/mm/newauthor?style=1
//        https://www.readnovel.com/rank/newbook?period=2&pageNum=1
//        String baseUrl1 = "http://r.qidian.com/signnewbook?style=1&page=%d";
//        try{
//            for (int i = 1; i < 20; i++) {
//                Document doc = Jsoup.connect(String.format(baseUrl1,i))
//                        .userAgent(USER_AGENT) // 设置 User-Agent
//                        .cookie("auth", "token") // 设置 cookie
//                        .timeout(10000)           // 设置连接超时时间
//                        .get();                 // 使用 POST 方法访问 URL
//                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
//                if(elements != null && elements.size() > 0){
//                    for(Element ele : elements){
//                        String title = ele.text();
//                        logger.info("重磅新书驱动第一个链接20本书名：i_"+i+"_title_"+ title);
//                        saveDrive(title,8,0);
//                    }
//                }else{
//                    logger.error("爬虫重磅新书驱动第一个链接未获取到数据i="+i);
//                }
//            }
//        }catch (Exception e){
//            logger.error("爬虫重磅新书驱动第一个链接异常");
//            e.printStackTrace();
//        }

//        String baseUrl2 = "http://r.qidian.com/mm/signnewbook?style=1&page=%d";
//        try{
//            for (int i = 1; i < 5; i++) {
//                Document doc = Jsoup.connect(String.format(baseUrl2,i))
//                        .userAgent(USER_AGENT) // 设置 User-Agent
//                        .cookie("auth", "token") // 设置 cookie
//                        .timeout(10000)           // 设置连接超时时间
//                        .get();                 // 使用 POST 方法访问 URL
//                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
//                if(elements != null && elements.size() > 0){
//                    for(Element ele : elements){
//                        String title = ele.text();
//                        logger.info("重磅新书驱动第二个链接20本书名：i_"+i+"_title_"+ title);
//                        saveDrive(title,8,0);
//                    }
//                }else{
//                    logger.info("爬虫重磅新书驱动第二个链接未获取到数据i="+i);
//                }
//            }
//        }catch (Exception e){
//            logger.error("爬虫重磅新书驱动第二个链接异常");
//            e.printStackTrace();
//        }

//        String baseUrl3 = "http://r.qidian.com/pubnewbook?style=1&page=%d";
//        try{
//            for (int i = 1; i < 20; i++) {
//                Document doc = Jsoup.connect(String.format(baseUrl3,i))
//                        .userAgent(USER_AGENT) // 设置 User-Agent
//                        .cookie("auth", "token") // 设置 cookie
//                        .timeout(10000)           // 设置连接超时时间
//                        .get();                 // 使用 POST 方法访问 URL
//                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
//                if(elements != null && elements.size() > 0){
//                    for(Element ele : elements){
//                        String title = ele.text();
//                        logger.info("重磅新书驱动第三个链接20本书名：i_"+i+"_title_"+ title);
//                        saveDrive(title,8,0);
//                    }
//                }else{
//                    logger.info("爬虫重磅新书驱动第三个链接未获取到数据i="+i);
//                }
//            }
//        }catch (Exception e){
//            logger.error("爬虫重磅新书驱动第三个链接异常");
//            e.printStackTrace();
//        }

//        String baseUrl4 = "http://r.qidian.com/mm/pubnewbook?style=1&page=%d";
//        try{
//            for (int i = 1; i < 15; i++) {
//                Document doc = Jsoup.connect(String.format(baseUrl4,i))
//                        .userAgent(USER_AGENT) // 设置 User-Agent
//                        .cookie("auth", "token") // 设置 cookie
//                        .timeout(10000)           // 设置连接超时时间
//                        .get();                 // 使用 POST 方法访问 URL
//                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
//                if(elements != null && elements.size() > 0){
//                    for(Element ele : elements){
//                        String title = ele.text();
//                        logger.info("重磅新书驱动第四个链接20本书名：i_"+i+"_title_"+ title);
//                        saveDrive(title,8,0);
//                    }
//                }else{
//                    logger.info("爬虫重磅新书驱动第四个链接未获取到数据i="+i);
//                }
//            }
//        }catch (Exception e){
//            logger.error("爬虫重磅新书驱动第四个链接异常");
//            e.printStackTrace();
//        }

//        String baseUrl5 = "http://r.qidian.com/newsign?style=1&page=%d";
//        try{
//            for (int i = 1; i < 20; i++) {
//                Document doc = Jsoup.connect(String.format(baseUrl5,i))
//                        .userAgent(USER_AGENT) // 设置 User-Agent
//                        .cookie("auth", "token") // 设置 cookie
//                        .timeout(10000)           // 设置连接超时时间
//                        .get();                 // 使用 POST 方法访问 URL
//                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
//                if(elements != null && elements.size() > 0){
//                    for(Element ele : elements){
//                        String title = ele.text();
//                        logger.info("重磅新书驱动第五个链接20本书名：i_"+i+"_title_"+ title);
//                        saveDrive(title,8,0);
//                    }
//                }else{
//                    logger.info("爬虫重磅新书驱动第五个链接未获取到数据i="+i);
//                }
//            }
//        }catch (Exception e){
//            logger.error("爬虫重磅新书驱动第五个链接异常");
//            e.printStackTrace();
//        }

//        String baseUrl6 = "http://r.qidian.com/mm/newsign?style=1&page=%d";
//        try{
//            for (int i = 1; i < 10; i++) {
//                Document doc = Jsoup.connect(String.format(baseUrl6,i))
//                        .userAgent(USER_AGENT) // 设置 User-Agent
//                        .cookie("auth", "token") // 设置 cookie
//                        .timeout(10000)           // 设置连接超时时间
//                        .get();                 // 使用 POST 方法访问 URL
//                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
//                if(elements != null && elements.size() > 0){
//                    for(Element ele : elements){
//                        String title = ele.text();
//                        logger.info("重磅新书驱动第六个链接20本书名：i_"+i+"_title_"+ title);
//                        saveDrive(title,8,0);
//                    }
//                }else{
//                    logger.info("爬虫重磅新书驱动第六个链接未获取到数据i="+i);
//                }
//            }
//        }catch (Exception e){
//            logger.error("爬虫重磅新书驱动第六个链接异常");
//            e.printStackTrace();
//        }

//        String baseUrl7 = "http://r.qidian.com/newauthor?style=1&page=%d";
//        try{
//            for (int i = 1; i < 20; i++) {
//                Document doc = Jsoup.connect(String.format(baseUrl7,i))
//                        .userAgent(USER_AGENT) // 设置 User-Agent
//                        .cookie("auth", "token") // 设置 cookie
//                        .timeout(10000)           // 设置连接超时时间
//                        .get();                 // 使用 POST 方法访问 URL
//                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
//                if(elements != null && elements.size() > 0){
//                    for(Element ele : elements){
//                        String title = ele.text();
//                        logger.info("重磅新书驱动第七个链接20本书名：i_"+i+"_title_"+ title);
//                        saveDrive(title,8,0);
//                    }
//                }else{
//                    logger.info("爬虫重磅新书驱动第七个链接未获取到数据i="+i);
//                }
//            }
//        }catch (Exception e){
//            logger.error("爬虫重磅新书驱动第七个链接异常");
//            e.printStackTrace();
//        }

//        String baseUrl8 = "http://r.qidian.com/mm/newauthor?style=1&page=%d";
//        try{
//            for (int i = 1; i < 20; i++) {
//                Document doc = Jsoup.connect(String.format(baseUrl8,i))
//                        .userAgent(USER_AGENT) // 设置 User-Agent
//                        .cookie("auth", "token") // 设置 cookie
//                        .timeout(10000)           // 设置连接超时时间
//                        .get();                 // 使用 POST 方法访问 URL
//                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
//                if(elements != null && elements.size() > 0){
//                    for(Element ele : elements){
//                        String title = ele.text();
//                        logger.info("重磅新书驱动第八个链接20本书名：i_"+i+"_title_"+ title);
//                        saveDrive(title,8,0);
//                    }
//                }else{
//                    logger.info("爬虫重磅新书驱动第八个链接未获取到数据i="+i);
//                }
//            }
//        }catch (Exception e){
//            logger.error("爬虫重磅新书驱动第八个链接异常");
//            e.printStackTrace();
//        }

//        String baseUrl9 = "https://www.readnovel.com/rank/newbook?period=2&pageNum=%d";
//        try{
//            for (int i = 1; i < 11; i++) {
//                Document doc = Jsoup.connect(String.format(baseUrl9,i))
//                        .userAgent(USER_AGENT) // 设置 User-Agent
//                        .cookie("auth", "token") // 设置 cookie
//                        .timeout(10000)           // 设置连接超时时间
//                        .get();                 // 使用 POST 方法访问 URL
//                Elements elements = doc.getElementsByAttributeValue("data-eid","qd_C40");
//                if(elements != null && elements.size() > 0){
//                    for(Element ele : elements){
//                        String title = ele.text();
//                        logger.info("重磅新书驱动第九个链接10本书名：i_"+i+"_title_"+ title);
//                        saveDrive(title,8,0);
//                    }
//                }else{
//                    logger.info("爬虫重磅新书驱动第九个链接未获取到数据i="+i);
//                }
//            }
//        }catch (Exception e){
//            logger.error("爬虫重磅新书驱动第九个链接异常");
//            e.printStackTrace();
//        }
        List<Book> books = this.bookService.selectNewBook();
        for(Book book : books){
            DriveBook driveBook = this.driveBookService.findUniqueByParams("type",8,"bookId",book.getBookId(),"status",0);
            if(driveBook == null){
                driveBook = new DriveBook();
                driveBook.setBookId(book.getBookId());
                driveBook.setType(8);
                driveBook.setScore(0);
                driveBook.setStatus(0);
                driveBook.setManType(0);
                driveBook.setCreateDate(new Date());
                driveBook.setUpdateDate(new Date());
                driveBookService.save(driveBook);
                logger.info("驱动保存成功书名_"+book.getTitle()+"_type+"+8+"_score_"+0);
            }else{
                logger.info("驱动图书已存在_"+book.getTitle()+"_type+"+8);
            }
        }

        //上线驱动
        onlineDrive(8);
        logger.info("结束爬虫重磅新书驱动");
    }

    /**
     * 手工驱动job
     */
    @RequestMapping("manDriveBook")
    public void manDriveBook() {
        logger.info("开始执行手工配置驱动");

    }

    /**
     * 将图书保存到驱动表
     * @param title  书名
     * @param type   驱动类型   1：首页驱动 2：首页男生最爱 3：首页女生频道
     * 4：首页二次元 5：大家都在搜索 6：书库全站畅销
     * 7：书库完结精选 8：书库重磅新书 9：限免 10：书籍相关图书
     * @param score  分数
     * @param manType  1:手工配置 0：网站抓取
     */
    public void saveDrive(String title,Integer type,Integer score,Integer manType) {
        if (StringUtils.isNotBlank(title)) {
            List<Book> books = bookService.findListByParams("title", title);
            if (CollectionUtils.isNotEmpty(books)) {
                Book book = books.get(0);
                if (book != null) {
                    DriveBook driveBook = this.driveBookService.findUniqueByParams("type",type,"bookId",book.getBookId(),"status",0);
                    if(driveBook == null){
                        driveBook = new DriveBook();
                        driveBook.setBookId(book.getBookId());
                        driveBook.setType(type);
                        driveBook.setScore(score);
                        driveBook.setStatus(0);
                        driveBook.setManType(manType == null ? 0 : manType);
                        driveBook.setCreateDate(new Date());
                        driveBook.setUpdateDate(new Date());
                        driveBookService.save(driveBook);
                        logger.info("驱动保存成功书名_"+title+"_type+"+type+"_score_"+score);
                    }else{
                        logger.info("驱动图书已存在_"+title+"_type+"+type);
                    }
                }else{
                    logger.info("保存驱动查询图书不存在:"+title);
                }
            }
        }
    }

    public void saveDrive(String title,Integer type,Integer score){
        saveDrive(title,type,score,0);
    }

    /**
     * 上线新驱动
     * @param type 类型 1：首页驱动 2：首页男生最爱 3：首页女生频道
     * 4：首页二次元 5：大家都在搜索 6：书库全站畅销
     * 7：书库完结精选 8：书库重磅新书 9：限免 10：书籍相关图书
     */
    public void onlineDrive(Integer type){
        logger.info("开始上线驱动type="+type);
        List<DriveBook> newDriveBooks = this.driveBookService.findListByParams("type",type,"status",0,"manType",0);
        if(newDriveBooks != null && newDriveBooks.size() > 10){
            //查询出来放入缓存
            List<DriveBook> oldDriveBooks = this.driveBookService.findListByParams("type",type,"status",1,"manType",0);
            if(CollectionUtils.isNotEmpty(oldDriveBooks)){
                for(DriveBook oldDriveBook : oldDriveBooks){
                    //删除老的驱动
                    this.driveBookService.deleteById(oldDriveBook.getId());
                    //清除缓存
                    String key = String.format(RedisKeyConstants.CACHE_DRIVE_BOOK_ONE_KEY,type,oldDriveBook.getBookId(),1);
                    masterRedisTemplate.delete(key);
                }
            }
            for(DriveBook newDriveBook : newDriveBooks){
                //新驱动上线
                newDriveBook.setStatus(1);
                this.driveBookService.update(newDriveBook);
            }
            //清除缓存
            String key =String.format(RedisKeyConstants.CACHE_DRIVE_BOOK_KEY, type, 1);
            masterRedisTemplate.delete(key);
        }
        logger.info("结束上线驱动type="+type);
    }


//    public static void climbBook1(){
//        String baseUrl = "http://www.ireader.com/index.php?ca=bookrank.ranklistdata&pca=bookrank.ranklist&rankId=14877&page=%d";
//        try{
//            for (int i = 1; i < 8; i++) {
//                Document doc = Jsoup.connect(String.format(baseUrl,i))
//                        .userAgent(USER_AGENT) // 设置 User-Agent
//                        .cookie("auth", "token") // 设置 cookie
//                        .timeout(10000)           // 设置连接超时时间
//                        .get();                 // 使用 POST 方法访问 URL
//                Elements elements = doc.getElementsByAttributeValue("class","secCol");
//                if(elements != null && elements.size() > 0){
//                    for(Element ele : elements){
//                        String title = ele.text();
//                        System.out.println(title);
//                    }
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        RankClimbController climbController = new RankClimbController();
        //climbController.climbIndexDrive();
        //climbController.climbSecDrive();
        //climbController.climbBoyDrive();
        //climbController.climbGirlDrive();
        //climbController.climbSaleDrive();
        //climbController.climbFullDrive();
        //climbController.climbNewDrive();
        //climbController.climbBook1();
    }

}
