package com.whayer.wx.upload.fileuploader.exception;

import java.util.UUID;


public class FileStillProcessingException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public FileStillProcessingException(UUID fileId) {
		super("The file "+fileId+" is still in a process. AsyncRequest ignored.");
	}
	
}
