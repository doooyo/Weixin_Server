package com.whayer.wx.wechat.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.whayer.wx.wechat.util.TokenThread;

@Deprecated
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Deprecated
	public void init() throws ServletException {

		new Thread(new TokenThread()).start();

	}
}
