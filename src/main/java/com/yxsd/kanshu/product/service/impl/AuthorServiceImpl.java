package com.yxsd.kanshu.product.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.product.dao.IAuthorDao;
import com.yxsd.kanshu.product.model.Author;
import com.yxsd.kanshu.product.service.IAuthorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/7.
 */
@Service(value="authorService")
public class AuthorServiceImpl extends BaseServiceImpl<Author, Long> implements IAuthorService {

    @Resource(name="authorDao")
    private IAuthorDao authorDao;

    @Override
    public IBaseDao<Author> getBaseDao() {
        return authorDao;
    }

}
