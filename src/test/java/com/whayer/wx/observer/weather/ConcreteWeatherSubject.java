package com.whayer.wx.observer.weather;

/**
 * 具体被观察的天气对象,一旦有天气更新,则通知所有订阅的人
 * @author duyu
 */
public class ConcreteWeatherSubject extends WeatherSubject{
	
	//获取天气内容信息
	private String weatherContent;
	

	public String getWeatherContent() {
		return weatherContent;
	}

	public void setWeatherContent(String weatherContent) {
		this.weatherContent = weatherContent;
		//天气一旦变化,立马通知所有订阅了天气的人
		this.notifyObservers();
	}
}
