package com.yxsd.kanshu.product.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.product.dao.ICategoryDao;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.model.Category;
import com.yxsd.kanshu.product.service.IBookService;
import com.yxsd.kanshu.product.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2017/8/7.
 */
@Service(value="categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements ICategoryService{

    @Resource(name="categoryDao")
    private ICategoryDao categoryDao;

    @Resource(name="bookService")
    IBookService bookService;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,Category> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,Category> slaveRedisTemplate;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,List<Category>> listMasterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,List<Category>> listSlaveRedisTemplate;

    @Override
    public IBaseDao<Category> getBaseDao() {
        return categoryDao;
    }


    @Override
    public List<Category> getCategorysByPid(Long pid,int type) {
        String key = RedisKeyConstants.CACHE_CATEGORY_LIST_PID_KEY + pid;
        List<Category> categories = null;
        if(listMasterRedisTemplate.hasKey(key)){
            categories = listSlaveRedisTemplate.opsForValue().get(key);
        }
        if(CollectionUtils.isEmpty(categories)){
            categories = findListByParams("pid",pid);
            if(CollectionUtils.isNotEmpty(categories)){
                if(type == 1){
                    List<Category> list = new ArrayList<Category>();
                    for(Category category : categories){
                        Book book = this.bookService.selectOneBookByCategerySecId(category.getCategoryId());
                        if(book != null){
                            list.add(category);
                        }
                    }
                    if(list.size() > 0){
                        categories = list;
                        listMasterRedisTemplate.opsForValue().set(key, categories, 1, TimeUnit.HOURS);
                    }else{
                        categories = null;
                    }
                }else{
                    listMasterRedisTemplate.opsForValue().set(key, categories, 1, TimeUnit.HOURS);
                }
            }
        }
        return categories;
    }
}
