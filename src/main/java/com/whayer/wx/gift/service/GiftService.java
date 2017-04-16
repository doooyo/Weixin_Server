package com.whayer.wx.gift.service;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.gift.vo.Gift;

public interface GiftService {
	
	public PageInfo<Gift> getGiftList(int isExpired, Pagination pagination);
	
	public Gift findById(String id);
	
	public int update(Gift gift);
	
	public int deleteById(String id);
	
	public int save(Gift gift);
}
