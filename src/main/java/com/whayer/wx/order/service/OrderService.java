package com.whayer.wx.order.service;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.order.vo.Order;
import com.whayer.wx.order.vo.OrderStatistics;

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
	
	/**
	 * 个人订单统计信息
	 * @param userId
	 * @return
	 */
	public OrderStatistics getOrderStatisticsByUid(String userId);
	
	/**
	 * 个人订单条件查询
	 * @param type      -1:所有订单(包括已取消) 0:未支付 1:未绑定检测盒 2:未结算 3:已结算
	 * @param userId    用户id
	 * @param beginTime 开始时间
	 * @param endTime   结束时间
	 * @return
	 */
	public List<Order> getListByType(String type, String userId, Date beginTime, Date endTime);
	
	/**
	 * 订单绑定检测盒
	 * @param orderId
	 * @param detectionboxId
	 * @return
	 */
	public int saveOrder2Box(String orderId, String detectionboxId);
}
