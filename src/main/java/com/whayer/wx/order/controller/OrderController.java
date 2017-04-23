package com.whayer.wx.order.controller;

import java.io.IOException;
import java.util.ArrayList;
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
		
		orderService.cancelOrder(id);
		
		return getResponse(X.TRUE);
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
		
		orderService.getOrderDetailById(id);
		
		return getResponse(X.TRUE);
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
}
