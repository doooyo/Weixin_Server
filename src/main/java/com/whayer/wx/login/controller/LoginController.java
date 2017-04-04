package com.whayer.wx.login.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whayer.wx.common.X;
import com.whayer.wx.common.encrypt.MD5;
import com.whayer.wx.common.mvc.BaseVerificationController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.common.page.HtmlParser;
import com.whayer.wx.login.service.UserService;
import com.whayer.wx.login.vo.SkUser;

/**
 * 登陆与验证
 * @author duyu
 * @since  20-03-17
 */

@Controller
public class LoginController extends BaseVerificationController {

	private final static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name = "userService")      //byName装配,并指定别名; 默认首字母转小写其他不变的规则
	//@Resource(type = UserService.class)//byType装配
	private UserService userService;

	private ResponseCondition getErrorBean(ResponseCondition res, Integer errorCode) {
		res.setErrorCode(errorCode);
		res.setHttpCode(500);
		res.setIsSuccess(false);
		return res;
	}
	
	@RequestMapping(value = "/login/verify", method = RequestMethod.GET)
	public void getVerifyCodeImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Box box = loadNewBox(request);
		response.setHeader("Progma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		outputVerification(box, response);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Box box = loadNewBox(request);
		ResponseCondition res = new ResponseCondition();
		if (!X.POST.equalsIgnoreCase(request.getMethod())) {
			return getErrorBean(res, null);
		}
		String userName = box.$p(X.USER_NAME);
		String passWord = box.$p(X.PASSWORD);
		// 无需验证 验证码
		//String cookie = box.$c(X.ENCRYPTED + X.KEY).getValue();
		// String verifyCode = box.$p("verifycode");
		userName = (userName == null || userName.isEmpty()) ? "" : userName.trim();
		passWord = (passWord == null || passWord.isEmpty()) ? "" : MD5.md5Encode(passWord.trim());
		/**
		 * 123456 : 49ba59abbe56e057                 (16 bit)
		 *        : e10adc3949ba59abbe56e057f20f883e (32 bit)
		 */
		
		/**
		 * 20-03-17 duyu
		 * 1.若有验证码: 
		 *   验证码携带三个cookie {key: X.ENCRYPTED+X.KEY , value: '验证码的值'} 
		 *                     {key: X.ENCRYPTED+X.TIME, value: '当前时间戳'} 用于验证验证码是否过期
		 *                     {key: X.ENCRYPTED+"hash", value: MD5.md5Encode(MD5.md5Encode(key.toLowerCase()+X.USER_PASS_PREFIX)+X.USER_PASS_PREFIX+time)}
		 *                     //验证hash签名,避免暴力注册与登陆
		 *   预定义错误码 enum:
		 *                     0:正常;1：用户名账号或密码不能为空;2:没有此用户;3:密码错误;4:验证码过期;5.验证码错误;6:非法登录
		 * 2.登陆成功:
		 *   设置三个会话 cookie: {key: X.USERID   ,  value: '用户ID'}
		 *                     {key: X.USER_TYPE,  value: '用户类型'}
		 *                     {key: X.SESSION_ID, value: MD5.md5Encode(X.uuid())}
		 *   设置三个会话 session:{key: X.USER   ,  value: 用户对象SKUser}                //当前用户
		 *                     {key: X.SESSION_ID,  value: MD5.md5Encode(X.uuid())}   //拦截器登陆验证
		 *                     //若需支持分布式,需将session移至第三方Redis服务
		 *                     
		 */

		int errorCode = 0;
		if (userName.isEmpty() || userName.isEmpty()) {
			log.info("账号密码不能为空");
			errorCode = 1;
			return getErrorBean(res, errorCode);
		} else {
			SkUser u = new SkUser();
			u.setUsername(userName);
			u.setPassword(passWord);
			SkUser user = userService.findUserByName(userName);
			if (null == user) {
				log.info("没有此用户");
				errorCode = 2;
				return getErrorBean(res, errorCode);
			} else {
				user = userService.findUser(u);
				if (null == user) {
					log.info("密码错误");
					errorCode = 3;
					return getErrorBean(res, errorCode);
				} else {
					// TODO update login time /IP /last_session
					String ip = HtmlParser.GetClientIp(request);
		            String sessionid = MD5.md5Encode(X.uuid());

					Cookie uidc = new Cookie(X.USERID, user.getId().toString());
					uidc.setMaxAge(60 * 60 * 6);
					box.getCookie().put(X.USERID, uidc);
					Cookie userType = new Cookie(X.USER_TYPE, user.getIsAgent().toString());
					userType.setMaxAge(60 * 60 * 6);
					box.getCookie().put(X.USER_TYPE, userType);
					Cookie sessioncookie = new Cookie(X.SESSION_ID, user.getId().toString());
					sessioncookie.setMaxAge(60 * 60 * 6);
					box.getCookie().put(X.SESSION_ID, sessioncookie);
					
					writeCookies(box, response);

					request.getSession().setAttribute(X.USER, user);
					request.getSession().setAttribute(X.SESSION_ID, sessionid);
					//request.getSession().setAttribute(X.USER, user.getId().toString());
					//request.getSession().setAttribute(X.USER_TYPE, user.getIsAgent().toString());
					//request.getSession().setAttribute(X.USER_NAME, user.getUsername());

					return res;
				}
			}
		}
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/logout")
	public ResponseCondition signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Box box = loadNewBox(request);
		Cookie cu = new Cookie(X.USER, null);
		Cookie ct = new Cookie(X.USER_TYPE, null);
		cu.setMaxAge(0);
		ct.setMaxAge(0);
		box.getCookie().put(X.USER, cu);
		box.getCookie().put(X.USER_TYPE, ct);
		writeCookies(box, response);
		request.getSession().setAttribute(X.USER, null);
		request.getSession().setAttribute(X.USER_TYPE, null);
		request.getSession().setAttribute(X.USER_NAME, null);
		return new ResponseCondition();
	}

}
