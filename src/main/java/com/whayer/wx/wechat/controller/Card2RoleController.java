package com.whayer.wx.wechat.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.wechat.service.Card2RoleService;
import com.whayer.wx.wechat.vo.CardInfo;
import com.whayer.wx.wechat.vo.CardInfoIsSelected;

/**
 * 卡劵打标
 * @author duyu
 *
 */
@RequestMapping(value = "/card2role")
@Controller
public class Card2RoleController extends BaseController{

	private final static Logger log = LoggerFactory.getLogger(Card2RoleController.class);
	
	@Resource
	private Card2RoleService card2RoleService;
	
	@RequestMapping(value = "/getCardList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getCardList(HttpServletRequest request, HttpServletResponse response) {
		log.info("Card2RoleController.getCardList()");
		
		Box box = loadNewBox(request);
		String title = box.$p("title"); //卡劵标题
				
		PageInfo<CardInfo> pi = card2RoleService.getCardList(title, box.getPagination());
		
		return pagerResponse(pi);
	}
	
	@RequestMapping(value = "/getCardList2Role", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getCardList2Role(HttpServletRequest request, HttpServletResponse response) {
		log.info("Card2RoleController.getCardList2Role()");
		
		Box box = loadNewBox(request);
		String role = box.$p("role");
		
		List<CardInfoIsSelected> list = card2RoleService.getCardList2Role(role);
		
		ResponseCondition res = getResponse(X.TRUE);
		res.setList(list);
		
		return res;
	}
	
	@RequestMapping(value = "/associate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition card2role(
			@RequestParam(value = "ids[]", required = false) String[] ids, 
			@RequestParam("role") String role,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Card2RoleController.card2role()");
		
		if(isNullOrEmpty(role) || isNullOrEmpty(ids)){
			return getResponse(X.FALSE);
		}
		
		boolean result = card2RoleService.card2role(role, ids);
		
		if(result){
			return getResponse(X.TRUE);
		}
		
		return getResponse(X.FALSE);
	}
	
	 
	
}
