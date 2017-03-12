package com.whayer.wx.socket.util;

import java.io.Serializable;

public class CommandTranser implements Serializable {
	
	private static final long serialVersionUID = 3524672778779248803L;
	private String cmd;// 当前操作的命令
	private Object data;// 发送的数据
	private boolean flag;// 操作是否成功
	private String result;// 返回的结果

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
