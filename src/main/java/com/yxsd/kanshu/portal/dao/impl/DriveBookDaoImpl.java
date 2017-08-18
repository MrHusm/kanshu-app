package com.yxsd.kanshu.portal.dao.impl;

import com.yxsd.kanshu.base.dao.impl.BaseDaoImpl;
import com.yxsd.kanshu.portal.dao.IDriveBookDao;
import com.yxsd.kanshu.portal.model.DriveBook;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="driveBookDao")
public class DriveBookDaoImpl extends BaseDaoImpl<DriveBook> implements IDriveBookDao {
}
