package com.whayer.wx.common.mvc;

import java.io.Serializable;
import java.util.List;


public class ResponseCondition implements Serializable{

	private static final long serialVersionUID = -8173852245814158731L;
	
	private Integer errorCode;
	private Integer httpCode = 200;    
	private String errorMsg = "";
	private Boolean isSuccess = true;
	private List<?> result;
	
	
	
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
	
	public List<?> getResult() {
		return result;
	}
	public void setResult(List<?> result) {
		this.result = result;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ResponseCondition [errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", isSuccess=" + isSuccess + ", result="
				+ result + "]";
	}
}
