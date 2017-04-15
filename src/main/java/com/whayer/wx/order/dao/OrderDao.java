package com.whayer.wx.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.order.vo.Order;

@Repository
public interface OrderDao extends DAO{
	
	/**
	 * 获取订单(若无用户id则查询所有订单)
	 * @param uid  
	 * @return
	 */
	public List<Order> getOrderList(@Param("uid") String uid);
	
	/**
	 * 生成订单
	 * @param order
	 * @return
	 */
	public int save(Order order);
	
	/**
	 * 取消订单状态
	 * @param id   订单id
	 * @param state 订单指定状态
	 * @return
	 */
	public int updateOrderStatusById(@Param("id") String id, @Param("state") Integer state);
	
	/**
	 * 将订单产品id存入中间表
	 * @param orderId
	 * @param pids
	 * @return
	 */
	public int saveOrder2Product(@Param("orderId") String orderId, @Param("pids") String... pids);
	
	/**
	 * 将订单的代金劵id存入中间表
	 * @param orderId
	 * @param vids
	 * @return
	 */
	public int saveOrder2Voucher(@Param("orderId") String orderId, @Param("vids") String... vids);
	
	/**
	 * 通过订单id获取指定的订单详情
	 * @param pid
	 * @return
	 */
	public Order getOrderById(@Param("id") String id);
}
