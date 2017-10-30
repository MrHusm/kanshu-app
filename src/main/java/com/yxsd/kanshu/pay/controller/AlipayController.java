package com.yxsd.kanshu.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.yxsd.kanshu.base.contants.Constants;
import com.yxsd.kanshu.base.contants.ErrorCodeEnum;
import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.JsonResultSender;
import com.yxsd.kanshu.base.utils.ResultSender;
import com.yxsd.kanshu.pay.config.AlipayConfig;
import com.yxsd.kanshu.pay.model.AlipayOrder;
import com.yxsd.kanshu.pay.model.AlipayResponse;
import com.yxsd.kanshu.pay.model.RechargeItem;
import com.yxsd.kanshu.pay.service.IAlipayOrderService;
import com.yxsd.kanshu.pay.service.IAlipayResponseService;
import com.yxsd.kanshu.pay.service.IRechargeItemService;
import com.yxsd.kanshu.product.model.Vip;
import com.yxsd.kanshu.product.service.IVipService;
import com.yxsd.kanshu.ucenter.service.IUserAccountLogService;
import com.yxsd.kanshu.ucenter.service.IUserAccountService;
import com.yxsd.kanshu.ucenter.service.IUserService;
import com.yxsd.kanshu.ucenter.service.IUserVipService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("alipay")
public class AlipayController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AlipayController.class);

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

	@Resource(name="alipayOrderService")
	IAlipayOrderService alipayOrderService;

	@Resource(name="alipayResponseService")
	IAlipayResponseService alipayResponseService;

	@Resource(name="rechargeItemService")
	IRechargeItemService rechargeItemService;

	/**
	 * 支付宝充值首页
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletResponse response, HttpServletRequest request){
		return "pay/alipay_index";
	}

	/**
	 * 创建支付宝订单
	 * @param response
	 * @param request
	 */
	@RequestMapping("order")
	public void order(HttpServletResponse response, HttpServletRequest request){
		ResultSender sender = JsonResultSender.getInstance();
		//入参
		String userId = request.getParameter("userId");
		String channel = request.getParameter("channel");
		String type = request.getParameter("type");
		String productId = request.getParameter("productId");
		String param = request.getParameter("param");

		if(StringUtils.isBlank(userId)){
			logger.error("AlipayController_order：userId为空");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		if(StringUtils.isBlank(productId)){
			logger.error("AlipayController_order：productId为空");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		if(StringUtils.isBlank(type)){
			logger.error("AlipayController_order：type为空");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			AlipayOrder order = new AlipayOrder();
			order.setChannel(StringUtils.isBlank(channel) ? null : Integer.parseInt(channel));
			order.setUserId(Long.parseLong(userId));
			order.setProductId(Long.parseLong(productId));
			order.setType(Integer.parseInt(type));
			order.setComment(param);
			if(order.getType() == Constants.CONSUME_TYPE_S4){
				Vip vip = this.vipService.get(Long.parseLong(productId));
				order.setWIDsubject("VIP"+vip.getDays()+"天");
				order.setWIDbody("VIP"+vip.getDays()+"天");
				if(vip.getDiscountPrice() == null){
					order.setWIDtotalAmount(new Double(vip.getPrice()));
				}else{
					order.setWIDtotalAmount(new Double(vip.getDiscountPrice()));
				}
			}else if(order.getType() == Constants.CONSUME_TYPE_1){
				RechargeItem rechargeItem = rechargeItemService.get(Long.parseLong(productId));
				order.setWIDsubject("充值"+rechargeItem.getMoney()+"钻");
				order.setWIDbody("充值"+rechargeItem.getMoney()+"钻赠送"+rechargeItem.getVirtual()+"钻");
				order.setWIDtotalAmount(rechargeItem.getPrice());
			}else if(order.getType() == Constants.CONSUME_TYPE_S1 || order.getType() == Constants.CONSUME_TYPE_S2 || order.getType() == Constants.CONSUME_TYPE_S3){
				RechargeItem rechargeItem = rechargeItemService.get(Long.parseLong(productId));
				order.setWIDsubject("充值"+rechargeItem.getMoney()+"钻");
				order.setWIDbody("充值"+rechargeItem.getMoney()+"钻赠送"+rechargeItem.getVirtual()+"钻并购买图书");
				order.setWIDtotalAmount(rechargeItem.getPrice());
			}
			order.setWIDoutTradeNo(Long.toHexString(System.currentTimeMillis()));
			order.setCreateDate(new Date());
			order.setUpdateDate(new Date());
			alipayOrderService.save(order);

			// 商户订单号，商户网站订单系统中唯一订单号，必填
			String out_trade_no = order.getWIDoutTradeNo();
			// 订单名称，必填
			String subject = order.getWIDsubject();
			// 付款金额，必填
			String total_amount = String.valueOf(order.getWIDtotalAmount());
			// 商品描述，可空
			String body = order.getWIDbody();
			// 超时时间 可空
			String timeout_express = "2m";
			// 销售产品码 必填
			String product_code = "QUICK_WAP_PAY";
			/**********************/
			// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
			//调用RSA签名方式
			AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
			AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

			// 封装请求支付信息
			AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
			model.setOutTradeNo(out_trade_no);
			model.setSubject(subject);
			model.setTotalAmount(total_amount);
			model.setBody(body);
			model.setTimeoutExpress(timeout_express);
			model.setProductCode(product_code);
			alipay_request.setBizModel(model);
			// 设置异步通知地址
			alipay_request.setNotifyUrl(AlipayConfig.notify_url);
			// 设置同步地址
			alipay_request.setReturnUrl(AlipayConfig.return_url);

			// form表单生产
			String form = "";

			// 调用SDK生成表单
			form = client.pageExecute(alipay_request).getBody();
			response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
			response.getWriter().write(form);//直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		}catch (Exception e){
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
		try {
			//获取支付宝POST过来反馈信息
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
			logger.info("notifyUrl_params:"+JSON.toJSONString(params));
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			//订单金额（元）
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

			//实收金额（元）
			String receipt_amount = new String(request.getParameter("receipt_amount").getBytes("ISO-8859-1"),"UTF-8");

			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			//计算得出通知验证结果
			//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
			boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

			if(verify_result){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				logger.info("notifyUrl_:验证成功");

				//保存或修改response表
				AlipayResponse alipayResponse = alipayResponseService.findUniqueByParams("outTradeNo",out_trade_no);
				if(alipayResponse == null){
					alipayResponse = new AlipayResponse();
				}
				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					if(alipayResponse.getStatus() == null || alipayResponse.getStatus() != 1){
						//支付成功充值
						AlipayOrder order = this.alipayOrderService.findUniqueByParams("WIDoutTradeNo",out_trade_no);
						if(order.getType() == Constants.CONSUME_TYPE_S4){
							//购买VIP
						}else if(order.getType() == Constants.CONSUME_TYPE_1){
							//充值
							this.userService.charge(order.getUserId(), 1, order.getChannel(), out_trade_no, order.getProductId());
						}
						alipayResponse.setStatus(1);
					}
				}else{
					alipayResponse.setStatus(0);
				}

				alipayResponse.setOutTradeNo(out_trade_no);
				alipayResponse.setReceiptAmount(Double.parseDouble(receipt_amount));
				alipayResponse.setTotalAmount(Double.parseDouble(total_amount));
				alipayResponse.setTradeNo(trade_no);
				alipayResponse.setTradeStatus(trade_status);
				alipayResponse.setUpdateDate(new Date());
				if(alipayResponse.getAlipayResponseId() != null){
					alipayResponseService.update(alipayResponse);
				}else{
					alipayResponse.setCreateDate(new Date());
					alipayResponseService.save(alipayResponse);
				}

				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				out("success",response);	//请不要修改或删除

				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				out("fail",response);
			}
		} catch (Exception e) {
			out("fail",response);
			e.printStackTrace();
		}
	}

	/**
	 * 支付后同步通知接口，完成页面跳转
	 * @param response
	 * @param request
	 */
	@RequestMapping("returnUrl")
	public String returnUrl(HttpServletResponse response, HttpServletRequest request){
		try {
			//获取支付宝GET过来反馈信息
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
			logger.info("returnUrl_params:"+JSON.toJSONString(params));
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			//计算得出通知验证结果
			//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
			boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

			if(verify_result){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				logger.info("returnUrl_:验证成功");
				AlipayOrder order = this.alipayOrderService.findUniqueByParams("WIDoutTradeNo",out_trade_no);
				if(order.getType() == Constants.CONSUME_TYPE_S4){
					response.sendRedirect("/vip/index.go?userId="+order.getUserId());
				}else if(order.getType() == Constants.CONSUME_TYPE_1){
					response.sendRedirect("/pay/index.go?userId="+order.getUserId());
				}else if(order.getType() == Constants.CONSUME_TYPE_S1 || order.getType() == Constants.CONSUME_TYPE_S2 || order.getType() == Constants.CONSUME_TYPE_S3){
					response.sendRedirect("/book/buyResponse.go?userId="+order.getUserId()+"&payType=1&type="+order.getType()+"&param="+order.getComment()+"&WIDout_trade_no"+order.getWIDoutTradeNo());
				}
				return null;

				//////////////////////////////////////////////////////////////////////////////////////////
			}else{
				return "/pay/pay_result";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pay/pay_result";
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
}
