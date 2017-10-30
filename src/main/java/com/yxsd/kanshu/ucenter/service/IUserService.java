package com.yxsd.kanshu.ucenter.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.ucenter.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IUserService extends IBaseService<User,Long> {

    /**
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    public User getUserByUserId(Long userId);

    /**
     * 注册用户
     * @param channel 渠道号
     * @param deviceType 0:安卓 1：ios 2:h5
     * @param deviceSerialNo imei->android_id->serialNunber ->UUID生成的
     * @return
     */
    public User register(String channel,String deviceType,String deviceSerialNo,HttpServletRequest request);

    /**
     * 消费
     * @param userId
     * @param price
     * @param type  -1:单章购买 -2：批量购买 -3：全本购买 -4：VIP消费
     * @param map
     * @return 0:成功 -1：余额不够
     */
    public int consume(Long userId, Integer price, Integer type, Map<String, Object> map);

    /**
     * 充值
     * @param userId
     * @param type 1：支付宝充值 2：微信充值
     * @return
     */
    public void charge(Long userId, Integer type, Integer channel, String orderNo,Long rechargeItemId);

}
