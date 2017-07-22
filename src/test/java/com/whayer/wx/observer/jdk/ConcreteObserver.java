package com.whayer.wx.observer.jdk;

import java.util.Observable;
import java.util.Observer;

public class ConcreteObserver implements Observer{
	
	private String observerName;

	@Override
	public void update(Observable o, Object arg) {
		//【推模式】
		System.out.println(observerName + "收到了消息, 目标推送的消息是: " + arg);
		//【拉模式】
		System.out.println(observerName + "收到了消息, 主动拉取的消息是: " 
		+ ((ConcreteWeatherSubject)o).getWeatherContent());
	}

	public String getObserverName() {
		return observerName;
	}

	public void setObserverName(String observerName) {
		this.observerName = observerName;
	}
}
