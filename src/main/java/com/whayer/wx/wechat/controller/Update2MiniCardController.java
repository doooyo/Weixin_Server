package com.whayer.wx.wechat.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mohoo.wechat.card.config.BaseConfig;
import com.mohoo.wechat.card.service.WxVipService;
import com.mysql.fabric.xmlrpc.base.Array;
import com.sun.tools.javac.util.List;
import com.whayer.wx.common.X;
import com.whayer.wx.common.encrypt.SHA1;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.pay.util.RandomUtils;
import com.whayer.wx.wechat.util.Constant;

@Controller
public class Update2MiniCardController extends BaseController{
	
	private final static Logger log = LoggerFactory.getLogger(Update2MiniCardController.class);
	
	private WxVipService wcs = null;
	private BaseConfig bc = null;
	
	public Update2MiniCardController() {
		wcs = new WxVipService();
		bc = new BaseConfig();
		bc.setGetToken(true);
		bc.setSecret(Constant.APPSECRET);
		bc.setAppid(Constant.APPID);
		wcs.setBaseConfig(bc);
	}
	
	//http://duyu.ngrok.cc/update2minicard?card_id=pwN5lwEEyGMIZob8Ws0YNUTOHy28&encrypt_code=GZ%2BsckbRF4OBDD8U%2FdMpqdd0pqS%2Fsg8MlzlLB2nHjgg%3D&openid=owN5lwKtQBu0m2sx3kbvz1JESe-k
	@RequestMapping("/update2minicard")
	@ResponseBody
	public void update2minicard(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.debug("Update2MiniCardController.update2minicard()");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		Box box = loadNewBox(request);
		
		
		String card_id = box.$p("card_id");//"pwN5lwEEyGMIZob8Ws0YNUTOHy28";
		String encrypt_code = box.$p("encrypt_code");//"GZ%2BsckbRF4OBDD8U%2FdMpqdd0pqS%2Fsg8MlzlLB2nHjgg%3D";
		String openid = box.$p("openid");//"owN5lwKtQBu0m2sx3kbvz1JESe-k";
				
		//encrypt_code = URLDecoder.decode(encrypt_code, "UTF-8");
		log.info("card_id:" + card_id +", encrypt_code:"+ encrypt_code + ",openid:"+openid);
		
		if(isNullOrEmpty(card_id) || isNullOrEmpty(encrypt_code)){
			response.getWriter().print("参数缺失！");
			return;
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("encrypt_code", encrypt_code);
		Map<String, Object> result = wcs.decryptCard(JSONObject.toJSONString(params));
		String real_code = String.valueOf(result.get("code"));
		
		log.info("真实code："+real_code);
		
		//获取卡劵类型(通过详情获取)
		String type = wcs.getCardType(card_id);
		log.info("卡劵类型：" + type);
		
		String data = "{" +
				      "\"card_id\":\""+card_id+"\","+
				      "\""+type+"\": {"+
				      "\"base_info\": {"+
				      "\"custom_url_name\": \"小程序\","+
				      "\"custom_url\": \"http://www.qq.com\","+
				      "\"custom_app_brand_user_name\": \""+Constant.ORIGINALID+"@app\","+
				      "\"custom_app_brand_pass\":\"pages/index/index\","+
				      "\"custom_url_sub_title\": \"点击进入\","+
				      "\"promotion_url_name\": \"更多信息\","+
				      "\"promotion_url\": \"http://www.qq.com\","+
				      "\"promotion_app_brand_user_name\": \""+Constant.ORIGINALID+"@app\","+
				      "\"promotion_app_brand_pass\":\"pages/login/login\""+
				      "}"+
				      "}"+
				      "}";
		
		
		Map<String, Object> result2 = wcs.updateCard(data);
		log.info(JSONObject.toJSONString("更新结果为："+result2));
		response.getWriter().print("卡劵升级成功！");
		
	}
	
	/**
	 * code解码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/decryptCode")
	@ResponseBody
	public ResponseCondition decryptCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.debug("Update2MiniCardController.decryptCode()");
		
		Box box = loadNewBox(request);
		
		String code = box.$p("code");
		
		if(isNullOrEmpty(code)){
			return getResponse(X.FALSE);
		}
		
		//springmvc接收已经是解码后的参数
		//code = URLDecoder.decode(code, "utf-8");
		
		Map<String, Object> params = new HashMap<>();
		params.put("encrypt_code", code);
		Map<String, Object> result = wcs.decryptCard(JSONObject.toJSONString(params));
		String real_code = String.valueOf(result.get("code"));
		
		log.info("解码后的code："+real_code);
		
		ResponseCondition res = getResponse(X.TRUE);
		ArrayList<String> list = new ArrayList<String>();
		list.add(real_code);
		res.setList(list);
		
		return res;
	}
	
	/**
	 * 用于添加卡劵测试
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/addCardTest")
	@ResponseBody
	public ResponseCondition addCardTest(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Update2MiniCardController.addCardTest()");
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
		
		Box box = loadNewBox(request);
		
		String cardId = box.$p("cardId");
				
		if(isNullOrEmpty(cardId)){
			return getResponse(X.FALSE);
		}
		
		String apiTicket = wcs.getWxCardTicket();
		String nonceStr = RandomUtils.generateMixString(32);;
		String timestamp = String.valueOf(System.currentTimeMillis()/1000);
		
		String[] arr = {apiTicket, cardId, nonceStr, timestamp};
		Arrays.sort(arr, String.CASE_INSENSITIVE_ORDER);
		
		StringBuilder sb = new StringBuilder();
        for(int i = 0; i < arr.length; i ++) {
            sb.append(arr[i]);
        }
        String result = sb.toString();
        
        String signature = SHA1.getSha1(result);
        log.info(
        		"ticket:" +apiTicket 
        		+ ";cardId:" +cardId 
        		+ ";nonceStr:" + nonceStr
        		+ ";timestamp:" + timestamp
        		+ ";signature:" + signature);
		
		
		JSONObject json = new JSONObject();
		
		json.put("ticket", apiTicket);
		json.put("cardId", cardId);
		json.put("nonceStr", nonceStr);
		json.put("timestamp", timestamp);
		json.put("signature", signature);
		
		ResponseCondition res = getResponse(X.TRUE);
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		list.add(json);
		res.setList(list);
        return res;

		
	}
}
