package com.whayer.wx.wechat.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mohoo.wechat.card.config.BaseConfig;
import com.mohoo.wechat.card.service.WxVipService;
import com.whayer.wx.common.X;
import com.whayer.wx.common.encrypt.SHA1;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.pay.util.RandomUtils;
import com.whayer.wx.pay2.service.PayV2Service;
import com.whayer.wx.wechat.service.EventService;
import com.whayer.wx.wechat.util.AesCbcUtil;
import com.whayer.wx.wechat.util.Constant;
import com.whayer.wx.wechat.vo.WechatAccount;

@Controller
public class WxCardController extends BaseController{
	
	private final static Logger log = LoggerFactory.getLogger(WxCardController.class);
	
	@Resource
	private PayV2Service payV2Service;
	@Resource
	private EventService eventService;
	
	private WxVipService wcs = null;
	private BaseConfig bc = null;
	
	public WxCardController() {
		wcs = new WxVipService();
		bc = new BaseConfig();
		bc.setGetToken(true);
		bc.setSecret(Constant.APPSECRET);
		bc.setAppid(Constant.APPID);
		wcs.setBaseConfig(bc);
	}
	
	/**
	 * 升级公众号卡劵
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://duyu.ngrok.cc/update2minicard?card_id=pwN5lwEEyGMIZob8Ws0YNUTOHy28&encrypt_code=GZ%2BsckbRF4OBDD8U%2FdMpqdd0pqS%2Fsg8MlzlLB2nHjgg%3D&openid=owN5lwKtQBu0m2sx3kbvz1JESe-k
	@RequestMapping("/update2minicard")
	@ResponseBody
	public void update2minicard(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("Update2MiniCardController.update2minicard()");
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
		
		//前端必须componentURI编码,后端再解码(否则'='会被解码为'#e')
		encrypt_code = URLDecoder.decode(encrypt_code, "UTF-8");
		
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
		log.info("Update2MiniCardController.decryptCode()");
		
		Box box = loadNewBox(request);
		
		String code = box.$p("code");//"GZ+sckbRF4OBDD8U/dMpqbT8k+UFXOaYn+Ps3Q2UVfo=";//box.$p("code");
		
		if(isNullOrEmpty(code)){
			return getResponse(X.FALSE);
		}
		
		//前端必须componentURI编码,后端再解码(否则'='会被解码为'#e')
		code = URLDecoder.decode(code, "UTF-8");
		log.info("解码参数code:"+code);
		
		Map<String, Object> params = new HashMap<>();
		params.put("encrypt_code", code);
		Map<String, Object> result = wcs.decryptCard(JSONObject.toJSONString(params));
		log.info("解码结果：" + JSONObject.toJSONString(result));
		String real_code = String.valueOf(result.get("code"));
		
		log.info("解码后的code："+real_code);
		
		ResponseCondition res = getResponse(X.TRUE);
		ArrayList<String> list = new ArrayList<String>();
		list.add(real_code);
		res.setList(list);
		
		return res;
	}
	
	/**
	 * 用于添加单个卡劵测试
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/addCardTest")
	@ResponseBody
	public ResponseCondition addCardTest(HttpServletRequest request, HttpServletResponse response) {
		log.info("Update2MiniCardController.addCardTest()");
		
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
	
	/**
	 * 用于批量添加卡劵测试
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/addCardTestV2")
	@ResponseBody
	public ResponseCondition addCardTestV2(
			//@RequestParam(value = "cardIds[]", required = false) String[] cardIds,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Update2MiniCardController.addCardTestV2()");
		
		Box box = loadNewBox(request);
		String ids = box.$p("cardIds");
				
		if(isNullOrEmpty(ids)){
			return getResponse(X.FALSE);
		}
		
		String[] cardIds = ids.split(",");
		
		
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		for (int i = 0; i < cardIds.length; i++) {
			
			String apiTicket = wcs.getWxCardTicket();
			String nonceStr = RandomUtils.generateMixString(32);;
			String timestamp = String.valueOf(System.currentTimeMillis()/1000);
			
			//String[] arr = {apiTicket, cardIds[i], nonceStr, timestamp};
			//Arrays.sort(arr, String.CASE_INSENSITIVE_ORDER);
			
			ArrayList<String> arr = new ArrayList<String>();
			arr.add(apiTicket);
			arr.add(cardIds[i]);
			arr.add(nonceStr);
			arr.add(timestamp);
			Collections.sort(arr);
			
			StringBuilder sb = new StringBuilder();
	        for(int k = 0; k < arr.size(); k ++) {
	            sb.append(arr.get(k));
	        }
	        String result = sb.toString();
	        
	        String signature = SHA1.getSha1(result);
	        
	        log.info("\n添加卡劵签名参数与结果:" 
	        + "\n apiTicket:" +apiTicket
	        + "\n nonceStr:" +nonceStr
	        + "\n timestamp:" +timestamp
	        + "\n cardId:" + cardIds[i] 
    		+ "\n signature:" +signature
	        + "\n 排序结果:" + result);
	        
	        JSONObject json = new JSONObject();
	        JSONObject child = new JSONObject();
			
			//json.put("ticket", apiTicket);
			json.put("cardId", cardIds[i]);
			
			child.put("nonce_str", nonceStr);
			child.put("timestamp", timestamp);
			child.put("signature", signature);
			
			json.put("cardExt", JSONObject.toJSONString(child));
			
			list.add(json);
		}
		
		ResponseCondition res = getResponse(X.TRUE);
		res.setList(list);
        return res;
	}
	
	/**
	 * 获取用户已领取卡券列表（测试用）
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getCardList")
	@ResponseBody
	public ResponseCondition getCardList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("Update2MiniCardController.getCardList()");
		
//		Box box = loadNewBox(request);
//		String code = box.$p("code");
//		String cardId = box.$p("card_id");
//		
//		if(isNullOrEmpty(code)){
//			return getResponse(X.FALSE);
//		}
//		
//		//获取小程序openid
//		String openId = payV2Service.getOpenId(code);
//		log.info("小程序openid：" + openId);
//		
//		if(isNullOrEmpty(openId)){
//			log.error("openid 获取失败");
//			ResponseCondition res = getResponse(X.FALSE);
//			res.setErrorMsg("openid 获取失败");
//			return res;
//		}
		
		//小程序某粉丝openid：o1z7s0Fm7hxkKspl_i3sH3yqOp_Q
		//公众号某粉丝openid：owN5lwOnxMBTLQeLOXuCjpaDewfM
		//unionid某粉丝：oc_yAxJ9szy52x_5xUv5cVxwM__Q
		
		Box box = loadNewBox(request);
		
		
		String openId = box.$p("openId");//"oc_yAxJ9szy52x_5xUv5cVxwM__Q";//"owN5lwOnxMBTLQeLOXuCjpaDewfM";
		String cardId = "";
		
		ResponseCondition res = getResponse(X.TRUE);
		Map<String, Object> result = wcs.getCardIdList(openId, cardId);
		
		log.info("获取卡劵列表结果：" + JSONObject.toJSONString(result));
		
		@SuppressWarnings("unchecked")
		List<JSONObject> list = (List<JSONObject>)result.get("card_list");
		res.setList(list);
		return res;
	}
	
	/**
	 * 获取公众号用户openid列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getUserList")
	@ResponseBody
	public ResponseCondition getUserList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("Update2MiniCardController.getUserList()");
		
		Map<String, Object> result = wcs.getUserList();
		
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(result);
		ResponseCondition res = getResponse(X.TRUE);
		res.setList(list);
		return res;
	}
	
	/**
	 * 小程序wx.getUserInfo()获取用户敏感信息并解密（测试用）
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Deprecated
	@ResponseBody
    @RequestMapping(value = "/decodeUserInfo", method = RequestMethod.POST)
    public ResponseCondition decodeUserInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		log.info("Update2MiniCardController.decodeUserInfo()");
		
		Box box = loadNewBox(request);
		
		String encryptedData = box.$p("encryptedData");
		String iv =  box.$p("iv"); 
		String code = box.$p("code"); 
		
		if(isNullOrEmpty(encryptedData) || isNullOrEmpty(iv) || isNullOrEmpty(code)){
			return getResponse(X.FALSE);
		}
		
		encryptedData = URLDecoder.decode(encryptedData, "UTF-8");
		iv = URLDecoder.decode(iv, "UTF-8");
		
		//获取小程序openid 与 session_key
		Map<String, String> map = payV2Service.getOpenIdAndSessionKey(code);
		if(isNullOrEmpty(map)){
			log.error("小程序openid获取失败");
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("小程序openid获取失败");
			return res;
		}
		
		String openid = map.get("openid");
		String session_key = map.get("session_key");

        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
		JSONObject userInfoJSON = null;
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            log.info("用户解密的数据：" + result);
            if (!isNullOrEmpty(result)) {

                userInfoJSON = JSONObject.parseObject(result);
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                
                log.info("主动获取的openid：" + openid +"/ 用户解密数据的openid：" + userInfoJSON.get("openId")
                + "/ unionId：" + userInfoJSON.get("unionId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ResponseCondition res = getResponse(X.TRUE);
        List<JSONObject> list = new ArrayList<>();
        list.add(userInfoJSON);
        res.setList(list);
        
        return res;
    }
	
	@Deprecated
	@ResponseBody
    @RequestMapping(value = "/getUserBaseInfo", method = RequestMethod.GET)
    public ResponseCondition getUserBaseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("Update2MiniCardController.getUserBaseInfo()");
		
		Box box = loadNewBox(request);
		
		String openid = box.$p("openid");
		if(isNullOrEmpty(openid)){
			return getResponse(X.FALSE);
		}
		
		Map<String, Object> result = wcs.getUserBaseInfo(openid);
		
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(result);
		ResponseCondition res = getResponse(X.TRUE);
		res.setList(list);
		return res;
	}
	
	
	/**
	 * 获取用户已领取卡券列表（最新）
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getCardListV2")
	@ResponseBody
	public ResponseCondition getCardListV2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("Update2MiniCardController.getCardListV2()");
		
		Box box = loadNewBox(request);
		
		String encryptedData = box.$p("encryptedData");
		String iv =  box.$p("iv"); 
		String code = box.$p("code"); //用户登陆态code
		
		if(isNullOrEmpty(encryptedData) || isNullOrEmpty(iv) || isNullOrEmpty(code)){
			return getResponse(X.FALSE);
		}
		
		encryptedData = URLDecoder.decode(encryptedData, "UTF-8");
		iv = URLDecoder.decode(iv, "UTF-8");
		
		/**
		 * 经验证，只有公共号的openid才可以获取用户已领取的卡劵列表
		 * 
		 * 小程序某粉丝openid：o1z7s0Fm7hxkKspl_i3sH3yqOp_Q
		 * 公众号某粉丝openid：owN5lwOnxMBTLQeLOXuCjpaDewfM
		 * unionid某粉丝：oc_yAxJ9szy52x_5xUv5cVxwM__Q
		 */
		
