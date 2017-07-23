package com.whayer.wx.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.whayer.wx.wechat.util.NotifyUtil;

/**
 * 验证微信token
 * @author duyu
 * @since 28-02-17
 */

@Controller
public class NotifyController {
	
	private final static Logger log = LoggerFactory.getLogger(NotifyController.class);
	
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
			
			//领取事件
			if("user_get_card".equals(eventType)){
				String fromUserName = map.get("FromUserName");
				String createTime = map.get("CreateTime");
				String cardId = map.get("CardId");
				String code = map.get("UserCardCode");
				String outer = map.get("OuterStr");
				String[] arr = {fromUserName, cardId, code, outer, createTime};
				log.info("推送消息为：" + StringUtils.join(arr, "\n"));
			}
		}
		//文本消息
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
