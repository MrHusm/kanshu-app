package com.yxsd.kanshu.product.service.impl;

import com.yxsd.kanshu.base.dao.IChapterBaseDao;
import com.yxsd.kanshu.product.dao.IChapterDao;
import com.yxsd.kanshu.product.service.IChapterService;
import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.service.impl.ChapterBaseServiceImpl;
import com.yxsd.kanshu.base.utils.BeanUtils;
import com.yxsd.kanshu.product.model.Chapter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2017/8/7.
 */
@Service(value="chapterService")
public class ChapterServiceImpl extends ChapterBaseServiceImpl<Chapter, Long> implements IChapterService {

    @Resource(name="chapterDao")
    private IChapterDao chapterDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,Chapter> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,Chapter> slaveRedisTemplate;

    @Override
    public IChapterBaseDao<Chapter> getBaseDao() {
        return chapterDao;
    }

    @Override
    public List<Chapter> getChaptersByBookId(Long bookId,Integer num) {
        String key = RedisKeyConstants.CACHE_BOOK_CATALOG_KEY+bookId;
        List<Chapter> chapters = null;
        if(masterRedisTemplate.hasKey(key)){
            chapters = slaveRedisTemplate.opsForList().range(key,0,-1);
        }
        if(CollectionUtils.isEmpty(chapters)){
            chapters = findListByParams("bookId",bookId,"shelfStatus",1,"num",num);
            if(CollectionUtils.isNotEmpty(chapters)){
                for(int i = 0; i < chapters.size(); i++){
                    Chapter chapter = chapters.get(i);
                    masterRedisTemplate.opsForList().rightPush(key,chapter);
                }
                masterRedisTemplate.expire(key,6L,TimeUnit.HOURS);
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

    @Override
    public int saveChapter(Chapter chapter, Integer num) {
        //TODO
        //压缩章节内容
        Map<String,Object> map = BeanUtils.convertBeanToMap(chapter);
        map.put("num",num);
        return this.getBaseDao().insert("ChapterMapper.insertByMap",map);
    }

    @Override
    public int updateChapter(Chapter chapter, Integer num) {
        //TODO
        //压缩章节内容
        Map<String,Object> map = BeanUtils.convertBeanToMap(chapter);
        map.put("num",num);
        return this.getBaseDao().update("ChapterMapper.updateByMap",map);
    }

    @Override
    public Integer updatedChapterCount(Long bookId, Integer num, Integer index) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("bookId",bookId);
        params.put("num",num);
        params.put("index",index);
        return this.chapterDao.updatedChapterCount(params);


    }

    @Override
    public void clearChapterAllCache(Long chapterId) {
        logger.info("开始清除章节"+chapterId+"相关缓存");
        try{
            //章节+内容key
            Set<String> chapterTypeKeys = masterRedisTemplate.keys("chapter_content_cid_"+String.valueOf(chapterId)+"*");
            if(CollectionUtils.isNotEmpty(chapterTypeKeys)){
                for(String key : chapterTypeKeys){
                    masterRedisTemplate.delete(key);
                }
            }
        }catch (Exception e){
            logger.info("清除章节"+chapterId+"相关缓存异常");
            e.printStackTrace();
        }
        logger.info("结束清除章节"+chapterId+"相关缓存");
    }
}
