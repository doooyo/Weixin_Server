package com.whayer.wx.observer.customized;

public class ConcreteObserver implements Observer{
	
	//观察者名称
	private String observerName;
	//天气内容
	private String weatherContent;
	//提醒内容
	private String tipContent;

	@Override
	public void update(WeatherSubject subject) {
		weatherContent = ((ConcreteWeatherSubject)subject).getWeatherContent();
		System.out.println(observerName + "收到了消息: " + weatherContent + "。 提示: " + tipContent);
	}

	@Override
	public void setObserverName(String observerName) {
		this.observerName = observerName;
	}

	@Override
	public String getObserverName() {
		return this.observerName;
	}

	public String getWeatherContent() {
		return weatherContent;
	}

	public void setWeatherContent(String weatherContent) {
		this.weatherContent = weatherContent;
	}

	public String getTipContent() {
		return tipContent;
	}

	public void setTipContent(String tipContent) {
		this.tipContent = tipContent;
	}
}
