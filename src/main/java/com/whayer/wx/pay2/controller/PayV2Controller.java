package com.whayer.wx.pay2.controller;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.whayer.wx.common.X;
import com.whayer.wx.common.bean.SpringFactory;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.order.service.OrderService;
import com.whayer.wx.order.vo.Order;
import com.whayer.wx.pay.util.CommonUtil;
import com.whayer.wx.pay.util.Constant;
import com.whayer.wx.pay.util.RandomUtils;
import com.whayer.wx.pay.vo.PayInfo;
import com.whayer.wx.pay2.service.PayV2Service;
import com.whayer.wx.pay2.util.BarCodeKit;
import com.whayer.wx.pay2.util.HttpRequest;
import com.whayer.wx.pay2.util.Signature;
import com.whayer.wx.pay2.util.XStreamUtil;
import com.whayer.wx.pay2.vo.OrderQuery;
import com.whayer.wx.pay2.vo.OrderReturnInfo;

@RequestMapping(value = "/payV2")
@Controller
public class PayV2Controller extends BaseController{
	private final static Logger log = LoggerFactory.getLogger(PayV2Controller.class);
	
	@Resource
	private OrderService orderService;
	
	@Resource
	private PayV2Service payV2Service;
	
	@Resource
	private SpringFactory springFactory;
	
	/**
	 *    生成订单 (业务系统) --> 客户端申请支付
	 *    客户端获取登陆态code
	 *    服务端通过code获取openid/session_key
	 *         https://api.weixin.qq.com/sns/jscode2session?
	 *         appid=小程序ID&secret=小程序SECRET&js_code=登陆态code&grant_type=authorization_code
	 *	  获得预支付prepayId
	 *	       统一下单地址: https://api.mch.weixin.qq.com/pay/unifiedorder  
	 *                     @see https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1
	 *		   预支付签名:1> 非空属性参与签名
	 *     				2> key按字典序排序
	 *     				3> MD5签名后转大写
	 *     				4> 最后签名赋值setSign(sign)
	 *               
	 *	  正式支付签名: 此次签名为客户端发起支付请求所需字段
	 *         签名所需字段: appId,nonceStr,package,signType,timeStamp,key (字典序后再加key)
	 *         1> nonceStr:后端生成随机字符串(低于32位)
	 *         2> package :'prepay_id='+prepayId
	 *         3> signType:MD5
	 *         4> timeStamp:时间戳
	 *         5> key:加密密钥
	 *	  输出客户端需要的参数
	 *		   paySign: 上述二次签名结果
	 *		   prepayId:预支付id
	 *		   nonceStr:上述服务端生产随机字符串(低于32位)
	 *	  客户端支付...
	 *    回调: 回调通知8次,服务端返回成功信息并停止通知,否则认为支付失败;
	 *         需要验证签名,避免回调通知伪造;
	 *         再对业务订单进行状态设置,同时需加以写锁控制,以免业务订单的数据混乱造成资金损失,账目对不上
	 *         
	 */
	
	/**
	 * 预支付
	 * @param code
	 * @param orderId
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/prepay", method = RequestMethod.POST)
    public ResponseCondition prePay(HttpServletRequest request, HttpServletResponse response) 
    		throws UnrecoverableKeyException, 
    		KeyManagementException, 
    		ClientProtocolException, 
    		KeyStoreException, 
    		NoSuchAlgorithmException, 
    		IOException {
		log.debug("PayV2Controller.prepay()");
		
		Box box = loadNewBox(request);
		String code = box.$p("code");
		String orderId = box.$p("orderId");
		
		if(isNullOrEmpty(orderId) || isNullOrEmpty(code)){
			log.error("参数为空: code: " + code + "; orderId: " + orderId);
			return getResponse(X.FALSE);
		}
		
		//获取业务系统订单信息
        Order order = orderService.getOrderById(orderId);
        if(isNullOrEmpty(order)){
        	log.error("没有此订单");
        	ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("没有此订单");
			return res;
        }
		
		//获取openid
		String openId = payV2Service.getOpenId(code);
		if(isNullOrEmpty(openId)){
			log.error("openid 获取失败");
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("openid 获取失败");
			return res;
		}
		
		openId = openId.replace("\"", "").trim();
		//获取客户端IP
		String clientIP = CommonUtil.getClientIp(request);
        //随机字符串(32字符以下)
        String randomNonceStr = RandomUtils.generateMixString(32);
        
        PayInfo payInfo = payV2Service.createPayInfo(openId, clientIP, randomNonceStr, order);
        String sign = payV2Service.getPrepaySign(payInfo);
        payInfo.setSign(sign);
        
        String result = HttpRequest.sendPost(Constant.URL_UNIFIED_ORDER, order);
		log.info("下单返回:"+result);
		
		//TODO 注意此处可能会报错,最好转为Map
		OrderReturnInfo returnInfo = (OrderReturnInfo)XStreamUtil.Xml2Obj(result, OrderReturnInfo.class);
//		XStream xStream = new XStream();
//		xStream.alias("xml", OrderReturnInfo.class); 
//		OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(result);
		
		String prepayId = returnInfo.getPrepay_id();
		if(isNullOrEmpty(prepayId)){
			ResponseCondition res = getResponse(X.FALSE);
			log.error("prepayId 获取失败");
			res.setErrorMsg("prepayId 获取失败");
			return res;
		}
		
		//正式支付再次签名
		JSONObject json = payV2Service.getPaySign(prepayId);
		
		ResponseCondition res = getResponse(X.TRUE);
		List<JSONObject> list = new ArrayList<>();
		list.add(json);
		res.setList(list);
		return res;
	}
	
	
	
	/**
	 * 支付回调
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/callback", produces = "application/xml")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("PayV2Controller.callback()");
		
		/**
		 * @see https://github.com/seven-cm/weixinpay/blob/master/WebContent/payNotifyUrl.jsp
		 * 签名验证
		 * 支付结果中transaction_id是否存在 -->FAIL
		 * 支付结果中查询订单，判断订单真实性
		 * 业务逻辑处理start-->更改订单状态-->保存对账信息到数据库  
		 */
		
