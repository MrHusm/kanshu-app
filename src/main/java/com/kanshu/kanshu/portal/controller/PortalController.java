package com.kanshu.kanshu.portal.controller;

import com.kanshu.kanshu.base.controller.BaseController;
import com.kanshu.kanshu.base.utils.PageFinder;
import com.kanshu.kanshu.base.utils.Query;
import com.kanshu.kanshu.portal.service.IDriveBookService;
import com.kanshu.kanshu.portal.service.IHistoryTodayImgService;
import com.kanshu.kanshu.portal.model.DriveBook;
import com.kanshu.kanshu.portal.service.IHistoryTodayService;

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

@Controller
@Scope("prototype")
@RequestMapping("portal")
public class PortalController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(PortalController.class);
    
    @Resource(name="driveBookService")
    IDriveBookService iDriveBookService;

    @Resource(name="historyTodayService")
    IHistoryTodayService historyTodayService;

    @Resource(name="historyTodayImgService")
    IHistoryTodayImgService historyTodayImgService;

    /**
     * 精选页
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("portalIndex")
    public String portalIndex(HttpServletResponse response, HttpServletRequest request, Model model,Short type) {
        String page = request.getParameter("page");
        String syn = request.getParameter("syn")==null?"0":request.getParameter("syn");
        model.addAttribute("syn",syn);
        Query query = new Query();
        if(StringUtils.isNotBlank(page)){
            query.setPage(Integer.parseInt(page));
        }else{
            query.setPage(1);
        }
        query.setPageSize(5);
        DriveBook driveBook = new DriveBook();
        driveBook.setType(type);
        
        PageFinder<DriveBook> pageFinder = iDriveBookService.findPageFinderObjs(driveBook,query);
        model.addAttribute("pageFinder",pageFinder);

        return "portal/portal_index";
    }
    
    

//    /**
//     * 精选页
//     * @param model
//     * @param response
//     * @param request
//     * @return
//     */
//    @RequestMapping("portalIndex")
//    public String portalIndex(HttpServletResponse response, HttpServletRequest request, Model model) {
//        String page = request.getParameter("page");
//        String syn = request.getParameter("syn")==null?"0":request.getParameter("syn");
//        model.addAttribute("syn",syn);
//        Query query = new Query();
//        if(StringUtils.isNotBlank(page)){
//            query.setPage(Integer.parseInt(page));
//        }else{
//            query.setPage(1);
//        }
//        query.setPageSize(5);
//        HistoryToday historyToday = new HistoryToday();
//        SimpleDateFormat df = new SimpleDateFormat("MMdd");//设置日期格式
//        String day = df.format(new Date());
//        historyToday.setDay(day);
//        PageFinder<HistoryToday> pageFinder = historyTodayService.findPageFinderObjs(historyToday,query);
//        if(CollectionUtils.isNotEmpty(pageFinder.getData())) {
//            for (HistoryToday history : pageFinder.getData()) {
//                List<HistoryTodayImg> imgs = this.historyTodayImgService.findListByParams("historyId", history.getId());
//                history.setImgs(imgs);
//            }
//        }
//        model.addAttribute("pageFinder",pageFinder);
//
//        return "portal/portal_index";
//    }

}
