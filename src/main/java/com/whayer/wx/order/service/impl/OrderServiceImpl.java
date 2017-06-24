package com.whayer.wx.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.coupon.dao.CouponDao;
import com.whayer.wx.login.dao.UserDao;
import com.whayer.wx.order.dao.ExamineeDao;
import com.whayer.wx.order.dao.MemberDao;
import com.whayer.wx.order.dao.OrderDao;
import com.whayer.wx.order.service.OrderService;
import com.whayer.wx.order.vo.Examinee;
import com.whayer.wx.order.vo.Member;
import com.whayer.wx.order.vo.Order;
import com.whayer.wx.order.vo.OrderStatistics;
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
	
	@Resource
	private MemberDao memberDao;
	
	@Resource 
	private UserDao userDao;
	
	@Override
	public PageInfo<Order> getOrderList(String uid, Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Order> list =  orderDao.getOrderList(uid);
		PageInfo<Order> pageInfo = new PageInfo<Order>(list, pagination.getNavigationSize());
		return pageInfo;
	}
	
	@Override
	public List<Order> getOrderList(String uid) {
		List<Order> list =  orderDao.getOrderList(uid);
		return list;
	}

	@Override
	public String save(Order order) {
		/**
		 * 1.保存体检人(设置id)
		 * 2.拿到体检人id并保存订单(设置订单id)
		 * 3.保存产品id到sk_order_product(id/order_id/product_id)中间表
		 * 4.保存体检人到会员表,密码是手机号末6位,账号是体检人手机号
		 * 5.若有代金劵,保存代金劵id到 sk_order_voucher(id/order_id/voucher_id)中间表
		 * 6.设置代金劵与优惠卷的is_effect/use_date 
		 */
		//保存体检人
		String examineeId = X.uuidPure();
		Examinee examinee = order.getExaminee();
		examinee.setId(examineeId);
		int e = examineeDao.saveExaminee(examinee);
		
		//保存订单
		String orderId = X.randomOrderId(); //X.uuidPure();
		order.setId(orderId);
		order.setExamineeId(examineeId);
		int o = orderDao.save(order);
		
		//保存产品id到中间表
		String[] pids = order.getProductIdList().split(",");
		int op = 0;
		if(pids.length > 0){
			op = orderDao.saveOrder2Product(order.getId(), pids);
		}
		
		//保存体检人到会员表
		String account = examinee.getMobile();
		
		int m = 0;
		int len = memberDao.isAccountExist(account);
		if(len <= 0){
			Member member = new Member();
			member.setId(X.uuidPure());
			member.setAccount(account);
			member.setPassword(account.substring(account.length() - 6, account.length()));
			m = memberDao.saveMember(member);
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
		System.out.println(e + o + op + vp + cus + vus + m); //7次数据库操作,AOP保证事物
		
		return orderId; 
	}

	@Override
	public int cancelOrder(String id) {
		//state 0:未付款, 1:已付款 2:已结算 3:已取消
		
		Order order = orderDao.getOrderById(id);
		String cIds = order.getCouponId();
		String vIds = order.getVouchersId();
		
		//同时将代金劵 与 优惠卷 的状态进行重置
		if(cIds.length() > 0){
			String[] ids = cIds.split(",");
			couponDao.updateStateRollBackById(ids);
		} 
		
		if(vIds.length() > 0){
			String[] ids = vIds.split(",");
			voucherDao.updateStateRollBackById(ids);
		}
		
		//取消订单
		orderDao.updateOrderStatusById(id, X.OrderState.CANCELED.getState());
		
		return 1;
		
	}
	
	@Override
	public Order getOrderById(String id) {
		
		return orderDao.getOrderById(id);
	}

	/**
	 * TODO 获取订单相关所有数据
	 */
	@Override
	public Order getOrderDetailById(String id) {
		
		return orderDao.getOrderDetailById(id);
	}

	@Override
	public OrderStatistics getOrderStatisticsByUid(String userId) {
		
		return orderDao.getOrderStatisticsByUid(userId);
	}

	@Override
	public List<Order> getListByType(String type, String userId, Date beginTime, Date endTime) {
		return orderDao.getListByType(type, userId, beginTime, endTime);
	}

	@Override
	public int saveOrder2Box(String orderId, String detectionboxId) {
		return orderDao.saveOrder2Box(orderId, detectionboxId);
	}

	@Override
	public int updateOrderStatusById(String orderId, int state) {
		return orderDao.updateOrderStatusById(orderId, state);
	}

	@Override
	public PageInfo<Order> getListByTypeV2(
			String type, 
			String userId, 
			Date beginTime, 
			Date endTime, 
			String nickname,
			String examineeName, 
			Pagination pagination) {

		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Order> list =  orderDao.getListByTypeV2(type, userId, beginTime, endTime, nickname, examineeName);
		PageInfo<Order> pageInfo = new PageInfo<Order>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	@Override
	public int updateOrderStatusByIdV2(String orderId, X.OrderState state, int points, String userId) {
		int i = updateOrderStatusById(orderId, state.getState());
		int j = userDao.updatePoints(userId, points);
		return i + j;
	}

}
