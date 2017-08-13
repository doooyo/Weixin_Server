package com.whayer.wx.upload.staticstate.entities;


import java.io.Serializable;

import com.whayer.wx.upload.fileuploader.json.FileStateJsonBase;



/**
 * Server-side entity representing a file.<br>
 * It contains the shared file information ({@link FileStateJsonBase}) and the url of the file.
 * @author antoinem
 *
 */
public class StaticFileState
		implements Serializable {


	/** generated id */
	private static final long serialVersionUID = 2196169291933051657L;

	/** The full path url of the uploaded file. */
	private String absoluteFullPathOfUploadedFile;

	/** The information related to the file upload. */
	private FileStateJsonBase staticFileStateJson;



	/**
	 * Default constructor.
	 */
	public StaticFileState() {
		super();
	}


	public String getAbsoluteFullPathOfUploadedFile() {
		return absoluteFullPathOfUploadedFile;
	}


	public void setAbsoluteFullPathOfUploadedFile(String absoluteFullPathOfUploadedFile) {
		this.absoluteFullPathOfUploadedFile = absoluteFullPathOfUploadedFile;
	}


	public FileStateJsonBase getStaticFileStateJson() {
		return staticFileStateJson;
	}


	public void setStaticFileStateJson(FileStateJsonBase staticFileStateJson) {
		this.staticFileStateJson = staticFileStateJson;
	}


}
