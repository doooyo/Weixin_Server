package com.whayer.wx.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mohoo.wechat.card.config.BaseConfig;
import com.mohoo.wechat.card.service.WxVipService;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.wechat.service.EventService;
import com.whayer.wx.wechat.util.Constant;
import com.whayer.wx.wechat.util.NotifyUtil;
import com.whayer.wx.wechat.vo.CardInfo;
import com.whayer.wx.wechat.vo.WechatAccount;

/**
 * 验证微信token
 * @author duyu
 * @since 28-02-17
 */

@Controller
public class NotifyController extends BaseController{
	
	private final static Logger log = LoggerFactory.getLogger(NotifyController.class);
	
	@Resource
	private EventService eventService;
	
	private static final Object object = new Object();
	
	private static final Object object1 = new Object();
	
	private static final ConcurrentHashMap<String, String> currentMap = new ConcurrentHashMap<>(32);
	
	private WxVipService wcs = null;
	private BaseConfig bc = null;
	
	public NotifyController() {
		wcs = new WxVipService();
		bc = new BaseConfig();
		bc.setGetToken(true);
		bc.setSecret(Constant.APPSECRET);
		bc.setAppid(Constant.APPID);
		wcs.setBaseConfig(bc);
	}
	
	@RequestMapping(value = "/notify", method = RequestMethod.GET)
	@ResponseBody
	public void notify(String signature, String nonce, String echostr, String timestamp,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.debug("NotifyController.notify()");
		
		PrintWriter pw = response.getWriter();
		if(NotifyUtil.verifySignature(signature, timestamp, nonce)){
			
			pw.print(echostr);
		}
	}
	
	
	private void clearCurrentMap(){
		if(currentMap.size() > 26){
			currentMap.clear();
		}
	}
	
	/**
	 * 消息通知处理
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	@ResponseBody
	public void notify1(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("NotifyController.notify1()");
		
		PrintWriter pw = response.getWriter();
		
		Map<String, String> map = NotifyUtil.xml2Map(request);
		log.info("返回结果为：" + JSONObject.toJSONString(map));
		
		String msgType = map.get("MsgType");
		//事件消息
		if(NotifyUtil.MESSAGE_EVENT.equals(msgType)){
			/**
			 * 关注：subscribe
			 * 取消关注：unsubscribe
			 * 菜单点击：CLICK,VIEW
			 * 卡劵：(只处理1/2/4/5)
			 *     1.审核事件：card_pass_check
			 *     2.领取事件：user_get_card
			 *     3.转赠事件：user_gifting_card
			 *     4.删除事件：user_del_card (指用户删除,非删除劵)
			 *     5.核销事件：user_consume_card
			 *     6.买单事件：user_pay_from_pay_cell
			 *     7.进入会员卡事件：user_view_card
			 *     8.从卡劵进入公众号：user_enter_session_from_card
			 *     9.会员卡内容更新：update_member_card
			 *     10.库存报警：card_sku_remind
			 *     11.卷点流水：card_pay_order
			 *     12.会员卡激活：submit_membercard_user_info
			 */
			
			String eventType = map.get("Event");
			
			//审核通过
			if("card_pass_check".equals(eventType)){
				String cardId = map.get("CardId");
				String fromUserName = map.get("FromUserName");
				String createTime = map.get("CreateTime");
				String msgId = fromUserName + createTime;
				
				// 幂等性方式1
				synchronized (object1) {
					if(!currentMap.containsKey(msgId)){
						currentMap.put(msgId, msgId);
						
						Map<String, Object> result = wcs.getCard(cardId);
						CardInfo info = eventService.createCardInfo(result);
						if(!isNullOrEmpty(info)){
							//保存审核通过的卡劵到数据库
							eventService.saveCardInfo(info);
							
							//触发批量同步已审核通过/已发放的卡劵
							
							HashMap<String, Object> params = new HashMap<>();
							String[] status = {"CARD_STATUS_VERIFY_OK", "CARD_STATUS_DISPATCH"};
							params.put("offset", 0);
							params.put("count", 50);
							params.put("status_list", status);
							String json = JSON.toJSONString(params);
							
							Map<String, Object> cardBatch = wcs.batchGetCard(json);
							if(!isNullOrEmpty(cardBatch)){
								
								@SuppressWarnings("unchecked")
								List<String> originCardIds = (List<String>)cardBatch.get("card_id_list");
								List<String> cardIds = eventService.getCardIds();
								
								//List<String> cloneOriginCardIds = new ArrayList<>();
								//Collections.copy(cloneOriginCardIds,originCardIds);
								List<String> cloneOriginCardIds = new ArrayList<>(originCardIds);
								List<String> cloneCardIds = new ArrayList<>(cardIds);
								
								originCardIds.removeAll(cardIds);
								cloneCardIds.removeAll(cloneOriginCardIds);
								if(cloneCardIds.size() > 0){
									eventService.deleteCardByIds(cloneCardIds);
								}
								
								
								for (String _cardId : originCardIds) {
									Map<String, Object> _cardInfo = wcs.getCard(_cardId);
									CardInfo _info = eventService.createCardInfo(_cardInfo);
									if(!isNullOrEmpty(_info)){
										//同步卡劵到数据库
										eventService.saveCardInfo(_info);
									}
								}
							}
						}
					}else{
						clearCurrentMap();
					}
				}
			}
				
			
			//领取
			if("user_get_card".equals(eventType)){
				String fromUserName = map.get("FromUserName");
				String createTime = map.get("CreateTime");
				String cardId = map.get("CardId");
				String code = map.get("UserCardCode");
				String outer = map.get("OuterStr");
				String[] arr = {fromUserName, cardId, code, outer, createTime};
				log.info("推送消息为：" + StringUtils.join(arr, "|"));
				
				String msgid = fromUserName + createTime;
				
				// 幂等性方式2
				synchronized (object) {
					
				    boolean duplicate = eventService.isMsgIdIsExist(msgid);
					if(!duplicate){
						Map<String, Object> result = wcs.getUserBaseInfo(fromUserName);
						String unionid = String.valueOf(result.get("unionid"));
						if(isNullOrEmpty(unionid)){
							log.info("unionid获取失败");
						}else{
							boolean unionidExist = eventService.isUnionIdIsExist(unionid);
							if(!unionidExist){
								WechatAccount wa = new WechatAccount();
								wa.setId(X.uuidPure());
								wa.setMsgid(msgid);
								wa.setOfficialAccountOpenid(fromUserName);
								wa.setUnionid(unionid);
								eventService.saveUnionIdAndOpenId(wa);
							}
						}
					}
				}
			}
		}
		//文本
		if(NotifyUtil.MESSAGE_TEXT.equals(msgType)){
			
		}
		if(NotifyUtil.MESSAGE_IMAGE.equals(msgType)){
			
		}
		if(NotifyUtil.MESSAGE_VOICE.equals(msgType)){
			
		}
		if(NotifyUtil.MESSAGE_VIDEO.equals(msgType)){
	
		}
		if(NotifyUtil.MESSAGE_LINK.equals(msgType)){
			
		}
		if(NotifyUtil.MESSAGE_LOCATION.equals(msgType)){
			
		}
		
		
		pw.print("");
		//pw.close();
	}
}
