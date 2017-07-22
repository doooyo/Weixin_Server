package com.whayer.wx.common.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * 可以监听每个请求
 * @author duyu
 *
 */
public class CommonServletRequestListener implements ServletRequestListener{

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		System.out.println("请求结束时");
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		String param = arg0.getServletRequest().getParameter("name");
		System.out.println("请求监听器开始，参数为：" + param);
	}
}
