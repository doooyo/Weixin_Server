package com.whayer.wx.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.order.vo.Member;


@Repository
public interface MemberDao extends DAO{
	
	
	public List<Member> getMemberList();
	
	public int saveMember(Member member);
	
	public int isAccountExist(@Param("account") String account);
}
