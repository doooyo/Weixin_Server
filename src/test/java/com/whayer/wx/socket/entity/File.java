package com.whayer.wx.socket.entity;

import java.io.Serializable;

public class File implements Serializable {

	private static final long serialVersionUID = 1254554707571682105L;
	private int fid;
	private String fname;
	private byte[] focntent;

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public byte[] getFocntent() {
		return focntent;
	}

	public void setFocntent(byte[] focntent) {
		this.focntent = focntent;
	}

	public File(int fid, String fname, byte[] focntent) {
		this.fid = fid;
		this.fname = fname;
		this.focntent = focntent;
	}

	public File(String fname, byte[] focntent) {
		this.fname = fname;
		this.focntent = focntent;
	}

}
