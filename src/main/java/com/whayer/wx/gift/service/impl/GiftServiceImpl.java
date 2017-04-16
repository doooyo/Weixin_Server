package com.whayer.wx.gift.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.gift.dao.GiftDao;
import com.whayer.wx.gift.service.GiftService;
import com.whayer.wx.gift.vo.Gift;

@Service
public class GiftServiceImpl implements GiftService{

	@Resource
	private GiftDao giftDao;
	
	
	@Override
	public PageInfo<Gift> getGiftList(int isExpired, Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Gift> list = giftDao.getGiftList(isExpired);
		PageInfo<Gift> pageInfo = new PageInfo<>(list, pagination.getNavigationSize());
		return pageInfo;
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

}