		Map<String, String> map = XStreamUtil.Xml2Map(HttpRequest.getRequest(request));
		if(isNullOrEmpty(map)){
			log.error("获取回调参数错误");
		}
		log.debug("回调参数" + map.toString());
		
		String sign = map.get("sign");
		String calcSign = Signature.getSign(map);
		log.debug("sign:" + sign);
		log.debug("calcSign" + calcSign);
		if(!sign.equals(calcSign)){
			response.getWriter().append(getWxReturnMessage(X.FALSE, "签名验证失败"));
		}else{
			String out_trade_no = map.get("out_trade_no");
			log.debug("out_trade_no:" + out_trade_no);
			if(isNullOrEmpty(out_trade_no)){
				response.getWriter().append(getWxReturnMessage(X.FALSE, "支付结果中微信订单号不存在"));
			}else{
				Order order = orderService.getOrderById(out_trade_no);
				if(isNullOrEmpty(order)){
					response.getWriter().append(getWxReturnMessage(X.FALSE, "订单查询失败"));
				}else{
					
					OrderQuery orderQuery = new OrderQuery();
					orderQuery.setAppid(Constant.APP_ID);
					orderQuery.setMch_id(Constant.MCH_ID);
					orderQuery.setNonce_str(RandomUtils.generateMixString(32));
					orderQuery.setOut_trade_no(out_trade_no);
					orderQuery.setSign_type("MD5");
					String qSign = Signature.getSign(orderQuery);
					orderQuery.setSign(qSign);
					
					//TODO 验证签名
					String result = HttpRequest.sendPost(Constant.URL_ORDER_QUERY, orderQuery);
					log.info("query wx order: " + result);
					Map<String, String> resultMap = XStreamUtil.Xml2Map(result);
					String res_out_trade_no = resultMap.get("out_trade_no");
					if(!res_out_trade_no.equals(out_trade_no)){
						response.getWriter().append(getWxReturnMessage(X.FALSE, "微信订单查询失败"));
					}else{
						int count = orderService.updateOrderStatusById(out_trade_no, 1);
						if(count > 0){
							log.info("更新业务订单成功");
							response.getWriter().append(getWxReturnMessage(X.TRUE, "OK"));
						}else{
							log.error("更新业务订单失败");
							response.getWriter().append(getWxReturnMessage(X.FALSE, "更新业务订单失败"));
						}
					}
				}
			}
			
		}
	}
	
	private String getWxReturnMessage(boolean state, String message){
		String str = "<xml><return_code><![CDATA[%s]]></return_code><return_msg><![CDATA[%s]]></return_msg></xml>";
		StringBuffer sb;
		if(state){
			sb = new StringBuffer(String.format(str, "SUCCESS", "OK"));
		}else{
			sb = new StringBuffer(String.format(str, "FAIL", message));
		}
		return sb.toString();
	}
	
	@ResponseBody
    @RequestMapping(value = "/qrcode")
    public void qrcode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.debug("PayV2Controller.qrcode()");
		
		Box box = loadNewBox(request);
		String orderId = box.$p("orderId");
		if(isNullOrEmpty(orderId)){
			return;
		}
		
		String url = Constant.URL_BARCODE + "?orderId=" + orderId;
		org.springframework.core.io.Resource resource = springFactory.getResource("classpath:image/logo.jpg");
        File logoFile = resource.getFile();
        //BufferedImage image = QRCodeKit.createQRCodeWithLogo(url, logoFile);
        //ImageIO.write(image, "gif", response.getOutputStream());
        
        response.setHeader("Progma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		
        BarCodeKit.encode2(url, 300, 300, logoFile, response);
        
        
	}
}
