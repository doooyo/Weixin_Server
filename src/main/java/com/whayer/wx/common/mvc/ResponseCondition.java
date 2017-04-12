package com.whayer.wx.common.mvc;

import java.io.Serializable;
import java.util.List;


public class ResponseCondition implements Serializable{

	private static final long serialVersionUID = -8173852245814158731L;
	
	//private Integer errorCode;         //业务错误码
	//private Integer httpCode = 200;    //HTTP状态码
	private String  errorMsg = "";     //错误堆栈
	private Boolean isSuccess = true;  //是否成功
	private List<?> list;              //返回集合
	//private Object  result;            //返回单个对象
	private int pageSize;       //分页大小        
	private int pageIndex;      //页码
	private long total;         //总数
	private int pages;          //总页数
	
//	public Object getResult() {
//		return result;
//	}
//	public void setResult(Object result) {
//		this.result = result;
//	}
//	public Integer getErrorCode() {
//		return errorCode;
//	}
//	public void setErrorCode(Integer errorCode) {
//		this.errorCode = errorCode;
//	}
//	public Integer getHttpCode() {
//		return httpCode;
//	}
//	public void setHttpCode(Integer httpCode) {
//		this.httpCode = httpCode;
//	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	@Override
	public String toString() {
		return "ResponseCondition [errorMsg=" + errorMsg + ", isSuccess=" + isSuccess + ", list=" + list + ", pageSize="
				+ pageSize + ", pageIndex=" + pageIndex + ", total=" + total + ", pages=" + pages + "]";
	}
	
}
