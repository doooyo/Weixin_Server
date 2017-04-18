package com.whayer.wx.order.service;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.order.vo.Order;

public interface OrderService {
	
	/**
	 * 获取订单列表,若有id则查询当前用户的订单
	 * @param uid
	 * @param pagination
	 * @return
	 */
	public PageInfo<Order> getOrderList(String uid, Pagination pagination);
	
	/**
	 * 获取订单详情
	 * @param id
	 * @return
	 */
	public Order getOrderDetailById(String id);
	
	/**
	 * 获取订单初略数据
	 * @param id
	 * @return
	 */
	public Order getOrderById(String id);
	
	/**
	 * 保存订单
	 * @param order
	 * @return
	 */
	public String save(Order order);
	
	/**
	 * 取消订单
	 * @param id
	 * @return
	 */
	public int cancelOrder(String id);
	
}