		//获取小程序openid 与 session_key
		Map<String, String> map = payV2Service.getOpenIdAndSessionKey(code);
		if(isNullOrEmpty(map)){
			log.error("小程序openid获取失败");
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("小程序openid获取失败");
			return res;
		}
		
		String openid = map.get("openid");
		String session_key = map.get("session_key");
		
		////////////////2、对encryptedData加密数据进行AES解密 ////////////////
		JSONObject userInfoJSON = null;
		String unionid = "";
		try {
		   String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
		   log.info("用户解密的数据：" + result);
		   if (!isNullOrEmpty(result)) {
		
		       userInfoJSON = JSONObject.parseObject(result);
		       Map<String, Object> userInfo = new HashMap<>();
		       
		       userInfo.put("openId", userInfoJSON.get("openId"));
		       userInfo.put("nickName", userInfoJSON.get("nickName"));
		       userInfo.put("gender", userInfoJSON.get("gender"));
		       userInfo.put("city", userInfoJSON.get("city"));
		       userInfo.put("province", userInfoJSON.get("province"));
		       userInfo.put("country", userInfoJSON.get("country"));
		       userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
		       userInfo.put("unionId", userInfoJSON.get("unionId"));
		       
		       log.info("主动获取的openid：" + openid +"/ 用户解密数据的openid：" + userInfoJSON.get("openId")
		       + "/ unionId：" + userInfoJSON.get("unionId"));
		       
		       unionid = userInfoJSON.getString("unionId");
		       if(!isNullOrEmpty(unionid))
		    	   eventService.updateMiniProgramAppId(unionid, openid);
		   }
		} catch (Exception e) {
		   e.printStackTrace();
		}
		
		WechatAccount wa = eventService.getWechatAccountByUnionId(unionid);
		if(isNullOrEmpty(wa)){
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("关联关系不存在");
			return res;
		}
		
		ResponseCondition res = getResponse(X.TRUE);
		Map<String, Object> result = wcs.getCardIdList(wa.getOfficialAccountOpenid(), "");//cardId为空表示查询所有已领取的劵
		
		log.info("获取用户已领取卡劵结果列表：" + JSONObject.toJSONString(result));
		
		@SuppressWarnings("unchecked")
		List<JSONObject> list = (List<JSONObject>)result.get("card_list");
		res.setList(list);
		return res;
	}
}
