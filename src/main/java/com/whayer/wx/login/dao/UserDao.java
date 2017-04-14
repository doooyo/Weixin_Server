package com.whayer.wx.login.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.login.vo.User;

@Repository
public interface UserDao extends DAO{
	
	public User findUserByName(@Param("username") String userName);
	
	public User findUser(User user);
	
	public int updateUserById(User user);
	
	/**
	 * username作为账号在数据库具有唯一性
	 * @param userName
	 * @return
	 */
	public User isUserNameExist(@Param("username") String userName);
	
	/**
	 * mobile作为账号在数据库具有唯一性
	 * @param mobile
	 * @return
	 */
	public User isMobileExist(@Param("mobile") String mobile);
	
	public Integer saveUser(User user);
	
	public List<User> getUserListByType(@Param("type") Integer type);
	
	/**
	 * 批量审核账号
	 * @param ids
	 * @return
	 */
	public int approveAuditBatch(@Param("ids") String... ids);
	
	/**
	 * 批量审核区域代理
	 * @param ids
	 * @return
	 */
	public int approveAgentBatch(@Param("ids") String... ids);
}
