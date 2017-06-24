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
	
	public List<User> getUserListByType(
			@Param("isAuditType") Integer isAuditType, 
			@Param("nickName") String nickName);
	
	public User findUserByPid(@Param("pid") String pid);
	
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
	
	/**
	 * 代理商获取下线
	 * @param userId
	 * @return
	 */
	public List<User> getTeams(@Param("userId") String userId);
	
	/**
	 * 验证父级是否存在
	 * @param pid 电话/ID
	 * @return
	 */
	public int validatePid(@Param("pid") String pid);
	
	/**
	 * 删除代理商
	 * @param id
	 * @return
	 */
	public int deleteUserById(@Param("id") String id);
	
	/**
	 * 更新用户积分
	 * @param points
	 * @return
	 */
	public int updatePoints(@Param("userId") String userId, @Param("points") Integer points);
}
