package com.whayer.wx.wechat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.wechat.vo.CardInfo;
import com.whayer.wx.wechat.vo.CardInfoIsSelected;

@Repository
public interface Card2RoleDao extends DAO{
	
	List<CardInfo> getCardList(@Param("title") String title);
	
	Integer deleteAllByRole(@Param("role") String role);
	
	Integer card2role(@Param("role") String role, @Param("ids") String... ids);
	
	public List<CardInfoIsSelected> getCardList2Role(@Param("role") String role);
}