package com.whayer.wx.observer.customized;

public interface Observer {
	//更新的接口
	public void update(WeatherSubject subject);
	//设置观察者名称
	public void setObserverName(String observerName);
	//获取观察者名称
	public String getObserverName();
}
