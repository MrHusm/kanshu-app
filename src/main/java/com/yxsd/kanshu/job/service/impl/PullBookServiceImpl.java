package com.yxsd.kanshu.job.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.job.dao.IPullBookDao;
import com.yxsd.kanshu.job.model.PullBook;
import com.yxsd.kanshu.job.service.IPullBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/8/6.
 */
@Service(value="pullBookService")
public class PullBookServiceImpl extends BaseServiceImpl<PullBook, Long> implements IPullBookService {

    @Resource(name="pullBookDao")
    private IPullBookDao pullBookDao;

    @Override
    public IBaseDao<PullBook> getBaseDao() {
        return pullBookDao;
    }

    @Override
    public int findCount(PullBook pullBook){
        Map<String, Object> paramsMap = this.convertBeanToMap(pullBook);
        return pullBookDao.selectCount(paramsMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveOrUpdatePullBook(String copyrightCode, String copyrightBookId, Integer pullStatus, String pullFailureCause) {
        //查询该图书是否推送过
        PullBook pullBook = this.findMasterUniqueByParams("copyrightCode", copyrightCode, "copyrightBookId", copyrightBookId);
        //增加或者更新拉取图书
        if(pullBook == null){
            pullBook = new PullBook(copyrightCode, copyrightBookId, pullStatus, pullFailureCause);
            this.save(pullBook);
        }else{
            pullBook.setPullStatus(pullStatus);
            pullBook.setPullFailureCause(pullFailureCause);
            pullBook.setUpdateDate(new Date());
            this.update(pullBook);
        }
    }

    /**
     *
     * @Title: findByCopyrightBookIds
     * @Description: 通过供应商图书ID
     * @param cbidList
     * @return
     * @author hushengmeng
     */
    @Override
    public List<PullBook> findByCopyrightBookIds(List<String> cbidList) {
        return this.pullBookDao.selectByCopyrightBookId(cbidList);
    }
}
