package com.kanshu.portal.controller;

import com.kanshu.base.controller.BaseController;
import com.kanshu.base.utils.PageFinder;
import com.kanshu.base.utils.Query;
import com.kanshu.portal.model.HistoryToday;
import com.kanshu.portal.model.HistoryTodayImg;
import com.kanshu.portal.service.IHistoryTodayImgService;
import com.kanshu.portal.service.IHistoryTodayService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping("portal")
public class PortalController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(PortalController.class);

    @Resource(name="historyTodayService")
    IHistoryTodayService historyTodayService;

    @Resource(name="historyTodayImgService")
    IHistoryTodayImgService historyTodayImgService;

    @RequestMapping("portalIndex")
    public String loginSubmit(Model model) {
        Query query = new Query();
        query.setPage(1);
        query.setPageSize(100);
        HistoryToday historyToday = new HistoryToday();
        SimpleDateFormat df = new SimpleDateFormat("MMdd");//设置日期格式
        String day = df.format(new Date());
        historyToday.setDay(day);
        PageFinder<HistoryToday> pageFinder = historyTodayService.findPageFinderObjs(historyToday,query);
        if(CollectionUtils.isNotEmpty(pageFinder.getData())) {
            for (HistoryToday history : pageFinder.getData()) {
                List<HistoryTodayImg> imgs = this.historyTodayImgService.findListByParams("historyId", history.getId());
                history.setImgs(imgs);
            }
        }
        model.addAttribute("pageFinder",pageFinder);

        return "portal/portal_index";
    }

    public static void main(String[] args) {

    }
}
