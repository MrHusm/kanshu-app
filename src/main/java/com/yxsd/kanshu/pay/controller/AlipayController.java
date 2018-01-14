package com.yxsd.kanshu.pay.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.yxsd.kanshu.base.contants.ErrorCodeEnum;
import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.JsonResultSender;
import com.yxsd.kanshu.base.utils.ResultSender;
import com.yxsd.kanshu.base.utils.UserUtils;
import com.yxsd.kanshu.pay.config.AlipayConfig;
import com.yxsd.kanshu.pay.model.AlipayOrder;
import com.yxsd.kanshu.pay.model.AlipayResponse;
import com.yxsd.kanshu.pay.model.RechargeItem;
import com.yxsd.kanshu.pay.service.IAlipayOrderService;
import com.yxsd.kanshu.pay.service.IAlipayResponseService;
import com.yxsd.kanshu.pay.service.IRechargeItemService;
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
		String token = request.getParameter("token");
		String channel = request.getParameter("channel");
		//充值的档位ID
		String productId = request.getParameter("productId");
		//备注信息
		String param = request.getParameter("param");

		if(StringUtils.isBlank(token) || StringUtils.isBlank(productId)){
			logger.error("AlipayController_order：token或productId为空");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			AlipayOrder order = new AlipayOrder();
			order.setChannel(StringUtils.isBlank(channel) ? null : Integer.parseInt(channel));
			order.setUserId(Long.parseLong(UserUtils.getUserIdByToken(token)));
			order.setProductId(Long.parseLong(productId));
			order.setType(1);
			order.setComment(param);
			RechargeItem rechargeItem = rechargeItemService.get(Long.parseLong(productId));
//			if(rechargeItem.getVirtual() != null && rechargeItem.getVirtual() > 0){
//				order.setWIDsubject("充值"+rechargeItem.getMoney()+"钻赠送"+rechargeItem.getVirtual()+"钻");
//			}else{
//				order.setWIDsubject("充值"+rechargeItem.getMoney()+"钻");
//			}
			order.setWIDsubject("春意小说支付宝充值");
			order.setWIDbody(order.getWIDsubject());
			order.setWIDtotalAmount(rechargeItem.getPrice());
			order.setWIDoutTradeNo(Long.toHexString(System.currentTimeMillis()));
			order.setCreateDate(new Date());
			order.setUpdateDate(new Date());
			alipayOrderService.save(order);

			//实例化客户端
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
			//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			AlipayTradeAppPayRequest payRequest = new AlipayTradeAppPayRequest();
			//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setBody(order.getWIDbody());
			model.setSubject(order.getWIDsubject());
			model.setOutTradeNo(order.getWIDoutTradeNo());
			model.setTimeoutExpress("30m");
			model.setTotalAmount(String.valueOf(order.getWIDtotalAmount()));
//			if(order.getUserId().intValue() == 5166){
//				model.setTotalAmount("0.01");
//			}
			model.setProductCode("QUICK_MSECURITY_PAY");
			payRequest.setBizModel(model);
			payRequest.setNotifyUrl(AlipayConfig.notify_url);
			//这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse payResponse = alipayClient.sdkExecute(payRequest);
			sender.put("orderString",payResponse.getBody());
			sender.success(response);
		}catch (Exception e){
			logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
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
				//乱码解决，这段代码在出现乱码时使用。
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
			//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
			boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,AlipayConfig.SIGNTYPE	);

			if(flag){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				logger.info("notifyUrl_:验证成功");
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
						//充值
						this.userService.charge(order.getUserId(), 1, order.getChannel(), out_trade_no, order.getProductId());
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
