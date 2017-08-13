package com.whayer.wx.upload.fileuploader.exception;


/**
 * Exception thrown if the uploads are not enabled at the moment.
 * @see JavaLargeFileUploaderService#enableFileUploader()
 * @see JavaLargeFileUploaderService#disableFileUploader()
 */
public class UploadIsCurrentlyDisabled extends Exception {

	private static final long serialVersionUID = 1L;

	public UploadIsCurrentlyDisabled() {
		super("All uploads are currently suspended.");
	}
	
}
