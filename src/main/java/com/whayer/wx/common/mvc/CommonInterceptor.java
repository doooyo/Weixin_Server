package com.whayer.wx.common.mvc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.whayer.wx.common.X;
import com.whayer.wx.common.encrypt.AES;
import com.whayer.wx.login.vo.User;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 拦截器
 * @author duyu
 * @since  15-03-17
 */

public class CommonInterceptor extends HandlerInterceptorAdapter {
	//由于没有设置扫描路径,最好使用配置文件
	protected AES aes;
	private final PathMatcher pathMatcher = new AntPathMatcher();

	private String[] excludeUrlPatterns = null;
	private final static Logger log = LoggerFactory.getLogger(CommonInterceptor.class);

	public void setExcludeUrlPatterns(final String[] excludeUrlPatterns) {
		this.excludeUrlPatterns = excludeUrlPatterns;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("CommonInterceptor.preHandle()");
		
		String ua = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(ua);
		log.info("UA:" + ua);
		log.info("客户端:" +userAgent.getOperatingSystem() + ",浏览器:" + userAgent.getBrowser());
		
		if(ua.contains("MicroMessenger")){
			return true;
		}
		
		if (isExclude(request)) {
			return true;
		}
		String reqUrl = request.getRequestURI();
		if (reqUrl.contains("/verify")) {
			return true;
		}
		// 验证登录信息,分别从cookie和session取出并进行匹配
		Box box = new Box();
		loadCookie(request, box);
		String sessioncookieid = box.$cv(X.ENCRYPTED + X.SESSION_ID);
		String userid = box.$cv(X.ENCRYPTED + X.USERID);
		if(null == sessioncookieid || null == userid){
			return false;
		}
		
		sessioncookieid = aes.decrypt(sessioncookieid);//aes.decrypt(box.$c(X.ENCRYPTED + X.SESSION_ID).getValue()); 
		userid = aes.decrypt(userid); //aes.decrypt(box.$c(X.ENCRYPTED + X.USERID).getValue()); 
		String sessionid = (String)request.getSession().getAttribute(X.SESSION_ID);
		User user = (User)request.getSession().getAttribute(X.USER);

		if (null != user && sessioncookieid.equals(sessionid) && userid.equals(user.getId())) {
			return true;
		} else {
			response.sendRedirect("/login");
			//request.getRequestDispatcher("/login").forward(request, response); //只提供RESTful接口,不提供后台跳转
			return false;
		}
		
	}

	private boolean isExclude(final HttpServletRequest request) {
		if (excludeUrlPatterns == null) {
			return false;
		}
		for (String pattern : excludeUrlPatterns) {
			if (pathMatcher.match(pattern, request.getServletPath())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 将request中的cookie全部放入box中
	 * 注意拦截器中的cookie与BaseController中的loadCookie不同,没有将携带'+'的cookie进行aes解密
	 * 
	 * @param request
	 * @param bean
	 */
	protected void loadCookie(HttpServletRequest request, Box box) {
		Cookie[] clientCookiesArray = request.getCookies();
		if (null == clientCookiesArray) {
			return;
		}
		for (Cookie c : clientCookiesArray) {
			box.getCookie().put(c.getName(), c);
		}
	}

	public void setAes(AES aes) {
		this.aes = aes;
	}
}
