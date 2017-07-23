package com.whayer.wx.pay2.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.whayer.wx.order.vo.Order;
import com.whayer.wx.pay.vo.PayInfo;

public interface PayV2Service {
	/**
	 * 获取openid
	 * @param code
	 * @return
	 */
	public Map<String, String> getOpenIdAndSessionKey(String code);
	
	/**
	 * 获取openid
	 * @param code
	 * @return
	 */
	public String getOpenId(String code);
	
	/**
	 * 创建预支付对象
	 * @param openId
	 * @param clientIP
	 * @param randomNonceStr
	 * @param order
	 * @return
	 */
	public PayInfo createPayInfo(String openId, String clientIP, String randomNonceStr, Order order);
	
	/**
	 * 获取预支付签名
	 * @param payInfo
	 * @return
	 */
	public String getPrepaySign(PayInfo payInfo);
	
	/**
	 * 获取正式支付签名
	 * @param repayId
	 * @return
	 */
	public JSONObject getPaySign(String repayId);
}
