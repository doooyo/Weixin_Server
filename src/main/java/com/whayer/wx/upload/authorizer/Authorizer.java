package com.whayer.wx.upload.authorizer;

import javax.servlet.http.HttpServletRequest;

public interface Authorizer {
	void getAuthorization(HttpServletRequest request);
}
