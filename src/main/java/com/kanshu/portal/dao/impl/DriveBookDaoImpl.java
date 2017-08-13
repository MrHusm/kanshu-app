package com.kanshu.portal.dao.impl;

import com.kanshu.base.dao.impl.BaseDaoImpl;
import com.kanshu.portal.dao.IDriveBookDao;
import com.kanshu.portal.model.DriveBook;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="driveBookDao")
public class DriveBookDaoImpl extends BaseDaoImpl<DriveBook> implements IDriveBookDao {
}
