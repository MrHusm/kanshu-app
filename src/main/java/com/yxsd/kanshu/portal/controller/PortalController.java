package com.yxsd.kanshu.portal.controller;

import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.portal.model.DriveBook;
import com.yxsd.kanshu.portal.service.IDriveBookService;
import com.yxsd.kanshu.product.model.Category;
import com.yxsd.kanshu.product.service.ICategoryService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("portal")
public class PortalController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(PortalController.class);
    
    @Resource(name="driveBookService")
    IDriveBookService driveBookService;

    @Resource(name="categoryService")
    ICategoryService categoryService;


    /**
     * 精选页
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("portalIndex")
    public String portalIndex(HttpServletResponse response, HttpServletRequest request, Model model) {
        String page = request.getParameter("page");
        //String type = request.getParameter("type");
        String syn = request.getParameter("syn")==null?"0":request.getParameter("syn");
        model.addAttribute("syn",syn);
        Query query = new Query();
        if(StringUtils.isNotBlank(page)){
            query.setPage(Integer.parseInt(page));
        }else{
            query.setPage(1);
        }
        query.setPageSize(20);
        DriveBook driveBook = new DriveBook();
        driveBook.setType(3);
        PageFinder<DriveBook> pageFinder = driveBookService.findPageFinderObjs(driveBook,query);
        model.addAttribute("pageFinder",pageFinder);

        return "portal/portal_index";
    }

    /**
     * 书库
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("categoryIndex")
    public String categoryIndex(HttpServletResponse response, HttpServletRequest request, Model model) {
        List<Category> parentCategorys = this.categoryService.getCategorysByPid(0L);
        List<Map<String,List<Category>>> data = new ArrayList<Map<String,List<Category>>>();
        for(Category parentCategory : parentCategorys){
            Map<String,List<Category>> map = new HashMap<String, List<Category>>();
            List<Category> categories = this.categoryService.getCategorysByPid(parentCategory.getCategoryId());
            map.put(parentCategory.getName(),categories);
            data.add(map);
        }
        model.addAttribute("data",data);
        return "portal/category_index";
    }

    /**
     * 榜单
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("rankList")
    public String rankList(HttpServletResponse response, HttpServletRequest request, Model model) {
        String page = request.getParameter("page");
        String type = request.getParameter("type");
        String syn = request.getParameter("syn")==null?"0":request.getParameter("syn");
        model.addAttribute("syn",syn);
        Query query = new Query();
        if(StringUtils.isNotBlank(page)){
            query.setPage(Integer.parseInt(page));
        }else{
            query.setPage(1);
        }
        query.setPageSize(20);
        DriveBook driveBook = new DriveBook();
        driveBook.setType(Integer.parseInt(type));
        PageFinder<DriveBook> pageFinder = driveBookService.findPageFinderObjs(driveBook,query);
        model.addAttribute("pageFinder",pageFinder);
        model.addAttribute("type",type);

        return "portal/rankList";
    }

}
