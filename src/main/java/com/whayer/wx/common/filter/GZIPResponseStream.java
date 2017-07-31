package com.whayer.wx.common.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;

public class GZIPResponseStream extends ServletOutputStream {

	protected ByteArrayOutputStream baos = null;
	protected GZIPOutputStream gzipstream = null;
	protected boolean closed = false;
	protected HttpServletResponse response = null;
	protected ServletOutputStream output = null;
	final AtomicBoolean open = new AtomicBoolean(true);

	public GZIPResponseStream(HttpServletResponse response) throws IOException {
		super();
		closed = false;
		open.set(true);
		
		this.response = response;
		this.output = response.getOutputStream();
		baos = new ByteArrayOutputStream();
		gzipstream = new GZIPOutputStream(baos);
	}

	@Override
	public void write(int b) throws IOException {
		//if (closed) {
		if(!open.get()) {
			throw new IOException("Cannot write to a closed output stream");
		}
		gzipstream.write((byte) b);
	}

	@Override
	public void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	@Override
	public void write(byte b[], int off, int len) throws IOException {
		//if (closed) {
		if(!open.get()) {
			throw new IOException("Cannot write to a closed output stream");
		}
		gzipstream.write(b, off, len);
	}

	
	public boolean closed() {
		return !this.open.get(); //(this.closed);
	}

	public void reset() {
		// noop
	}

	public void close() throws IOException {
		//if (closed) {
		if(!open.get()) {
			throw new IOException("This output stream has already been closed");
		}
		gzipstream.finish();

		byte[] bytes = baos.toByteArray();

		response.addHeader("Content-Length", Integer.toString(bytes.length));
		response.addHeader("Content-Encoding", "gzip");
		output.write(bytes);
		output.flush();
		output.close();
		//closed = true;
		open.compareAndSet(true, false);
	}

	public void flush() throws IOException {
		//if (closed) {
		if(!open.get()) {
			throw new IOException("Cannot flush a closed output stream");
		}
		gzipstream.flush();
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWriteListener(WriteListener arg0) {
		// TODO Auto-generated method stub
		
	}
}
