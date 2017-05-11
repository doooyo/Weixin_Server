package com.whayer.wx.pay2.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.whayer.wx.order.vo.Order;
import com.whayer.wx.pay.util.Constant;
import com.whayer.wx.pay.util.HttpResult;
import com.whayer.wx.pay.util.HttpUtil;
import com.whayer.wx.pay.util.RandomUtils;
import com.whayer.wx.pay.util.TimeUtils;
import com.whayer.wx.pay.vo.PayInfo;
import com.whayer.wx.pay2.service.PayV2Service;
import com.whayer.wx.pay2.util.Signature;
import com.whayer.wx.pay2.vo.SignInfo;

@Service
public class PayV2ServiceImpl implements PayV2Service{
	private final static Logger log = LoggerFactory.getLogger(PayV2ServiceImpl.class);

	@Override
	public String getOpenId(String code) {
		String url = Constant.URL_OPENID 
				+ "?appid="    + Constant.APP_ID 
				+ "&secret="  + Constant.APP_SECRET 
				+ "&js_code=" + code 
				+ "&grant_type=authorization_code";

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

	@Override
	public PayInfo createPayInfo(String openId, String clientIP, String randomNonceStr, Order order) {
		
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
        payInfo.setBody("skyg-product");
        payInfo.setAttach("pay test");
        payInfo.setOut_trade_no(order.getId());//(randomOrderId);
        payInfo.setFee_type("CNY");
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

	@Override
	public String getPrepaySign(PayInfo payInfo) {
		try {
			return Signature.getSign(payInfo);
		} catch (Exception e) {
			log.error("获取预支付签名失败");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JSONObject getPaySign(String repayId) {
		SignInfo signInfo = new SignInfo();
		signInfo.setAppId(Constant.APP_ID);
		long time = System.currentTimeMillis()/1000;
		signInfo.setTimeStamp(String.valueOf(time));
		String randomNonceStr = RandomUtils.generateMixString(32);
		signInfo.setNonceStr(randomNonceStr);
		signInfo.setRepay_id("prepay_id=" + repayId);
		signInfo.setSignType("MD5");
		//生成签名
		String sign;
		try {
			sign = Signature.getSign(signInfo);
			
			JSONObject json = new JSONObject();
			json.put("timeStamp", signInfo.getTimeStamp());
			json.put("nonceStr", signInfo.getNonceStr());
			json.put("package", signInfo.getRepay_id());
			json.put("signType", signInfo.getSignType());
			json.put("paySign", sign);
			log.info("再签名:" + json.toJSONString());
			return json;
		} catch (Exception e) {
			log.error("支付签名失败");
			e.printStackTrace();
			return null;
		}
	}
}
