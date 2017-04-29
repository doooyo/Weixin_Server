package com.whayer.wx.gift.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.gift.service.GiftService;
import com.whayer.wx.gift.vo.Gift;
import com.whayer.wx.gift.vo.GiftRelease;

@RequestMapping(value = "/gift")
@Controller
public class GiftController extends BaseController{
	private final static Logger log = LoggerFactory.getLogger(GiftController.class);
	
	@Resource
	private GiftService giftService;
	
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("GiftController.getList()");
		
		Box box = loadNewBox(request);
		//type 0/null:查询全部礼品 1:查询未过期礼品
		String expired = box.$p("expired");
		int isExpired = 0;
		if(!isNullOrEmpty(expired)){
			isExpired = X.string2int(expired);
		}
		
		PageInfo<Gift> pi = giftService.getGiftList(isExpired, box.getPagination());
		
		return pagerResponse(pi);
	}
	
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition findById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("GiftController.findById()");
		
		Box box = loadNewBox(request);
		
		String id = box.$p("id");
		
		if(isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		Gift gift = giftService.findById(id);
		ResponseCondition res = getResponse(X.TRUE);
		List<Gift> list = new ArrayList<>();
		list.add(gift);
		res.setList(list);
		return res;
	}
	
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("GiftController.deleteById()");
		
		Box box = loadNewBox(request);
		
		String id = box.$p("id");
		
		if(isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		int count = giftService.deleteById(id);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("删除礼品失败");
			log.error("删除礼品失败");
			return res;
		}
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("GiftController.update()");
		
		Box box = loadNewBox(request);
		String id = box.$p("id");
		String name = box.$p("name");
		String detail = box.$p("detail");
		String deadline = box.$p("deadline");
		String imgSrc = box.$p("imgSrc");
		
		if(isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		Date deadlineDate = X.string2date(deadline, X.TIMEA);
		
		if(!isNullOrEmpty(deadlineDate) && deadlineDate.compareTo(new Date()) < 0){
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("截止日期不能小于当前日期");
			return res;
		}
		
		Gift gift = new Gift();
		gift.setId(id);
		gift.setName(name);
		gift.setDetail(detail);
		gift.setDeadline(deadlineDate);
		gift.setImgSrc(imgSrc);
		
		int count = giftService.update(gift);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("更新礼品失败");
			log.error("更新礼品失败");
			return res;
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition save(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("GiftController.save()");
		
		Box box = loadNewBox(request);
		
		String name = box.$p("name");
		String detail = box.$p("detail");
		String deadline = box.$p("deadline");
		String imgSrc = box.$p("imgSrc");
		
		if(isNullOrEmpty(name) || isNullOrEmpty(deadline)){
			return getResponse(X.FALSE);
		}
		
		String id = X.uuidPure();
		Date deadlineDate = X.string2date(deadline, X.TIMEA);
		
		if(isNullOrEmpty(deadlineDate)){
			return getResponse(X.FALSE);
		}
		if(deadlineDate.compareTo(new Date()) < 0){
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("截止日期不能小于当前日期");
			return res;
		}
		
		Gift gift = new Gift();
		gift.setId(id);
		gift.setName(name.trim());
		gift.setDetail(detail.trim());
		gift.setDeadline(deadlineDate);
		gift.setImgSrc(imgSrc);
		gift.setCreateTime(new Date());
		
		int count = giftService.save(gift);
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("保存礼品失败");
			log.error("保存礼品失败");
			return res;
		}
	}
	
	/**
	 * 保存礼品发放记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveGiftRelease", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition saveGiftRelease(HttpServletRequest request, HttpServletResponse response){
		
		log.info("GiftController.saveGiftRelease()");
		
		Box box = loadNewBox(request);
		
		String orderId = box.$p("orderId");
		String name = box.$p("name");
		String mobile = box.$p("mobile");
		String address = box.$p("address");
		
		if(isNullOrEmpty(orderId) || isNullOrEmpty(name) 
				|| isNullOrEmpty(mobile) || isNullOrEmpty(address)){
			return getResponse(X.FALSE);
		}
		
		//验证是否有此订单,同时验证此订单是否已有发放记录
		if(!giftService.validateGiftReleaseExist(orderId)){
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("订单不存在或已有发放记录");
			return res;
		}
		
		//查询发放礼品
		List<Gift> list = giftService.getGiftList(1);
		if(isNullOrEmpty(list)){
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("当前没有礼品");
			return res;
		}
		List<String> arr = new ArrayList<>();
		for(Gift gift : list){
			arr.add(gift.getId());
		}
		String giftId = StringUtils.collectionToDelimitedString(arr, ",");
		
		GiftRelease giftRelease = new GiftRelease();
		giftRelease.setId(X.uuidPure());
		giftRelease.setGiftId(giftId);
		giftRelease.setMailed(X.FALSE);
		giftRelease.setName(name);
		giftRelease.setAddress(address);
		giftRelease.setMobile(mobile);
		giftRelease.setOrderId(orderId);
		
		int count = giftService.saveGiftRelease(giftRelease);
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("保存礼品发放记录失败");
			log.error("保存礼品发放记录失败");
			return res;
		}
	}
	
	/**
	 * 更新礼品记录为已发放
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateGiftRelease", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition updateGiftRelease(HttpServletRequest request, HttpServletResponse response){
		
		log.info("GiftController.updateGiftRelease()");
		
		Box box = loadNewBox(request);
		
		//礼品发放记录id
		String id = box.$p("id");
		if(isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		int count = giftService.updateGiftRelease(id, 1);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("更新礼品发放记录失败");
			log.error("更新礼品发放记录失败");
			return res;
		}
	}
}
