package com.whayer.wx.order.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.order.service.OrderService;
import com.whayer.wx.order.vo.Order;
import com.whayer.wx.order.vo.OrderStatistics;

@RequestMapping(value = "/order")
@Controller
public class OrderController extends BaseController{
	private final static Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@Resource
	private OrderService orderService;
	
	/**
	 * 提交订单
	 * @param order
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition save(@RequestBody Order order, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("OrderController.save()");
		
		
		if(isNullOrEmpty(order) || order.getProductIdList().length() <= 0){
			return getResponse(X.FALSE);
		}
		
		String orderId = orderService.save(order);
		
		ResponseCondition res = getResponse(X.TRUE);
		List<String> list = new ArrayList<>();
		list.add(orderId);
		res.setList(list);
		return res;
	}
	
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("OrderController.getList()");
		
		Box box = loadNewBox(request);
		
		String id = box.$p("userId");
		//如果id为空,查询所有订单
		//如果id不为空, 查询当前用户id的订单
		PageInfo<Order> pi = orderService.getOrderList(id, box.getPagination());
		
		return pagerResponse(pi);
	}
	
	/**
	 * 取消订单
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition cancel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("OrderController.cancel()");
		
		Box box = loadNewBox(request);
		//订单id
		String id = box.$p("id");
		if(isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		int count = orderService.cancelOrder(id);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("取消订单失败");
			log.error("取消订单失败");
			return res;
		}
		
	}
	
	/**
	 * 获取订单所有详情
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getDetailById", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getDetailById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("OrderController.getDetailById()");
		
		Box box = loadNewBox(request);
		//订单id
		String id = box.$p("id");
		if(isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		Order order = orderService.getOrderDetailById(id);
		
		ResponseCondition res = getResponse(X.TRUE);
		List<Order> list = new ArrayList<>();
		list.add(order);
		res.setList(list);
		return res;
	}
	
	/**
	 * 用户订单统计
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getOrderStatisticsByUid", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getOrderStatisticsByUid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("OrderController.getUserOrderStatistics()");
		Box box = loadNewBox(request);
		
		String userId = box.$p("userId");
		if(isNullOrEmpty(userId)){
			return getResponse(X.FALSE);
		}
		
		OrderStatistics result = orderService.getOrderStatisticsByUid(userId);
		
		ResponseCondition res = getResponse(X.TRUE);
		List<OrderStatistics> list = new ArrayList<OrderStatistics>();
		list.add(result);
		res.setList(list);
		return res;
	}
	
	/**
	 * 通过用户id查询指定状态的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getListByType", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getListByType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("OrderController.getListByType()");
		Box box = loadNewBox(request);
		
		/**
		 * -1:所有订单(包括已取消) 0:未支付 1:未绑定检测盒 2:未结算 3:已结算
		 */
		String type = box.$p("type");
		String userId = box.$p("userId");
		String beginTime = box.$p("beginTime");
		String endTime = box.$p("endTime");
		if(isNullOrEmpty(userId) || isNullOrEmpty(type) 
				|| isNullOrEmpty(beginTime) || isNullOrEmpty(endTime)){
			return getResponse(X.FALSE);
		}
		
		Date begin = X.string2date(beginTime + " 00:00:00", X.TIMEA);
		Date end = X.string2date(endTime + " 23:59:59", X.TIMEA);
		
		if(begin.compareTo(end) > 0){
			return getResponse(X.FALSE);
		}
		
		List<Order> list = orderService.getListByType(type, userId, begin, end);
		ResponseCondition res = getResponse(X.TRUE);
		res.setList(list);
		return res;
		
	}
	
	//http://git.oschina.net/free/Mybatis_PageHelper/issues/158
	//pageHelper不支持一对多,多对多分页,只能通过手动对主表分页
	@RequestMapping(value = "/getListByTypeV2", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getListByTypeV2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("OrderController.getListByTypeV2()");
		Box box = loadNewBox(request);
		
		/**
		 * -1:所有订单(包括已取消) 0:未支付 1:未绑定检测盒 2:未结算 3:已结算
		 */
		String type = box.$p("type");
		String userId = box.$p("userId");
		String beginTime = box.$p("beginTime");
		String endTime = box.$p("endTime");
		String nickname = box.$p("nickname");
		String examineeName = box.$p("examineeName");
		
		if(isNullOrEmpty(type)){
			return getResponse(X.FALSE);
		}
		
		if(!isNullOrEmpty(nickname)) nickname = nickname.trim();
		if(!isNullOrEmpty(examineeName)) examineeName = examineeName.trim();
		
		Date begin = X.string2date(beginTime + " 00:00:00", X.TIMEA);
		Date end = X.string2date(endTime + " 23:59:59", X.TIMEA);
		
		if(!isNullOrEmpty(begin) && !isNullOrEmpty(end) && begin.compareTo(end) > 0){
			return getResponse(X.FALSE);
		}
		
		//List<Order> list = orderService.getListByType(type, userId, begin, end);
		
		PageInfo<Order> pi = orderService.getListByTypeV2(type, userId, begin, end, nickname, examineeName, box.getPagination());
		return pagerResponse(pi);
	}
	
	/**
	 * 订单绑定检测盒
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveOrder2Box", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition saveOrder2Box(HttpServletRequest request, HttpServletResponse response) {
		log.info("OrderController.saveOrder2Box()");
		Box box = loadNewBox(request);
		
		String orderId = box.$p("orderId");
		String detectionboxId = box.$p("detectionboxId");
		
		if(isNullOrEmpty(orderId) || isNullOrEmpty(detectionboxId)){
			return getResponse(X.FALSE);
		}
		
		int count  = orderService.saveOrder2Box(orderId, detectionboxId);
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("订单绑定检测盒失败");
			log.error("订单绑定检测盒失败");
			return res;
		}
		
		
	}
}
