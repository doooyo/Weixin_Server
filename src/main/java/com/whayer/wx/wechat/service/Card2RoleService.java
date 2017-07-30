package com.whayer.wx.wechat.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.wechat.vo.CardInfo;
import com.whayer.wx.wechat.vo.CardInfoIsSelected;

public interface Card2RoleService {
	/**
	 * 获取卡劵分页列表
	 * @param title
	 * @param pagination
	 * @return
	 */
	public PageInfo<CardInfo> getCardList(String title, Pagination pagination);
	
	/**
	 * 获取已打标的卡劵列表(关联角色)
	 * @return
	 */
	public List<CardInfoIsSelected> getCardList2Role(String role);
	
	/**
	 * 角色关联卡劵
	 * @param role
	 * @param ids 先删除该role的所有卡劵,再添加
	 * @return
	 */
	public boolean card2role(String role, String[] ids);
}
