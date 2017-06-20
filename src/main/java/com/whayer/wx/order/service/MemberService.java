package com.whayer.wx.order.service;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.order.vo.Member;

public interface MemberService {
	
	/**
	 * 获取会员列表
	 * @return
	 */
	public PageInfo<Member> getMemberList(Pagination pagination);
	
	/**
	 * 保存会员
	 * @param member
	 * @return
	 */
	public Integer saveMember(Member member);
	
	/**
	 * 检测会员账号是否存在
	 * @param account
	 * @return
	 */
	public Boolean isAccountExist(String account);
}
