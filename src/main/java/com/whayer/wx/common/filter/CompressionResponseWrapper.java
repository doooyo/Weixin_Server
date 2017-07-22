package com.whayer.wx.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@Deprecated
public class CompressionResponseWrapper extends HttpServletResponseWrapper {

	private GZIPServletOutputStream gzipsos;

	private PrintWriter pw;

	public CompressionResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);

		gzipsos = new GZIPServletOutputStream(response.getOutputStream());

		pw = new PrintWriter(gzipsos);
	}

	/**
	 * 重写setContentLength()方法，以避免Content-Length实体报头所指出的长度 和压缩后的实体正文长度不匹配
	 * 目的是阻止Servlet代码设置响应的内容长度头,因为直到响应被压缩之后它才能获得内容长度
	 */
	@Override
	public void setContentLength(int len) {
	}
	 

//    @Override  
//    public void setHeader(String name, String value)  
//    {  
//        if(!"content-length".equalsIgnoreCase(name))  
//            super.setHeader(name, value);  
//    }  

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return gzipsos;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return pw;
	}

	/**
	 * 过滤器调用这个方法来得到GZIPOutputStream对象，以便完成将压缩数据写入输出流的操作
	 */
	public GZIPOutputStream getGZIPOutputStream() {
		return gzipsos.getGZIPOutputStream();
	}
}
