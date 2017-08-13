package com.whayer.wx.upload.fileuploader.exception;

public class InvalidCrcException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidCrcException(String crc32, String crc) {
		super("The file chunk is invalid. Expected "+crc32+" but received "+crc);
	}


	
	
}
