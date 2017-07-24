package com.whayer.wx.wechat.vo;

import java.util.Date;

public class WechatAccount {
	
	private String id;
	private String miniprogramOpenid;      //小程序openid
	private String officialAccountOpenid;  //公众号openid
	private String unionid;                //开放平台id
	private String msgid;                  //消息id
	private Date createTime;               //创建时间
	
	public WechatAccount(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMiniprogramOpenid() {
		return miniprogramOpenid;
	}

	public void setMiniprogramOpenid(String miniprogramOpenid) {
		this.miniprogramOpenid = miniprogramOpenid;
	}

	public String getOfficialAccountOpenid() {
		return officialAccountOpenid;
	}

	public void setOfficialAccountOpenid(String officialAccountOpenid) {
		this.officialAccountOpenid = officialAccountOpenid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "WechatAccount [id=" + id + ", miniprogramOpenid=" + miniprogramOpenid + ", officialAccountOpenid="
				+ officialAccountOpenid + ", unionid=" + unionid + ", msgid=" + msgid + ", createTime=" + createTime
				+ "]";
	}
}
