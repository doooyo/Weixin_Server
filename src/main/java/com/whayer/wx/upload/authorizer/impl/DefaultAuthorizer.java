package com.whayer.wx.upload.authorizer.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.whayer.wx.upload.authorizer.Authorizer;
import com.whayer.wx.upload.fileuploader.web.UploadServletAction;


@Component
public class DefaultAuthorizer implements Authorizer {

	@Override
	public void getAuthorization(HttpServletRequest request, UploadServletAction action, UUID clientId, UUID... optionalFileId) {
		// by default, all calls are authorized
	}

}
