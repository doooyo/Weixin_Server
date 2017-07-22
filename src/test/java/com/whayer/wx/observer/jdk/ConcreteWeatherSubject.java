package com.whayer.wx.observer.jdk;

import java.util.Observable;

public class ConcreteWeatherSubject extends Observable{
	
	private String weatherContent;

	public String getWeatherContent() {
		return weatherContent;
	}

	public void setWeatherContent(String weatherContent) {
		//注意要现设置内容,然后再推送
		this.weatherContent = weatherContent;
		
		//注意JDK再通知前,必须先调用setChanged
		this.setChanged();
		
		//【拉模式】,内部传递的是this; 跟我们自己实现的是一样的效果
		//this.notifyObservers();
		
		//【推模式】内部已经实现了【拉模式】
		this.notifyObservers(weatherContent);
	}
}
