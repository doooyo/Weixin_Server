package com.whayer.wx.login.service;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.login.vo.User;

public interface UserService {
	
	/**
	 * 根据用户类型查找用户列表
	 * @param isAuditType 是否通过审核
	 * @param nickName    昵称模糊查询
	 * @param pagination
	 * @return
	 */
	public PageInfo<User> getUserListByType(Integer isAuditType, String nickName,  Pagination pagination);

	/**
	 * 根据条件查询用户信息
	 * 
	 * @param user
	 * @return
	 */
	public User findUser(User user);

	/**
	 * 通过用户名查找用户
	 * 
	 * @param user
	 * @return
	 */
	public User findUserByName(String userName);

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	public int updateUserById(User user);

	/**
	 * 检测用户名是否存在
	 * 
	 * @param userName
	 * @return 存在返回true
	 */
	public boolean isUserNameExist(String userName);

	/**
	 * 验证手机号码是否存在
	 * 
	 * @param mobile
	 * @return
	 */
	public boolean isMobileExist(String mobile);

	/**
	 * 保存用户
	 * 
	 * @param user
	 * @return
	 */
	public int saveUser(User user);
	
	/**
	 * 批量审批注册用户
	 * @param ids
	 * @return
	 */
	public int approveAuditBatch(String... ids);

	/**
	 * 批量审批区域代理
	 * @param ids
	 * @return
	 */
	public int approveAgentBatch(String... ids);
	
	/**
	 * 获取代理商下线
	 * @param userId
	 * @param pagination
	 * @return
	 */
	public PageInfo<User> getTeams(String userId, Pagination pagination);
	
	/**
	 * 验证父级是否存在
	 * @param pid 电话/ID
	 * @return
	 */
	public boolean validatePid(String pid);
	
	/**
	 * 删除代理商
	 * @param id
	 * @return
	 */
	public int deleteUserById(String id);
}
