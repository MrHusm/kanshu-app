package com.yxsd.kanshu.product.controller;

import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.product.service.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/14.
 */
@Controller
@Scope("prototype")
@RequestMapping("category")
public class CategoryController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Resource(name="bookService")
    IBookService bookService;
}
