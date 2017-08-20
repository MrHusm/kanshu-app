package com.yxsd.kanshu.product.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.product.dao.ICategoryDao;
import com.yxsd.kanshu.product.model.Category;
import com.yxsd.kanshu.product.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2017/8/7.
 */
@Service(value="categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements ICategoryService{

    @Resource(name="categoryDao")
    private ICategoryDao categoryDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,Category> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,Category> slaveRedisTemplate;

    @Override
    public IBaseDao<Category> getBaseDao() {
        return categoryDao;
    }


    @Override
    public List<Category> getCategorysByPid(Long pid) {
        String key = RedisKeyConstants.CACHE_CATEGORY_LIST_PID_KEY + pid;
        List<Category> categories = null;
        if(slaveRedisTemplate.hasKey(key)){
            categories = slaveRedisTemplate.opsForList().range(key,0,-1);
        }
        if(CollectionUtils.isEmpty(categories)){
            categories = findListByParams("pid",pid);
            if(CollectionUtils.isNotEmpty(categories)){
                for(int i = 0; i < categories.size(); i++){
                    Category category = categories.get(i);
                    masterRedisTemplate.opsForList().rightPush(key,category);
                }
                masterRedisTemplate.expire(key,1, TimeUnit.DAYS);
            }
        }
        return categories;
    }
}
