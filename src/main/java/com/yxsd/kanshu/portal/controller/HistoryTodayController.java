package com.yxsd.kanshu.portal.controller;

import com.yxsd.kanshu.base.contants.ErrorCodeEnum;
import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.JsonResultSender;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.base.utils.ResultSender;
import com.yxsd.kanshu.portal.model.HistoryTodayImg;
import com.yxsd.kanshu.portal.service.IHistoryTodayImgService;
import com.yxsd.kanshu.portal.model.HistoryToday;
import com.yxsd.kanshu.portal.service.IHistoryTodayService;
import com.yxsd.kanshu.ucenter.controller.UserController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author hushengmeng
 * @date 2017/7/7.
 */
@Controller
@Scope("prototype")
@RequestMapping("historyToday")
public class HistoryTodayController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String  USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36";

    @Resource(name="historyTodayService")
    IHistoryTodayService historyTodayService;

    @Resource(name="historyTodayImgService")
    IHistoryTodayImgService historyTodayImgService;

    @RequestMapping("climbData")
    public void climbData() {
        String baseUrl = "http://www.people.com.cn/GB/historic/";
        for(int i = 1; i < 13; i++){
            for(int j = 1; j < 32; j++){
                String day = "";
                if(i < 10){
                    day += "0"+i;
                }else{
                    day += i;
                }
                if(j < 10){
                    day += "0"+j;
                }else{
                    day += j;
                }
                try {
                    logger.info("开始爬虫day："+day);
                    Document doc = Jsoup.connect(baseUrl+day)
                            .userAgent(USER_AGENT) // 设置 User-Agent
                            .cookie("auth", "token") // 设置 cookie
                            .timeout(10000)           // 设置连接超时时间
                            .get();                 // 使用 POST 方法访问 URL

                    Elements links = doc.select("td.t14l14 > a");
                    for (Element link : links) {
                        String linkHref = link.attr("abs:href");
                        //创建请求
                        URL url = new URL(linkHref);
                        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setUseCaches(Boolean.FALSE);
                        connection.addRequestProperty("Connection", "close");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        connection.addRequestProperty("Cookie","你的Cookies" );
                        //开始请求
                        Document secondDoc = Jsoup.parse(connection.getInputStream(), "GBK", linkHref);

                        Element title = secondDoc.select("td.FontMax > b").first();
                        Element content = secondDoc.select("td.content").first();
                        Date now = new Date();
                        HistoryToday historyToday = new HistoryToday();
                        historyToday.setTitle(title.html());
                        historyToday.setContent(content.html());
                        historyToday.setDay(day);
                        historyToday.setCreateDate(now);
                        historyToday.setUpdateDate(now);
                        historyTodayService.save(historyToday);

                        Elements imgs = secondDoc.select("img[src^=/media]");
                        if(!imgs.isEmpty()){
                            for(int k = 0; k < imgs.size(); k++){
                                Element imgEle = imgs.get(k);
                                String imgSrc = imgEle.attr("abs:src");
                                String path = getSession().getServletContext().getRealPath("/media");
                                String fileName = UUID.randomUUID().toString()+ "."+ FilenameUtils.getExtension(imgSrc);
                                //下载图片
                                if(download(imgSrc,path,fileName)){
                                    HistoryTodayImg img = new HistoryTodayImg();
                                    img.setHistoryId(historyToday.getId());
                                    img.setIndex(k);
                                    img.setCreateDate(now);
                                    img.setUpdateDate(now);
                                    img.setFileName(fileName);
                                    if(k == 0){
                                        Element imgTitle =  imgEle.parent();
                                        img.setTitle(imgTitle.text());
                                    }else{
                                        Element imgTitle =  imgEle.parent().parent().nextElementSibling();
                                        img.setTitle(imgTitle.text());
                                    }
                                    historyTodayImgService.save(img);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean download(String url,String path,String fileName) {
        boolean flag = true;
        try {
            URL uri = new URL(url);
            InputStream in = uri.openStream();
            File file = new File(path +File.separator+fileName);
            System.out.println(path +File.separator+fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fo = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int length = 0;
            logger.info("开始下载:srcPath:" + url + "destPath:"+path+File.separator+fileName);
            while ((length = in.read(buf, 0, buf.length)) != -1) {
                fo.write(buf, 0, length);
            }
            in.close();
            fo.close();
            logger.info("下载完成");
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            logger.error("下载失败");
        }
        return flag;
    }


    /**
     * 获取历史的今天数据列表
     * @param start
     * @param count
     * @param day
     * @return
     */
    @RequestMapping("historyTodayList")
    public void getHistoryTodayList(HttpServletResponse response,HttpServletRequest request,Integer start,Integer count,String day) {
        ResultSender sender = JsonResultSender.getInstance();
        try{
            Query query = new Query();
            if(start == null){
                start = 1;
            }
            query.setPage(start);
            if(count == null || count < 0){
                query.setPageSize(Integer.MAX_VALUE);
            }else{
                query.setPageSize(count);
            }
            HistoryToday historyToday = new HistoryToday();
            if(StringUtils.isBlank(day)){
                SimpleDateFormat df = new SimpleDateFormat("MMdd");//设置日期格式
                day = df.format(new Date());
            }
            historyToday.setDay(day);
            PageFinder<HistoryToday> historyTodays = historyTodayService.findPageFinderObjs(historyToday,query);
            if(CollectionUtils.isNotEmpty(historyTodays.getData())){
                for(HistoryToday history :  historyTodays.getData()){
                    List<HistoryTodayImg> imgs = this.historyTodayImgService.findListByParams("historyId",history.getId());
                    if(CollectionUtils.isNotEmpty(imgs)){
                        for(HistoryTodayImg img : imgs){
                            img.setFileName("http://120.25.125.138:8082/media/"+img.getFileName());
                        }
                    }
                    history.setImgs(imgs);
                }
                sender.put("historyTodays",historyTodays.getData());

                sender.send(response);
            }else{
                sender.fail(ErrorCodeEnum.ERROR_CODE_10007.getErrorCode(),
                        ErrorCodeEnum.ERROR_CODE_10007.getErrorMessage(), response);
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("系统错误："+ request.getRequestURL());
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 根据ID获取历史的今天详情数据
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("historyTodayDetail")
    public String getHistoryTodayDetail(Model model,@RequestParam(value="id",required=true)String id) {
        HistoryToday historyToday = this.historyTodayService.get(Long.parseLong(id));
        model.addAttribute("historyToday",historyToday);
        return "/portal/history_today_detail";
    }














    public static void main(String[] args) {
        HistoryTodayController t = new HistoryTodayController();
        t.climbData();
    }
}
