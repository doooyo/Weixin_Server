package com.whayer.wx.observer.customized;


public class Client {
	public static void main(String[] args) {
		// 1 创建目标
		ConcreteWeatherSubject weather = new ConcreteWeatherSubject();

		// 2 创建观察者
		ConcreteObserver observerFather = new ConcreteObserver();
		observerFather.setObserverName("爸爸");
		observerFather.setTipContent("下雨了,呆在家看报纸");

		ConcreteObserver observerMother = new ConcreteObserver();
		observerMother.setObserverName("妈妈");
		observerMother.setTipContent("不管下雨还是下雪,都要去逛商场");
		
		ConcreteObserver observerOther = new ConcreteObserver();
		observerOther.setObserverName("Other");
		observerOther.setTipContent("最新天气预报");

		// 3 注册观察者
		weather.attach(observerFather);
		weather.attach(observerMother);
		weather.attach(observerOther);

		// 4 目标发布天气
		//weather.setWeatherContent("明天天气晴朗,蓝天白云,气温26度");
		//weather.setWeatherContent("明天下雨,倾盆大雨,气温18度,请带上雨伞");
		weather.setWeatherContent("明天下雪,鹅毛大雪,气温-2度,请注意保暖");
	}
}
