package com.whayer.wx.login.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whayer.wx.common.X;
import com.whayer.wx.common.encrypt.MD5;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.login.service.UserService;
import com.whayer.wx.login.vo.SkUser;

@Controller
public class LoginController extends BaseController {

	private final static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name = "userService") //按byName装配,并指定别名; 默认其实就是首字母转小写其他不变的规则
	//@Resource(type = UserService.class)//按byType装配
	private UserService userService;

	private ResponseCondition getErrorBean(ResponseCondition res, Integer errorCode) {
		res.setErrorCode(errorCode);
		res.setHttpCode(500);
		res.setIsSuccess(false);
		return res;
	}

	@RequestMapping(value = "/login")
	@ResponseBody
	public ResponseCondition login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Box box = loadNewBox(request);
		ResponseCondition res = new ResponseCondition();
		if (!"POST".equalsIgnoreCase(request.getMethod())) {
			return getErrorBean(res, null);
		}
		String userName = box.$p("username");
		String passWord = X.USER_PASS_PREFIX + box.$p("password");
		// 无需验证 验证码
		// String verifyCode = box.$p("verifycode");
		userName = (userName == null || userName.isEmpty()) ? "" : userName.trim();
		passWord = (passWord == null || passWord.isEmpty()) ? "" : MD5.md5Encode(passWord.trim());

		// TODO 预定义错误码 enum
		// 0:正常;1：用户名账号或密码不能为空;2:没有此用户;3:密码错误;4:验证码过期;5.验证码错误;6:非法登录
		int errorCode = 0;
		if (userName.isEmpty() || userName.isEmpty()) {
			log.info("登陆失败--账号密码不能为空");
			errorCode = 1;
			return getErrorBean(res, errorCode);
		} else {
			SkUser u = new SkUser();
			u.setUsername(userName);
			u.setPassword(passWord);
			SkUser user = userService.findUserByName(u);
			if (user == null) {
				errorCode = 2;
				return getErrorBean(res, errorCode);
			} else {
				// TODO 验证码过期验证
				// Cookie[] cookies = request.getCookies();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Cookie cookie = box.$c(X.ENCRYPTED + X.TIME);
				long diff = 1000000;
				try {
					diff = format.parse(X.nowString()).getTime() - format.parse(cookie.getValue()).getTime();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (diff / 1000 > Integer.parseInt(X.getTimeout())) {
					errorCode = 4;
					return getErrorBean(res, errorCode);
				}

				user = userService.findUser(u);
				if (user == null) {
					errorCode = 3;
					return getErrorBean(res, errorCode);
				} else {
					// TODO update login time /IP /last_session

					// 登录成功设置session 与 cookie
					Cookie c = new Cookie(X.USER, user.getId().toString());
					c.setMaxAge(-1);
					box.getCookie().put(X.USER, c);
					Cookie userType = new Cookie(X.USER_TYPE, user.getIsAgent().toString());
					userType.setMaxAge(-1);
					box.getCookie().put(X.USER_TYPE, userType);
					writeCookies(box, response);

					// request.getSession().setAttribute(X.USER, user);
					request.getSession().setAttribute(X.USER, user.getId().toString());
					request.getSession().setAttribute(X.USER_TYPE, user.getIsAgent().toString());
					request.getSession().setAttribute(X.USER_NAME, user.getUsername());

					// TODO use for login unique
					String sessionid = MD5.md5Encode(X.uuid());
					request.getSession().setAttribute(X.SESSION_ID, sessionid);

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
