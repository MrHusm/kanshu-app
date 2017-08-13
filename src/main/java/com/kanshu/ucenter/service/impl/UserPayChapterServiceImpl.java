package com.kanshu.ucenter.service.impl;

import com.kanshu.base.dao.IBaseDao;
import com.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.ucenter.dao.IUserPayChapterDao;
import com.kanshu.ucenter.model.UserPayChapter;
import com.kanshu.ucenter.service.IUserPayChapterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userPayChapterService")
public class UserPayChapterServiceImpl extends BaseServiceImpl<UserPayChapter, Long> implements IUserPayChapterService {

    @Resource(name="userPayChapterDao")
    private IUserPayChapterDao userPayChapterDao;

    @Override
    public IBaseDao<UserPayChapter> getBaseDao() {
        return userPayChapterDao;
    }

}
