package com.whayer.wx.gift.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.gift.dao.GiftDao;
import com.whayer.wx.gift.service.GiftService;
import com.whayer.wx.gift.vo.Gift;
import com.whayer.wx.gift.vo.GiftRelease;
import com.whayer.wx.order.dao.OrderDao;
import com.whayer.wx.order.vo.Order;

@Service
public class GiftServiceImpl implements GiftService{

	@Resource
	private GiftDao giftDao;
	
	@Resource
	private OrderDao orderDao;
	
	@Override
	public PageInfo<Gift> getGiftList(int isExpired, Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Gift> list = giftDao.getGiftList(isExpired);
		PageInfo<Gift> pageInfo = new PageInfo<>(list, pagination.getNavigationSize());
		return pageInfo;
	}
	
	@Override
	public List<Gift> getGiftList(int isExpired){
		List<Gift> list = giftDao.getGiftList(isExpired);
		return list;
	}

	@Override
	public Gift findById(String id) {
		return giftDao.findById(id);
	}

	@Override
	public int update(Gift gift) {
		return giftDao.update(gift);
	}

	@Override
	public int deleteById(String id) {
		return giftDao.deleteById(id);
	}

	@Override
	public int save(Gift gift) {
		return giftDao.save(gift);
	}

	@Override
	public int saveGiftRelease(GiftRelease giftRelease) {
		
		return giftDao.saveGiftRelease(giftRelease);
	}

	
	@Override
	public int updateGiftRelease(String id, int isMailed) {
		return giftDao.updateGiftRelease(id, isMailed);
	}

	@Override
	public boolean validateGiftReleaseExist(String orderId) {
		
		Order order = orderDao.getOrderById(orderId);
		if(null == order){
			return X.FALSE;
		}
		
		GiftRelease giftRelease = giftDao.getGiftReleaseById(orderId);
		if(null == giftRelease){
			return X.TRUE;
		}else{
			return X.FALSE;
		}
	}

	@Override
	public PageInfo<GiftRelease> getGiftReleaseList(Integer isMailed, String name, Pagination pagination) {
		
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<GiftRelease> list = giftDao.getGiftReleaseList(isMailed, name);
		PageInfo<GiftRelease> pageInfo = new PageInfo<>(list, pagination.getNavigationSize());
		return pageInfo;
	}

}
