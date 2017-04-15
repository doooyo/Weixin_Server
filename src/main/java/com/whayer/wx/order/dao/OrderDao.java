package com.whayer.wx.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.order.vo.Order;

@Repository
public interface OrderDao extends DAO{
	
	public List<Order> getOrderList(@Param("id") String id);
	
	public int save(Order order);
	
	public int updateOrderStatusById(@Param("id") String id, @Param("state") Integer state);
	
	public int saveOrder2Product(@Param("orderId") String orderId, @Param("pids") String... pids);
	
	public int saveOrder2Voucher(@Param("orderId") String orderId, @Param("vids") String... vids);
}
