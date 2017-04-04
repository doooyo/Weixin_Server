package com.whayer.wx.login.dao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.login.vo.SkUser;

@Repository
public interface UserDao extends DAO{
	
	public SkUser findUserByName(@Param("username") String userName);
	
	
}
