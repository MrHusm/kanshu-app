package com.yxsd.kanshu.pay.controller;

import com.yxsd.kanshu.base.contants.Constants;
import com.yxsd.kanshu.base.contants.ErrorCodeEnum;
import com.yxsd.kanshu.base.contants.WXPayConstants;
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
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
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
import java.io.BufferedReader;
import java.util.Date;
import java.util.HashMap;
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
			order.setType(2);
			order.setComment(content);

			RechargeItem rechargeItem = rechargeItemService.get(Long.parseLong(productId));
			if(rechargeItem.getVirtual() != null && rechargeItem.getVirtual() > 0){
				order.setBody("充值"+rechargeItem.getMoney()+"钻赠送"+rechargeItem.getVirtual()+"钻");
			}else{
				order.setBody("充值"+rechargeItem.getMoney()+"钻");
			}
			order.setTotal_fee(rechargeItem.getPrice().intValue() * 100);
			order.setOut_trade_no(Long.toHexString(System.currentTimeMillis()));
			order.setNonce_str(WXPayUtil.generateNonceStr());
			order.setSpbill_create_ip(HttpUtils.getIp(request));
			order.setTrade_type("APP");
			order.setCreateDate(new Date());
			order.setUpdateDate(new Date());

			//封装下单参
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("appid", WxPayConfig.APPID);
			param.put("mch_id", WxPayConfig.MCH_ID);
			param.put("nonce_str", order.getNonce_str());
			param.put("body", order.getBody());
			param.put("out_trade_no", order.getOut_trade_no());
			param.put("total_fee", String.valueOf(order.getTotal_fee()));
			//param.put("total_fee", "1");
			param.put("spbill_create_ip", order.getSpbill_create_ip());
			param.put("notify_url", WxPayConfig.NOTIFY_URL);
			param.put("trade_type", order.getTrade_type());

			String sign = WXPayUtil.generateSignature(param, WxPayConfig.KEY, WXPayConstants.SignType.MD5);
			param.put("sign", sign);
			order.setSign(sign);
			//保存微信订单
			weixinOrderService.save(order);
			String reqBody = WXPayUtil.mapToXml(param);

			BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
					RegistryBuilder.<ConnectionSocketFactory>create()
							.register("http", PlainConnectionSocketFactory.getSocketFactory())
							.register("https", SSLConnectionSocketFactory.getSocketFactory())
							.build(),null,null,null);
			HttpClient httpClient = HttpClientBuilder.create()
					.setConnectionManager(connManager)
					.build();

			String url = "https://" + WXPayConstants.DOMAIN_API + WXPayConstants.UNIFIEDORDER_URL_SUFFIX;
			HttpPost httpPost = new HttpPost(url);

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(5000).build();
			httpPost.setConfig(requestConfig);

			StringEntity postEntity = new StringEntity(reqBody, "UTF-8");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + WxPayConfig.MCH_ID);  //很重要，用来检测 sdk 的使用情况，要不要加上商户信息？
			httpPost.setEntity(postEntity);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			String resultXml = EntityUtils.toString(httpEntity, "UTF-8");
			//微信下单数据返回
			logger.info("weixin_unifiedorder_result:" + resultXml);
			Map<String,String> result =  WXPayUtil.xmlToMap(resultXml);
			if("SUCCESS".equals(result.get("return_code")) && "SUCCESS".equals(result.get("result_code"))){
				Map<String,String> map = new HashMap<String, String>();
				map.put("appid",result.get("appid"));
				map.put("partnerid",result.get("mch_id"));
				map.put("prepayid",result.get("prepay_id"));
				map.put("package","Sign=WXPay");
				map.put("noncestr",WXPayUtil.generateNonceStr());
				map.put("timestamp", String.valueOf(new Date().getTime() / 1000));
				map.put("sign",WXPayUtil.generateSignature(map, WxPayConfig.KEY, WXPayConstants.SignType.MD5));
				sender.put("params",map);
				sender.success(response);
			}else{
				sender.fail(ErrorCodeEnum.ERROR_CODE_99999.getErrorCode(),
						result.get("return_msg") + "|" +result.get("err_code_des"), response);
			}
		} catch (Exception e) {
			logger.error("wxWapPay Exception reason is " + e);
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
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
		Map<String,String> result = new HashMap<String, String>();
		try {
			StringBuilder paramXml = new StringBuilder();
			BufferedReader br =  request.getReader();
			String s = null;
			while((s = br.readLine())!=null){
				paramXml.append(s);
			}
			br.close();
			logger.info("weixin_notifyUrl_paramXml:" + paramXml);

			Map<String,String> params = WXPayUtil.xmlToMap(paramXml.toString());
			if("SUCCESS".equals(params.get("return_code"))){
				//验证签名
				boolean verify_result = WXPayUtil.isSignatureValid(params, WxPayConfig.KEY, WXPayConstants.SignType.MD5);
				if(verify_result){
					logger.info("weixin_notifyUrl：验签成功");
					//保存或修改response表
					WeixinResponse weixinResponse = weixinResponseService.findUniqueByParams("outTradeNo", params.get("out_trade_no"));
					if(weixinResponse == null){
						weixinResponse = new WeixinResponse();
					}
					//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
					if("SUCCESS".equals(params.get("result_code"))){
						if(weixinResponse.getStatus() == null || weixinResponse.getStatus() != 1){
							//支付成功充值
							WeixinOrder order = this.weixinOrderService.findUniqueByParams("out_trade_no",params.get("out_trade_no"));
							if(order.getType() == Constants.CONSUME_TYPE_S4){
								//购买VIP
							}else if(order.getType() == Constants.CONSUME_TYPE_2){
								//充值
								this.userService.charge(order.getUserId(), 2, order.getChannel(), params.get("out_trade_no"), order.getProductId());
							}
							weixinResponse.setStatus(1);
						}
					}else{
						weixinResponse.setStatus(0);
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
				}else{
					result.put("return_code","FAIL");
					result.put("return_msg","签名失败");
					logger.error("wxWapPay notify error reason is " + params.get("return_msg"));
				}
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
		try {
			out(WXPayUtil.mapToXml(result),response);
		} catch (Exception e) {
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
