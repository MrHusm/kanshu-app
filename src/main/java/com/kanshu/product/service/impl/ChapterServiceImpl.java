package com.kanshu.product.service.impl;

import com.kanshu.base.contants.RedisKeyConstants;
import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.product.dao.IChapterDao;
import com.kanshu.product.model.Chapter;
import com.kanshu.product.service.IChapterService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lenovo on 2017/8/7.
 */
@Service(value="chapterService")
public class ChapterServiceImpl extends BaseServiceImpl<Chapter, Long> implements IChapterService {

    @Resource(name="chapterDao")
    private IChapterDao chapterDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,Chapter> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,Chapter> slaveRedisTemplate;

    @Override
    public IBaseDao<Chapter> getBaseDao() {
        return chapterDao;
    }

    @Override
    public List<Chapter> getChaptersByBookId(Long bookId) {
        String key = RedisKeyConstants.CACHE_BOOK_CATALOG_KEY+bookId;
        List<Chapter> chapters = slaveRedisTemplate.opsForList().range(key,0,-1);
        if(CollectionUtils.isEmpty(chapters)){
            chapters = findListByParams("bookId",bookId);
            if(CollectionUtils.isNotEmpty(chapters)){
                for(int i = 0; i < chapters.size(); i++){
                    Chapter chapter = chapters.get(i);
                    masterRedisTemplate.opsForList().set(key,i,chapter);
                }
            }else{
                return null;
            }
        }
        return chapters;
    }

    @Override
    public Chapter getChapterWithContentById(Long chapterId) {
        String key = RedisKeyConstants.CACHE_CHAPTER_CONTENT_KEY + chapterId;
        Chapter chapter = this.slaveRedisTemplate.opsForValue().get(key);
        if(chapter == null){

        }
        return null;
    }
}
