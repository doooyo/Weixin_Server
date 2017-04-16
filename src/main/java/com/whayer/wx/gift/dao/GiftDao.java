package com.whayer.wx.gift.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.gift.vo.Gift;

@Repository
public interface GiftDao extends DAO{

	public List<Gift> getGiftList(@Param("isExpired") int isExpired);
	
	public Gift findById(@Param("id") String id);
	
	public int update(Gift gift);
	
	public int deleteById(@Param("id") String id);
	
	public int save(Gift gift);
}
