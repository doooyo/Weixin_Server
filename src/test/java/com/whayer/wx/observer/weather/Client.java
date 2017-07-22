package com.whayer.wx.observer.weather;

public class Client {
	public static void main(String[] args) {
		//1 创建目标
		ConcreteWeatherSubject weather = new ConcreteWeatherSubject();
		
		//2 创建观察者
		ConcreteObserver observerFather = new ConcreteObserver();
		observerFather.setObserverName("爸爸");
		observerFather.setRemindContent("去钓鱼...");
		
		ConcreteObserver observerMother = new ConcreteObserver();
		observerMother.setObserverName("妈妈");
		observerMother.setRemindContent("去逛商场买衣服...");
		
		//3 注册观察者
		weather.attach(observerFather);
		weather.attach(observerMother);
		
		//4 目标发布天气
		weather.setWeatherContent("明天天气晴朗,蓝天白云,气温26度");
	}
}
