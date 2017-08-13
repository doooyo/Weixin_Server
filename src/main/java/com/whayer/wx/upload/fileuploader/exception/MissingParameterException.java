package com.whayer.wx.upload.fileuploader.exception;

import com.whayer.wx.upload.fileuploader.web.UploadServletParameter;

public class MissingParameterException extends BadRequestException {

	private static final long serialVersionUID = 1L;

	public MissingParameterException(UploadServletParameter parameter) {
		super("The parameter " + parameter.name() + " is missing for this request.");
	}


}
