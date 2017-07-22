package com.whayer.wx.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CommonServletContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		/**
		 * TODO 这里一般是解除数据库资源占用, 销毁定时任务等等...
		 */
		System.out.println("全局上下文监听器销毁");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		String initParam = arg0.getServletContext().getInitParameter("test");
		System.out.println("全局上下文监听器初始化参数：" + initParam);
	}
}
