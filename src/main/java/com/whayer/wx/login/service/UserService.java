package com.whayer.wx.login.service;

import com.whayer.wx.login.vo.SkUser;

public interface UserService {

	/**
	 * 根据条件查询用户信息
	 * 
	 * @param user
	 * @return
	 */
	public SkUser findUser(SkUser user);

	/**
	 * 通过用户名查找用户
	 * 
	 * @param user
	 * @return
	 */
	public SkUser findUserByName(SkUser user);

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	public int updateUser(SkUser user);

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
	public boolean saveUser(SkUser user);

}
