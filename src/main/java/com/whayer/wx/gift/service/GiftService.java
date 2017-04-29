package com.whayer.wx.gift.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.gift.vo.Gift;
import com.whayer.wx.gift.vo.GiftRelease;

public interface GiftService {
	
	public PageInfo<Gift> getGiftList(int isExpired, Pagination pagination);
	
	public List<Gift> getGiftList(int isExpired);
	
	public Gift findById(String id);
	
	public int update(Gift gift);
	
	public int deleteById(String id);
	
	public int save(Gift gift);
	
	public int saveGiftRelease(GiftRelease giftRelease);
	
	/**
	 * 更新发放记录为以邮寄，同时更新发放时间
	 * @param isMailed
	 * @return
	 */
	public int updateGiftRelease(String id, int isMailed);
	
	/**
	 * 验证订单存在性,是否已拥有发放记录
	 * @param orderId
	 * @return
	 */
	public boolean validateGiftReleaseExist(String orderId);
}
