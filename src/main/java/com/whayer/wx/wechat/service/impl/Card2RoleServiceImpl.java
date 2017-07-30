package com.whayer.wx.wechat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.wechat.dao.Card2RoleDao;
import com.whayer.wx.wechat.service.Card2RoleService;
import com.whayer.wx.wechat.vo.CardInfo;
import com.whayer.wx.wechat.vo.CardInfoIsSelected;

@Service
public class Card2RoleServiceImpl implements Card2RoleService{

	@Resource
	private Card2RoleDao card2RoleDao;
	
	@Override
	public PageInfo<CardInfo> getCardList(String title, Pagination pagination) {
		
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<CardInfo> list =  card2RoleDao.getCardList(title);
		PageInfo<CardInfo> pageInfo = new PageInfo<CardInfo>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	@Override
	public boolean card2role(String role, String[] ids) {
		int i = 0, j = 0;
		i = card2RoleDao.deleteAllByRole(role);
		if(i > 0){
			j = card2RoleDao.card2role(role, ids);
		}
		
		return j > 0;
	}

	@Override
	public List<CardInfoIsSelected> getCardList2Role(String role) {
		return card2RoleDao.getCardList2Role(role);
	}

}
