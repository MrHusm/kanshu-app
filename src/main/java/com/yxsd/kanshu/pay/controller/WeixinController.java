package com.yxsd.kanshu.pay.controller;

import com.alibaba.fastjson.JSON;
import com.yxsd.kanshu.base.contants.Constants;
import com.yxsd.kanshu.base.contants.ErrorCodeEnum;
import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.*;
import com.yxsd.kanshu.pay.config.WxPayConfig;
import com.yxsd.kanshu.pay.model.RechargeItem;
import com.yxsd.kanshu.pay.model.WeixinOrder;
import com.yxsd.kanshu.pay.model.WeixinResponse;
import com.yxsd.kanshu.pay.service.IRechargeItemService;
import com.yxsd.kanshu.pay.service.IWeixinOrderService;
import com.yxsd.kanshu.pay.service.IWeixinResponseService;
import com.yxsd.kanshu.product.service.IVipService;
import com.yxsd.kanshu.ucenter.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("weixin")
public class WeixinController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WeixinController.class);

	@Resource(name="vipService")
	IVipService vipService;

	@Resource(name="userService")
	IUserService userService;

	@Resource(name="userAccountService")
	IUserAccountService userAccountService;

	@Resource(name="userAccountLogService")
	IUserAccountLogService userAccountLogService;

	@Resource(name="userVipService")
	IUserVipService userVipService;

	@Resource(name="weixinOrderService")
	IWeixinOrderService weixinOrderService;

	@Resource(name="weixinResponseService")
	IWeixinResponseService weixinResponseService;

	@Resource(name="rechargeItemService")
	IRechargeItemService rechargeItemService;


	@Resource(name="userReceiveService")
	IUserReceiveService userReceiveService;

	/**
	 * 创建微信订单
	 * @param response
	 * @param request
	 */
	@RequestMapping("order")
	public void order(HttpServletResponse response, HttpServletRequest request){
		ResultSender sender = JsonResultSender.getInstance();
		//入参
		String token = request.getParameter("token");
		String channel = request.getParameter("channel");
		//充值的档位ID
		String productId = request.getParameter("productId");
		//备注信息
		String content = request.getParameter("param");

		if(StringUtils.isBlank(token) || StringUtils.isBlank(productId)){
			logger.error("WeixinController_order：token或productId为空");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			WeixinOrder order = new WeixinOrder();
			order.setChannel(StringUtils.isBlank(channel) ? null : Integer.parseInt(channel));
			order.setUserId(Long.parseLong(UserUtils.getUserIdByToken(token)));
			order.setProductId(Long.parseLong(productId));
			order.setType(1);
			order.setComment(content);

			RechargeItem rechargeItem = rechargeItemService.get(Long.parseLong(productId));
			if(rechargeItem.getVirtual() != null && rechargeItem.getVirtual() > 0){
				order.setBody("充值"+rechargeItem.getMoney()+"钻赠送"+rechargeItem.getVirtual()+"钻");
			}else{
				order.setBody("充值"+rechargeItem.getMoney()+"钻");
			}
			order.setTotal_fee(rechargeItem.getPrice().intValue() * 100);

			order.setOut_trade_no(Long.toHexString(System.currentTimeMillis()));
			order.setNonce_str(CommonUtil.createNoncestr());
			order.setSpbill_create_ip(HttpUtils.getIp(request));
			order.setTrade_type("APP");
			order.setCreateDate(new Date());
			order.setUpdateDate(new Date());

			//封装下单参
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("appid", WxPayConfig.APPID);
			param.put("mch_id", WxPayConfig.MCH_ID);
			param.put("nonce_str", order.getNonce_str());
			param.put("body", order.getBody());
			param.put("out_trade_no", order.getOut_trade_no());
			param.put("total_fee", order.getTotal_fee());
			param.put("spbill_create_ip", order.getSpbill_create_ip());
			param.put("notify_url", WxPayConfig.NOTIFY_URL);
			param.put("trade_type", order.getTrade_type());

			String sign = getSign(param);
			param.put("sign", sign);

			order.setSign(sign);
			weixinOrderService.save(order);

		}catch (Exception e){
			logger.error("wxWapPay Exception reason is "+ e);
			e.printStackTrace();
		}
	}

	/**
	 * 支付后异步通知接口，完成充值
	 * @param response
	 * @param request
	 */
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class,value="transactionManager")
	@RequestMapping("notifyUrl")
	public void notifyUrl(HttpServletResponse response, HttpServletRequest request){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			//获取微信POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			logger.info("wixin_notifyUrl_params:"+JSON.toJSONString(params));

			if(params.get("return_code") == "SUCCESS") {
				String sign = params.get("sign");
				//TODO 签名验证
				//保存或修改response表
				WeixinResponse weixinResponse = weixinResponseService.findUniqueByParams("out_trade_no", params.get("out_trade_no"));
				if(weixinResponse == null){
					weixinResponse = new WeixinResponse();
				}
				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if("SUCCESS".equals(params.get("result_code"))){
					if(StringUtils.isBlank(weixinResponse.getResultCode()) || "FAIL".equals(weixinResponse.getResultCode())){
						//支付成功充值
						WeixinOrder order = this.weixinOrderService.findUniqueByParams("out_trade_no",params.get("out_trade_no"));
						if(order.getType() == Constants.CONSUME_TYPE_S4){
							//购买VIP
						}else if(order.getType() == Constants.CONSUME_TYPE_1){
							//充值
							this.userService.charge(order.getUserId(), 1, order.getChannel(), params.get("out_trade_no"), order.getProductId());
						}
					}
				}
				weixinResponse.setBankType(params.get("bank_type"));
				weixinResponse.setCashFee(Integer.parseInt(params.get("cash_fee")));
				weixinResponse.setOpenid(params.get("openid"));
				weixinResponse.setOutTradeNo(params.get("out_trade_no"));
				weixinResponse.setTimeEnd(params.get("time_end"));
				weixinResponse.setTotalFee(Integer.parseInt(params.get("total_fee")));
				weixinResponse.setTradeType(params.get("trade_type"));
				weixinResponse.setTransactionId(params.get("transaction_id"));
				weixinResponse.setResultCode(params.get("result_code"));
				weixinResponse.setErrCode(params.get("err_code"));
				weixinResponse.setErrCodeDes(params.get("err_code_des"));
				weixinResponse.setUpdateDate(new Date());
				if(weixinResponse.getWxResponseId() != null){
					weixinResponseService.update(weixinResponse);
				}else{
					weixinResponse.setCreateDate(new Date());
					weixinResponseService.save(weixinResponse);
				}
				result.put("return_code","SUCCESS");
				result.put("return_msg","OK");
			}else {
				result.put("return_code","FAIL");
				result.put("return_msg","失败");
				logger.error("wxWapPay notify error reason is " + params.get("return_msg"));
			}
		} catch (Exception e) {
			result.put("return_code","FAIL");
			result.put("return_msg","通知接口异常");
			e.printStackTrace();
		}
		out(CommonUtil.formatParamMap(result),response);
	}

	/**
	 * 支付后同步通知接口，完成页面跳转
	 * @param response
	 * @param request
	 */
	@RequestMapping("returnUrl")
	public String returnUrl(HttpServletResponse response, HttpServletRequest request,Model model){
		try {
			//获取微信GET过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			logger.info("weixin_returnUrl_params:"+JSON.toJSONString(params));
			//获取微信的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			model.addAttribute("orderNo",out_trade_no);

			//请在这里加上商户的业务逻辑程序代码
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			logger.info("weixin_returnUrl_:验证成功");
			WeixinResponse weixinResponse = weixinResponseService.findUniqueByParams("out_trade_no",out_trade_no);
			if("SUCCESS".equals(weixinResponse.getResultCode())){
				//充值成功页面跳转
				WeixinOrder weixinOrder = weixinOrderService.findUniqueByParams("out_trade_no",out_trade_no);
				String returnUrl = null;
//				PayBefore payBefore = this.payBeforeService.getUserPayBefore(weixinOrder.getUserId());
//				if(StringUtils.isNotBlank(payBefore.getReturnUrl())){
//					returnUrl = payBefore.getReturnUrl();
//				}else{
//					returnUrl = HttpUtils.getBasePath(request) + "/user/userCenter.go";
//				}
//				//用户是否已绑定第三方账号
//				UserReceive userReceive = this.userReceiveService.findUniqueByParams("userId",weixinOrder.getUserId());
//				if(userReceive != null && ((userReceive.getQqStatus() != null && userReceive.getQqStatus() == 1)
//						|| (userReceive.getWeixinStatus() != null && userReceive.getWeixinStatus() == 1)
//						|| (userReceive.getWeiboStatus() != null && userReceive.getWeiboStatus() == 1)
//						|| (userReceive.getTelStatus() != null && userReceive.getTelStatus() == 1))){
//					response.sendRedirect(returnUrl);
//				}else{
//					response.sendRedirect(HttpUtils.getBasePath(request) + "/user/toLogin.go?type=1&returnUrl="+ URLEncoder.encode(returnUrl,"UTF-8"));
//				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pay/pay_success";
	}

	private void out(String content,HttpServletResponse response){
		try {
			ServletOutputStream out=response.getOutputStream();
			out.write(content.getBytes());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String getSign(HashMap<String, Object> param){
		String sign="";
		String content = CommonUtil.formatParamMap(param);
		sign =  sign(content, WxPayConfig.KEY);
		return sign;
	}

	public static String sign(String content, String key){
		String signStr = "";
		signStr = content + "&key=" + key;
		return MD5Utils.getInstance().cell32(signStr).toUpperCase();
	}
}
