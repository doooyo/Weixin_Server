package com.whayer.wx.common.filter;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;

@Deprecated
public class GZIPServletOutputStream extends ServletOutputStream {

	private GZIPOutputStream gzipos;

	public GZIPServletOutputStream(ServletOutputStream sos) throws IOException {
		this.gzipos = new GZIPOutputStream(sos);
	}

	@Override
	public void write(int data) throws IOException {
		gzipos.write(data);
	}

	public GZIPOutputStream getGZIPOutputStream() {
		return gzipos;
	}
}
