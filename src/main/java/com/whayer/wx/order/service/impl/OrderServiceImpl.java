package com.whayer.wx.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.coupon.dao.CouponDao;
import com.whayer.wx.order.dao.ExamineeDao;
import com.whayer.wx.order.dao.OrderDao;
import com.whayer.wx.order.service.OrderService;
import com.whayer.wx.order.vo.Examinee;
import com.whayer.wx.order.vo.Order;
import com.whayer.wx.vouchers.dao.VoucherDao;

@Service
public class OrderServiceImpl implements OrderService{

	@Resource
	private OrderDao orderDao;
	
	@Resource
	private ExamineeDao examineeDao;
	
	@Resource 
	private CouponDao couponDao;
	
	@Resource
	private VoucherDao voucherDao;
	
	@Override
	public PageInfo<Order> getOrderList(String id, Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Order> list =  orderDao.getOrderList(id);
		PageInfo<Order> pageInfo = new PageInfo<Order>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	@Override
	public int save(Order order) {
		/**
		 * 1.保存体检人(设置id)
		 * 2.拿到体检人id并保存订单(设置订单id)
		 * 3.保存产品id到sk_order_product(id/order_id/product_id)中间表
		 * 4.若有代金劵,保存代金劵id到 sk_order_voucher(id/order_id/voucher_id)中间表
		 * 5.设置代金劵与优惠卷的is_effect/use_date 
		 */
		//保存体检人
		String examineeId = X.uuidPure();
		Examinee examinee = order.getExaminee();
		examinee.setId(examineeId);
		int e = examineeDao.saveExaminee(examinee);
		
		//保存订单
		order.setId(X.uuidPure());
		order.setExamineeId(examineeId);
		int o = orderDao.save(order);
		
		//保存产品id到中间表
		String[] pids = order.getProductIdList().split(",");
		int op = 0;
		if(pids.length > 0){
			op = orderDao.saveOrder2Product(order.getId(), pids);
		}
		
		//若有代金劵,保存代金劵id至中间表
		String[] vids = order.getVouchersId().split(",");
		int vp = 0;
		if(vids.length > 0){
			vp = orderDao.saveOrder2Voucher(order.getId(), vids);
		}
		
		//更新代金劵与优惠卷的状态与使用日期
		String couponId = order.getCouponId();
		int vus = 0, cus = 0;
		if(null != couponId){
			String[] ids = {couponId};
			cus = couponDao.updateStateByIds(ids);
		}
		if(vids.length > 0){
			vus = voucherDao.updateStateByIds(vids);
		}
		System.out.println(e + o + op + vp + cus + vus);
		return e + o + op + vp + cus + vus; //6次数据库操作,AOP保证事物
	}

	@Override
	public int cancelOrder(String id) {
		//state 0:未付款, 1:已付款 2:已结算 3:已取消
		return orderDao.updateOrderStatusById(id, 3);
	}

}
