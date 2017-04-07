package com.whayer.wx.common.mvc;

import java.io.Serializable;
import java.util.List;


public class ResponseCondition implements Serializable{

	private static final long serialVersionUID = -8173852245814158731L;
	
	private Integer errorCode;
	private Integer httpCode = 200;    
	private String errorMsg = "";
	private Boolean isSuccess = true;
	private List<?> list;
	private Object result;
	
	
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public Integer getHttpCode() {
		return httpCode;
	}
	public void setHttpCode(Integer httpCode) {
		this.httpCode = httpCode;
	}
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
	
	@Override
	public String toString() {
		return "ResponseCondition [errorCode=" + errorCode + ", httpCode=" + httpCode + ", errorMsg=" + errorMsg
				+ ", isSuccess=" + isSuccess + ", list=" + list + "]";
	}
	
}
