package com.yxsd.kanshu.portal.controller;

import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.portal.model.DriveBook;
import com.yxsd.kanshu.portal.model.Special;
import com.yxsd.kanshu.portal.service.IDriveBookService;
import com.yxsd.kanshu.portal.service.ISpecialService;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.model.Category;
import com.yxsd.kanshu.product.service.IBookService;
import com.yxsd.kanshu.product.service.ICategoryService;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

    @Resource(name="bookService")
    IBookService bookService;

    @Resource(name="specialService")
    ISpecialService specialService;


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
        String type = request.getParameter("type");
        String version = request.getParameter("version");
        String syn = request.getParameter("syn")==null?"0":request.getParameter("syn");
        model.addAttribute("syn",syn);
        if(StringUtils.isNotBlank(version)){
            model.addAttribute("version",Integer.parseInt(version.replace(".","")));
        }
        Query query = new Query();
        if(StringUtils.isNotBlank(page)){
            query.setPage(Integer.parseInt(page));
        }else{
            query.setPage(1);
        }
        query.setPageSize(20);
        if(StringUtils.isBlank(type)){
            //默认取首页驱动数据
            type = "1";
        }
        PageFinder<DriveBook> pageFinder = this.driveBookService.findPageWithCondition(Integer.parseInt(type),query);
        if(query.getPage() == 1){
            //第一页面查询专题数据
            List<Special> specials = this.specialService.findListByParamsObjs(null);
            List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
            for(DriveBook driveBook : pageFinder.getData()){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("type",1);
                map.put("data",driveBook);
                result.add(map);
            }
            if(CollectionUtils.isNotEmpty(specials)){
                for(int i = 0; i < specials.size(); i++){
                    Special special = specials.get(i);
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("type",2);
                    map.put("data",special);
                    result.add(special.getIndex()+i,map);
                }
            }
            model.addAttribute("result",result);
        }
        model.addAttribute("pageFinder",pageFinder);
        model.addAttribute("type",type);
        return "/portal/portal_index";
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
        List<Category> parentCategorys = this.categoryService.getCategorysByPid(0L,0);
        List<Map<String,List<Category>>> data = new ArrayList<Map<String,List<Category>>>();
        for(Category parentCategory : parentCategorys){
            Map<String,List<Category>> map = new HashMap<String, List<Category>>();
            List<Category> categories = this.categoryService.getCategorysByPid(parentCategory.getCategoryId(),1);
            map.put(parentCategory.getName(),categories);
            data.add(map);
        }
        model.addAttribute("data",data);
        return "/portal/category_index";
    }

    /**
     * 分类下面的书籍
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("categoryBooks")
    public String categoryBooks(HttpServletResponse response, HttpServletRequest request, Model model) {
        //入参
        String categoryId = request.getParameter("categoryId");
        String childCategoryId = request.getParameter("childCategoryId");
        String isFull = request.getParameter("isFull");
        String page = request.getParameter("page");
        String syn = request.getParameter("syn")==null?"0":request.getParameter("syn");
        String version = request.getParameter("version");
        if(StringUtils.isNotBlank(version)){
            model.addAttribute("version",Integer.parseInt(version.replace(".","")));
        }

        Category category = this.categoryService.findUniqueByParams("categoryId",categoryId);
        List<Category> childCategorys = this.categoryService.getCategorysByPid(Long.parseLong(categoryId),1);

        Query query = new Query();
        if(StringUtils.isNotBlank(page)){
            query.setPage(Integer.parseInt(page));
        }else{
            query.setPage(1);
        }
        query.setPageSize(20);
        Book condition = new Book();
        condition.setShelfStatus(1);
        condition.setCategorySecId(Long.parseLong(categoryId));
        condition.setCategoryThrId(StringUtils.isBlank(childCategoryId) ? null : Long.parseLong(childCategoryId));
        condition.setIsFull(StringUtils.isBlank(isFull) ? null : Integer.parseInt(isFull));

        PageFinder<Book> pageFinder = this.bookService.findPageFinderWithExpandObjs(condition, query);

        model.addAttribute("category",category);
        model.addAttribute("childCategorys",childCategorys);
        model.addAttribute("pageFinder",pageFinder);
        model.addAttribute("categoryId",categoryId);
        if(StringUtils.isNotBlank(childCategoryId)){
            model.addAttribute("childCategoryId",childCategoryId);
        }
        if(StringUtils.isNotBlank(isFull)){
            model.addAttribute("isFull",isFull);
        }
        model.addAttribute("page",page);
        model.addAttribute("syn",syn);

        return "/portal/category_books";
    }

    /**
     * 标签下面的书籍
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("tagBooks")
    public String tagBooks(HttpServletResponse response, HttpServletRequest request, Model model) {
        //入参
        String tag = null;
        try {
            tag = URLDecoder.decode(request.getParameter("tag"),"UTF-8");
            model.addAttribute("tag", URLEncoder.encode(URLEncoder.encode(tag,"UTF-8"),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("tag:"+tag);
        String page = request.getParameter("page");
        String syn = request.getParameter("syn")==null?"0":request.getParameter("syn");
        String version = request.getParameter("version");
        if(StringUtils.isNotBlank(version)){
            model.addAttribute("version",Integer.parseInt(version.replace(".","")));
        }

        Query query = new Query();
        if(StringUtils.isNotBlank(page)){
            query.setPage(Integer.parseInt(page));
        }else{
            query.setPage(1);
        }
        query.setPageSize(20);
        Book condition = new Book();
        condition.setShelfStatus(1);
        condition.setTag("%" + tag + "%");
        PageFinder<Book> pageFinder = this.bookService.findPageFinderWithExpandObjs(condition, query);

        model.addAttribute("pageFinder",pageFinder);
        model.addAttribute("page",page);
        model.addAttribute("syn",syn);
        return "/portal/tag_books";
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
        String version = request.getParameter("version");
        String channel = request.getParameter("channel");
        if(StringUtils.isNotBlank(version)){
            model.addAttribute("version",Integer.parseInt(version.replace(".","")));
        }
        model.addAttribute("syn",syn);
        model.addAttribute("type",type);

        Query query = new Query();
        if(StringUtils.isNotBlank(page)){
            query.setPage(Integer.parseInt(page));
        }else{
            query.setPage(1);
        }
        query.setPageSize(20);
        PageFinder<DriveBook> pageFinder = this.driveBookService.findPageWithCondition(Integer.parseInt(type), query);

        if("0".equals(syn) && "9".equals(type)){
            //免费页
            List<Map<String,Object>> data = this.driveBookService.getTimeFreeBooks(channel);
            if(CollectionUtils.isNotEmpty(data)){
                model.addAttribute("data",data);
            }
        }

        model.addAttribute("pageFinder",pageFinder);
        if("9".equals(type)){
            return "/portal/free_index";
        }else{
            return "/portal/rank_list";
        }
    }
}
