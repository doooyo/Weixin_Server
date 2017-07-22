package com.whayer.wx.observer.weather;

/**
 * 具体观察者
 * @author duyu
 */
public class ConcreteObserver implements Observer{
	
	private String observerName;   //订阅者名字
	private String weatherContent; //订阅的天气变化内容
	private String remindContent;  //提醒内容

	/**
	 * 接到通知,则更新目标对象状态到观察者状态中
	 */
	@Override
	public void update(WeatherSubject subject) {
		//从订阅的天气对象中获取天气的变化内容
		this.weatherContent = ((ConcreteWeatherSubject)subject).getWeatherContent();
		//打印消息
		System.out.println(observerName + "收到了消息: " + weatherContent + ", " + remindContent);
	}

	public String getObserverName() {
		return observerName;
	}

	public void setObserverName(String observerName) {
		this.observerName = observerName;
	}

	public String getWeatherContent() {
		return weatherContent;
	}

	public void setWeatherContent(String weatherContent) {
		this.weatherContent = weatherContent;
	}

	public String getRemindContent() {
		return remindContent;
	}

	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}
}
