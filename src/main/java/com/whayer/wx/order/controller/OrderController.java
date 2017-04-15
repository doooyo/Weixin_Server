package com.whayer.wx.order.controller;

import java.io.IOException;

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

@RequestMapping(value = "/order")
@Controller
public class OrderController extends BaseController{
	private final static Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@Resource
	private OrderService orderService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition save(@RequestBody Order order, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("OrderController.save()");
		
		
		if(isNullOrEmpty(order) || order.getProductIdList().length() <= 0){
			return getResponse(X.FALSE);
		}
		
		orderService.save(order);
		
		return getResponse(X.TRUE);
	}
	
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("OrderController.getList()");
		
		Box box = loadNewBox(request);
		
		String id = box.$p("id");
		//如果id为空,查询所有订单
		//如果id不为空, 查询当前用户id的订单
		PageInfo<Order> pi = orderService.getOrderList(id, box.getPagination());
		
		return pagerResponse(pi);
	}
	
	@RequestMapping(value = "/cancle", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition cancle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("OrderController.cancle()");
		
		Box box = loadNewBox(request);
		
		String id = box.$p("id");
		if(isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		orderService.cancelOrder(id);
		
		return getResponse(X.TRUE);
	}
}
