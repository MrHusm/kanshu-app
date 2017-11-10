package com.yxsd.kanshu.pay.controller;

import com.yxsd.kanshu.base.contants.ErrorCodeEnum;
import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.JsonResultSender;
import com.yxsd.kanshu.base.utils.ResultSender;
import com.yxsd.kanshu.base.utils.UserUtils;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
			if(rechargeItem.getVirtual() != null && rechargeItem.getVirtual() > 0){
				order.setWIDsubject("充值"+rechargeItem.getMoney()+"钻赠送"+rechargeItem.getVirtual()+"钻");
			}else{
				order.setWIDsubject("充值"+rechargeItem.getMoney()+"钻");
			}
			order.setWIDbody(order.getWIDsubject());
			order.setWIDtotalAmount(rechargeItem.getPrice());
			order.setWIDoutTradeNo(Long.toHexString(System.currentTimeMillis()));
			order.setCreateDate(new Date());
			order.setUpdateDate(new Date());
			alipayOrderService.save(order);
			sender.put("orderNo",order.getWIDoutTradeNo());
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
		ResultSender sender = JsonResultSender.getInstance();
		//入参
		//商户订单号
		String out_trade_no = request.getParameter("out_trade_no");
		//支付宝交易号
		String trade_no = request.getParameter("trade_no");
		//交易状态
		String trade_status = request.getParameter("trade_status");
		//订单金额（元）
		String total_amount = request.getParameter("total_amount");
		//实收金额（元）
		String receipt_amount = request.getParameter("receipt_amount");
		if(StringUtils.isBlank(out_trade_no) || StringUtils.isBlank(trade_no)
				|| StringUtils.isBlank(trade_status) || StringUtils.isBlank(total_amount)
				|| StringUtils.isBlank(receipt_amount)){
			logger.error("AlipayController_notifyUrl：缺少参数");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			//保存或修改response表
			AlipayResponse alipayResponse = alipayResponseService.findUniqueByParams("outTradeNo",out_trade_no);
			if(alipayResponse == null){
				alipayResponse = new AlipayResponse();
			}
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
			sender.success(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
