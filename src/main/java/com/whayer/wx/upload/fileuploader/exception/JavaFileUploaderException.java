package com.whayer.wx.upload.fileuploader.exception;

import com.whayer.wx.upload.fileuploader.web.utils.ExceptionCodeMappingHelper.ExceptionCodeMapping;

/**
 * This exception contains a simple identifier ({@link #exceptionIdentifier}) so that the javascript
 * can identify it and i18n it.
 * 
 */
public class JavaFileUploaderException extends Exception {

	private static final long serialVersionUID = 1L;
	private ExceptionCodeMapping exceptionCodeMapping;



	public JavaFileUploaderException() {
	}


	public JavaFileUploaderException(ExceptionCodeMapping exceptionCodeMapping) {
		super();
		this.exceptionCodeMapping = exceptionCodeMapping;
	}


	public ExceptionCodeMapping getExceptionCodeMapping() {
		return exceptionCodeMapping;
	}


	public void setExceptionCodeMapping(ExceptionCodeMapping exceptionCodeMapping) {
		this.exceptionCodeMapping = exceptionCodeMapping;
	}


}
