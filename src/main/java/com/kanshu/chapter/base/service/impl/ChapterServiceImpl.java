package com.kanshu.chapter.base.service.impl;

import com.kanshu.kanshu.base.contants.RedisKeyConstants;
import com.kanshu.chapter.base.dao.IBaseDao;
import com.kanshu.chapter.base.dao.IChapterDao;
import com.kanshu.kanshu.product.model.Chapter;
import com.kanshu.chapter.base.service.IChapterService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public List<Chapter> getChaptersByBookId(Long bookId,Integer num) {
        String key = RedisKeyConstants.CACHE_BOOK_CATALOG_KEY+bookId;
        List<Chapter> chapters = null;
        if(slaveRedisTemplate.hasKey(key)){
            chapters = slaveRedisTemplate.opsForList().range(key,0,-1);
        }
        if(CollectionUtils.isEmpty(chapters)){
            chapters = findListByParams("bookId",bookId,"num",num);
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
    public Chapter getChapterById(Long chapterId,Integer type,Integer num) {
        String key = String.format(RedisKeyConstants.CACHE_CHAPTER_TYPE_KEY,chapterId,type);
        Chapter chapter = this.slaveRedisTemplate.opsForValue().get(key);
        if(chapter == null){
            chapter = this.findUniqueByParams("chapterId",chapterId,"type",type,"num",num);
            if(chapter != null){
                this.masterRedisTemplate.opsForValue().set(key,chapter,1, TimeUnit.HOURS);
            }
        }
        return chapter;
    }
}
