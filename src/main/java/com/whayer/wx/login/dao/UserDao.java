package com.whayer.wx.login.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.login.vo.SkUser;

@Repository
public interface UserDao extends DAO{
	
	public SkUser findUserByName(@Param("username") String userName);
	
	public SkUser findUser(SkUser user);
	
	public int updateUserById(SkUser user);
	
	/**
	 * username作为账号在数据库具有唯一性
	 * @param userName
	 * @return
	 */
	public SkUser isUserNameExist(@Param("username") String userName);
	
	/**
	 * mobile作为账号在数据库具有唯一性
	 * @param mobile
	 * @return
	 */
	public SkUser isMobileExist(@Param("mobile") String mobile);
	
	public Integer saveUser(SkUser user);
	
	public List<SkUser> getUserListByType(@Param("type") Integer type);
}
