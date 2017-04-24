package com.whayer.wx.order.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.whayer.wx.base.UnitTestBase;
import com.whayer.wx.common.X;
import com.whayer.wx.order.vo.Order;

public class OrderServiceTest extends UnitTestBase{

	public OrderServiceTest() {
		super("classpath:IOC.xml");
	}
	
	@Test
	public void testGetListByType() {
		OrderService orderService = super.getBean("orderServiceImpl");
		
		String userId = "be9e9b0c-39df-456b-9223-c03fe0f3e77c";
		Date beginTime = X.string2date("2017-04-13 00:00:00", X.TIMEA);
		Date endTime = X.string2date("2017-06-01 00:00:00", X.TIMEA);
		
		//-1:所有订单(包括已取消) 0:未支付 1:未绑定检测盒 2:未结算 3:已结算
		List<Order> all = orderService.getListByType("-1", userId, beginTime, endTime);  //22
		List<Order> list0 = orderService.getListByType("0", userId, beginTime, endTime); //19
		List<Order> list1 = orderService.getListByType("1", userId, beginTime, endTime); //0
		List<Order> list2 = orderService.getListByType("2", userId, beginTime, endTime); //0
		List<Order> list3 = orderService.getListByType("3", userId, beginTime, endTime); //0
		System.out.println("所有订单:"+all.size() + "\n未支付:"+list0.size()+"\n未绑定检测盒:"
		+list1.size()+"\n未结算:"+list2.size()+"\n已结算:"+list3.size());
	}

}
