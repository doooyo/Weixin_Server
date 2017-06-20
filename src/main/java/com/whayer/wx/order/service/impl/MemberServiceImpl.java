package com.whayer.wx.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.order.dao.MemberDao;
import com.whayer.wx.order.service.MemberService;
import com.whayer.wx.order.vo.Member;


@Service
public class MemberServiceImpl implements MemberService{
	
	@Resource
	private MemberDao memberDao;

	@Override
	public PageInfo<Member> getMemberList(Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Member> list =  memberDao.getMemberList();
		PageInfo<Member> pageInfo = new PageInfo<Member>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	@Override
	public Integer saveMember(Member member) {
		return memberDao.saveMember(member);
	}

	@Override
	public Boolean isAccountExist(String account) {
		int result = memberDao.isAccountExist(account);
		return result > 0 ? true : false;
	}

}
