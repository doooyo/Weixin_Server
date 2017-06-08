package com.whayer.wx.gift.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.gift.vo.Gift;
import com.whayer.wx.gift.vo.GiftRelease;

@Repository
public interface GiftDao extends DAO{

	public List<Gift> getGiftList(@Param("isExpired") int isExpired);
	
	public Gift findById(@Param("id") String id);
	
	public int update(Gift gift);
	
	public int deleteById(@Param("id") String id);
	
	public int save(Gift gift);
	
	public int saveGiftRelease(GiftRelease giftRelease);
	
	public int updateGiftRelease(@Param("id") String id, @Param("isMailed") int isMailed);
	
	public GiftRelease getGiftReleaseById(@Param("orderId") String orderId);
	
	public List<GiftRelease> getGiftReleaseList(@Param("isMailed") Integer isMailed, @Param("name") String name);
}
