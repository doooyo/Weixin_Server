package com.whayer.wx.pay.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.whayer.wx.pay.vo.PayInfo;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.order.service.OrderService;
import com.whayer.wx.order.vo.Order;
import com.whayer.wx.pay.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@RequestMapping(value = "/pay")
@Controller
public class PayController extends BaseController{
	private final static Logger log = LoggerFactory.getLogger(PayController.class);
	
	@Resource
	private OrderService orderService;
	
	@ResponseBody
    @RequestMapping(value = "/callback", produces = "application/xml")
    public void callback(String code, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/**
		 * <xml> 
		 * 	<return_code><![CDATA[SUCCESS]]></return_code> 
		 * 	<return_msg><![CDATA[OK]]></return_msg>
		 * </xml>
		 */
		
		InputStream inputStream;
		StringBuffer sb = new StringBuffer();
		inputStream = request.getInputStream();
		String s = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();

		Map<String, String> map = CommonUtil.parseXml(sb.toString());

		SortedMap packageParams = new TreeMap();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = map.get(parameter);

			String v = "";
			if (null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}
		log.info(packageParams.toString());

		// 判断签名是否正确
		//if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, Constant.APP_KEY)) {
			// ------------------------------
			// 处理业务开始
			// ------------------------------
			String resXml = "";
			if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
				// 这里是支付成功
				////////// 执行自己的业务逻辑////////////////
				String mch_id = (String) packageParams.get("mch_id");
				String openid = (String) packageParams.get("openid");
				String is_subscribe = (String) packageParams.get("is_subscribe");
				String out_trade_no = (String) packageParams.get("out_trade_no");

				String total_fee = (String) packageParams.get("total_fee");

				log.info("mch_id:" + mch_id);
				log.info("openid:" + openid);
				log.info("is_subscribe:" + is_subscribe);
				log.info("out_trade_no:" + out_trade_no);
				log.info("total_fee:" + total_fee);

				////////// 执行自己的业务逻辑////////////////

				log.info("支付成功");
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				resXml = "<xml>" 
						+"<return_code><![CDATA[SUCCESS]]></return_code>" 
						+"<return_msg><![CDATA[OK]]></return_msg>"
						+"</xml>";

			} else {
				log.info("支付失败,错误信息：" + packageParams.get("err_code"));
				resXml = "" + "" + "" + "";
			}
			// ------------------------------
			// 处理业务完毕
			// ------------------------------
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
//		} 
//		else {
//			log.info("通知签名验证失败");
//		}
	}
	
	/**
	 * 预支付
	 * @param code
	 * @param model
	 * @param request
	 * @return {prepayId:预支付Id nonceStr:定长的随机纯字母字符串}
	 */
	@ResponseBody
    @RequestMapping(value = "/prepay", produces = "text/html;charset=UTF-8")
    public ResponseCondition prePay(String code, String orderId, ModelMap model, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        //ObjectMapper mapper = new ObjectMapper();


        if(isNullOrEmpty(code) || isNullOrEmpty(orderId)){
        	log.error("\n======================================================");
            log.error("code: " + code + "; orderId: " + orderId);
            return getResponse(X.FALSE);
        }

        //code 换取 openid 和 session_key
        String openId = getOpenId(code);
        
        if(StringUtils.isBlank(openId)) {
        	log.error("openId: " + openId);
            ResponseCondition res = getResponse(X.FALSE);
            res.setErrorMsg("获取到openId为空");
            return res;
        } else {
            openId = openId.replace("\"", "").trim();

            String clientIP = CommonUtil.getClientIp(request);

            log.debug("openId: " + openId + ", clientIP: " + clientIP);
            
            //获取业务系统订单信息
            Order order = orderService.getOrderById(orderId);
            if(isNullOrEmpty(order)){
            	log.error("没有此订单");
            	return getResponse(X.FALSE);
            }

            //随机字符串(32字符以下)
            String randomNonceStr = RandomUtils.generateMixString(32);
            String prepayId = unifiedOrder(openId, clientIP, randomNonceStr, order);

            log.debug("prepayId: " + prepayId);

            if(StringUtils.isBlank(prepayId)) {
            	ResponseCondition res = getResponse(X.FALSE);
                res.setErrorMsg("未获取到prepayId");
                return res;
            } else {
                map.put("prepayId", prepayId);
                map.put("nonceStr", randomNonceStr);
                
                ResponseCondition res = getResponse(X.TRUE);
                List<Map<String, Object>> list = new ArrayList<>();
                list.add(map);
                res.setList(list);
                return res;
            }
        }

//        try {
//            map.put("result", result);
//            map.put("info", info);
//            content = mapper.writeValueAsString(map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        return content;
    }
	
	
	/**
     * 调用统一下单接口
     * @param openId
     */
    private String unifiedOrder(String openId, String clientIP, String randomNonceStr, Order order) {

        try {

            String url = Constant.URL_UNIFIED_ORDER;//https://api.mch.weixin.qq.com/pay/unifiedorder 统一下单地址

            PayInfo payInfo = createPayInfo(openId, clientIP, randomNonceStr, order);
            String md5 = getSign(payInfo);
            payInfo.setSign(md5);

            log.debug("md5 value: " + md5);

            String xml = CommonUtil.payInfoToXML(payInfo);
            xml = xml.replace("__", "_").replace("<![CDATA[1]]>", "1");
            //xml = xml.replace("__", "_").replace("<![CDATA[", "").replace("]]>", "");
            log.debug(xml);

            StringBuffer buffer = HttpUtil.httpsRequest(url, "POST", xml);
            log.debug("unifiedOrder request return body: \n" + buffer.toString());
            Map<String, String> result = CommonUtil.parseXml(buffer.toString());


            String return_code = result.get("return_code");
            if(StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {

                String return_msg = result.get("return_msg");
                if(StringUtils.isNotBlank(return_msg) && !return_msg.equals("OK")) {
                    log.error("统一下单错误！");
                    return "";
                }

                String prepay_Id = result.get("prepay_id");
                return prepay_Id;

            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    
    private PayInfo createPayInfo(String openId, String clientIP, String randomNonceStr, Order order) {

        Date date = new Date();
        //订单生成时间
        String timeStart = TimeUtils.getFormatTime(date, Constant.TIME_FORMAT);
        
        //2天后订单过期
        String timeExpire = TimeUtils.getFormatTime(TimeUtils.addDay(date, Constant.TIME_EXPIRE), Constant.TIME_FORMAT);

        //订单号
        //2017-04-18 by duyu   改为业务系统订单id 
        //String randomOrderId = CommonUtil.getRandomOrderId();

        PayInfo payInfo = new PayInfo();
        payInfo.setAppid(Constant.APP_ID);
        payInfo.setMch_id(Constant.MCH_ID);
        payInfo.setDevice_info("WEB");
        payInfo.setNonce_str(randomNonceStr);
        payInfo.setSign_type("MD5");  //默认即为MD5
        payInfo.setBody("尚康阳光-病原检测");
        payInfo.setAttach("支付测试");
        payInfo.setOut_trade_no(order.getId());//(randomOrderId);
        payInfo.setTotal_fee(1); //order.getAmount() * 100
        payInfo.setSpbill_create_ip(clientIP);
        payInfo.setTime_start(timeStart);
        payInfo.setTime_expire(timeExpire);
        payInfo.setNotify_url(Constant.URL_NOTIFY);
        payInfo.setTrade_type("JSAPI");
        payInfo.setLimit_pay("no_credit");
        payInfo.setOpenid(openId);

        return payInfo;
    }

    private String getSign(PayInfo payInfo) throws Exception {
        StringBuffer sb = new StringBuffer();
        //按字典序列,TODO 注意值为空则不参与签名/sign不参与签名
        sb.append("appid=" + payInfo.getAppid())
                .append("&attach=" + payInfo.getAttach())
                .append("&body=" + payInfo.getBody())
                .append("&device_info=" + payInfo.getDevice_info())
                .append("&limit_pay=" + payInfo.getLimit_pay())
                .append("&mch_id=" + payInfo.getMch_id())
                .append("&nonce_str=" + payInfo.getNonce_str())
                .append("&notify_url=" + payInfo.getNotify_url())
                .append("&openid=" + payInfo.getOpenid())
                .append("&out_trade_no=" + payInfo.getOut_trade_no())
                .append("&sign_type=" + payInfo.getSign_type())
                .append("&spbill_create_ip=" + payInfo.getSpbill_create_ip())
                .append("&time_expire=" + payInfo.getTime_expire())
                .append("&time_start=" + payInfo.getTime_start())
                .append("&total_fee=" + payInfo.getTotal_fee())
                .append("&trade_type=" + payInfo.getTrade_type())
                .append("&key=" + Constant.APP_KEY);

        log.debug("排序后的拼接参数：" + sb.toString());

        return CommonUtil.getMD5(sb.toString().trim()).toUpperCase();//注意大写
    }
	
	
	private String getOpenId(String code) {
		//https://api.weixin.qq.com/sns/jscode2session?appid=wxa142513e524e496c&secret=5d6a7d86048884e7c60f84f7aa85253c&js_code=" + code + "&grant_type=authorization_code
		//https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Constant.APP_ID +
                "&secret=" + Constant.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";

        HttpUtil httpUtil = new HttpUtil();
        try {

            HttpResult httpResult = httpUtil.doGet(url, null, null);

            if(httpResult.getStatusCode() == 200) {

                JsonParser jsonParser = new JsonParser();
                JsonObject obj = (JsonObject) jsonParser.parse(httpResult.getBody());

                log.debug("getOpenId: " + obj.toString());

                if(obj.get("errcode") != null) {
                    log.error("getOpenId returns errcode: " + obj.get("errcode"));
                    return "";
                } else {
                    return obj.get("openid").toString();
                }
                //return httpResult.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
