package com.whayer.wx.login.vo;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Arrays;

public class SkUser implements Serializable{

	private static final long serialVersionUID = -9040151004036751096L;
	
	private String id;
	private String pId;
	private Long points;
	private byte[] photo;
	private Integer auditState;
	private Integer isAgent;
	
	@Override
	public String toString() {
		return "SkUser [id=" + id + ", pId=" + pId + ", points=" + points + ", photo=" + Arrays.toString(photo)
				+ ", auditState=" + auditState + ", isAgent=" + isAgent + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public Long getPoints() {
		return points;
	}
	public void setPoints(Long points) {
		this.points = points;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public Integer getAuditState() {
		return auditState;
	}
	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
	public Integer getIsAgent() {
		return isAgent;
	}
	public void setIsAgent(Integer isAgent) {
		this.isAgent = isAgent;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
