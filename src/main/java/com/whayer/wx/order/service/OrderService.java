package com.whayer.wx.order.service;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.order.vo.Order;

public interface OrderService {
	
	/**
	 * 获取订单列表,若有id则查询当前用户的订单
	 * @param id
	 * @param pagination
	 * @return
	 */
	public PageInfo<Order> getOrderList(String id, Pagination pagination);
	
	/**
	 * 保存订单
	 * @param order
	 * @return
	 */
	public int save(Order order);
	
	/**
	 * 取消订单
	 * @param id
	 * @return
	 */
	public int cancelOrder(String id);
	
}
