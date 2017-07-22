package com.whayer.wx.wechat.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.whayer.wx.wechat.util.TokenThread;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {

		new Thread(new TokenThread()).start();

	}
}
