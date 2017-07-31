package com.whayer.wx.wechat.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mohoo.wechat.card.config.BaseConfig;
import com.mohoo.wechat.card.service.WxConsumeService;
import com.mohoo.wechat.card.service.WxVipService;
import com.whayer.wx.common.X;
import com.whayer.wx.common.encrypt.SHA1;
import com.whayer.wx.common.json.Json;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.pay.util.RandomUtils;
import com.whayer.wx.pay2.service.PayV2Service;
import com.whayer.wx.wechat.service.EventService;
import com.whayer.wx.wechat.util.AesCbcUtil;
import com.whayer.wx.wechat.util.Constant;
import com.whayer.wx.wechat.vo.CardInfo;
import com.whayer.wx.wechat.vo.WechatAccount;

@RequestMapping(value = "/card")
@Controller
public class WxCardV2Controller extends BaseController{
	private final static Logger log = LoggerFactory.getLogger(WxCardV2Controller.class);
	
	@Resource
	private PayV2Service payV2Service;
	@Resource
	private EventService eventService;
	
	private WxVipService wvs = null;
	private WxConsumeService wcs = null;
	private BaseConfig bc = null;
	
	public WxCardV2Controller() {
		wvs = new WxVipService();
		wcs = new WxConsumeService();
		bc = new BaseConfig();
		bc.setGetToken(true);
		bc.setSecret(Constant.APPSECRET);
		bc.setAppid(Constant.APPID);
		wcs.setBaseConfig(bc);
		wvs.setBaseConfig(bc);
	}
	
	/**
	 * 用于批量打开卡劵列表进行添加到卡包( wx.addCard() )
	 * 现实中只会单个手动添加
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/addCardList")
	@ResponseBody
	public ResponseCondition addCard(
			//@RequestParam(value = "cardIds[]", required = false) String[] cardIds,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("WxCardV2Controller.addCard()");
		
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
	 * 获取用户已领取卡券列表( wx.openCard() )
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getCardList")
	@ResponseBody
	public ResponseCondition getCardListV2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("WxCardV2Controller.getCardList()");
		
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
		
		//TODO 通过小程序openId 去数据库拿 公众号openId, 若没有再去解密获取unionId并保存。
		//注意小程序获取公众号openId时，必然已经存在了unionId
		
		WechatAccount wa = eventService.getWechatAccountByMiniProgramOpenId(openid);
		if(isNullOrEmpty(wa) 
				|| isNullOrEmpty(wa.getUnionid()) 
				|| isNullOrEmpty(wa.getOfficialAccountOpenid())){
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
			    	   eventService.updateMiniProgramOpenId(unionid, openid);
			   }
			} catch (Exception e) {
			   e.printStackTrace();
			}
			
		    wa = eventService.getWechatAccountByUnionId(unionid);
			if(isNullOrEmpty(wa)){
				ResponseCondition res = getResponse(X.FALSE);
				res.setErrorMsg("关联关系不存在");
				return res;
			}
		}
		
		
		ResponseCondition res = getResponse(X.TRUE);
		
		if(isNullOrEmpty(wa.getOfficialAccountOpenid())){
			log.error("数据库还未保存公众号openId");
			res.setErrorMsg("数据库还未保存公众号openId");
			res.setIsSuccess(X.FALSE);
			return res;
		}
		
		Map<String, Object> result = wvs.getCardIdList(wa.getOfficialAccountOpenid(), "");//cardId为空表示查询所有已领取的劵
		
		log.info("获取用户已领取卡劵结果列表：" + JSONObject.toJSONString(result));
		
		@SuppressWarnings("unchecked")
		List<JSONObject> list = (List<JSONObject>)result.get("card_list");
		res.setList(list);
		return res;
	}
	
	/**
	 * 获取所有可用卡劵列表详情(数据库获取)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
    @RequestMapping(value = "/getAllCardListDetail", method = RequestMethod.GET)
    public ResponseCondition getAllCardListDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("WxCardV2Controller.getAllCardListDetail()");
		
		Box box = loadNewBox(request);
		
		String role = box.$p("code");
		
		if(isNullOrEmpty(role)){
			return getResponse(X.FALSE);
		}
		
		List<CardInfo> list = eventService.getCardListDetail(role);
		
		ResponseCondition res = getResponse(X.TRUE);
		res.setList(list);
		
		return res;
	}
	
	/**
	 * 获取用户自身已领取卡劵列表详情
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
    @RequestMapping(value = "/getOwnCardListDetail", method = RequestMethod.GET)
    public ResponseCondition getOwnCardListDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("WxCardV2Controller.getOwnCardListDetail()");
		
		Box box = loadNewBox(request);
		
		// 登陆态code
		String code = box.$p("code");
		Map<String, String> map = payV2Service.getOpenIdAndSessionKey(code);
		if(isNullOrEmpty(map)){
			log.error("小程序openid获取失败");
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("小程序openid获取失败");
			return res;
		}
		
		String openid = map.get("openid");
		//String session_key = map.get("session_key");
		
		//默认从数据库取
		WechatAccount wa = eventService.getWechatAccountByMiniProgramOpenId(openid);
		
		if(!isNullOrEmpty(wa) && !isNullOrEmpty(wa.getOfficialAccountOpenid())){
			Map<String, Object> result = wvs.getCardIdList(wa.getOfficialAccountOpenid(), "");//cardId为空表示查询所有已领取的劵
			
			log.info("获取用户已领取卡劵结果列表：" + JSONObject.toJSONString(result));
			
			JSONArray array = (JSONArray)result.get("card_list");
			if(!isNullOrEmpty(array)){
				List<JSONObject> voList = new ArrayList<>();
				List<String> cardIdList = new ArrayList<>();
				for (Object object : array) {
					String _cardId = ((JSONObject)object).getString("card_id");
					cardIdList.add(_cardId);
				}
				
				List<CardInfo> _result = eventService.getCardListDetailByCardIds(cardIdList);
				for (CardInfo cardInfo : _result) {
					String cardId = cardInfo.getCardId();
					for (Object obj : array) {
						JSONObject jsonObject = (JSONObject)obj;
						String _cardId = jsonObject.getString("card_id");
						String _code = jsonObject.getString("code");
						if(_cardId.equals(cardId)){
							JSONObject cardObj = JSONObject.parseObject(Json.toJson(cardInfo));
							cardObj.put("code", _code);
							voList.add(cardObj);
							continue;
						}
					}
				}
				
				ResponseCondition res = getResponse(X.TRUE);
				res.setList(voList);
				return res;
			}else{
				return getResponse(X.TRUE);
			}
			
		}else {
			return getResponse(X.TRUE);
		}
	}
	
	/**
	 * 验证已领卡劵的有效性
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
    @RequestMapping(value = "/validateCardCode", method = RequestMethod.POST)
    public ResponseCondition validateCardCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("WxCardV2Controller.validateCardCode()");
		
		Box box = loadNewBox(request);
		String cardCode = box.$p("card_code");
		String cardId = box.$p("card_id");
		
		if(isNullOrEmpty(cardCode) || isNullOrEmpty(cardId)){
			return getResponse(X.FALSE);
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("card_id", cardId);
		params.put("code", cardCode);
		params.put("check_consume", false);
		
		Map<String, Object> result = wcs.getCode(params);
		
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(result);
		
		ResponseCondition res = getResponse(X.TRUE);
		res.setList(list);
		
		return res;
	}
}
