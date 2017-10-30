package com.yxsd.kanshu.ucenter.service.impl;

import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserReceiveDao;
import com.yxsd.kanshu.ucenter.model.UserReceive;
import com.yxsd.kanshu.ucenter.service.IUserReceiveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userReceiveService")
public class UserReceiveServiceImpl extends BaseServiceImpl<UserReceive, Long> implements IUserReceiveService {

    @Resource(name="userReceiveDao")
    private IUserReceiveDao userReceiveDao;


    @Override
    public IBaseDao<UserReceive> getBaseDao() {
        return userReceiveDao;
    }

    @Override
    public UserReceive userThirdBind(Long userId, Integer type) {
        UserReceive userReceive = findUniqueByParams("userId",userId);
        if(userReceive != null){
            if(type == 1){
                userReceive.setTelStatus(1);
            }else if(type == 2){
                userReceive.setQqStatus(1);
            }else if(type == 3){
                userReceive.setWeixinStatus(1);
            }else if(type == 4){
                userReceive.setWeiboStatus(1);
            }
            userReceive.setUpdateDate(new Date());
            this.update(userReceive);
        }else{
            userReceive = new UserReceive();
            userReceive.setUserId(userId);
            if(type == 1){
                userReceive.setTelStatus(1);
            }else if(type == 2){
                userReceive.setQqStatus(1);
            }else if(type == 3){
                userReceive.setWeixinStatus(1);
            }else if(type == 4){
                userReceive.setWeiboStatus(1);
            }
            userReceive.setUpdateDate(new Date());
            userReceive.setCreateDate(new Date());
            this.save(userReceive);
        }
        return userReceive;
    }
}
