package com.whayer.wx.observer.weather;

/**
 * 观察者接口
 * @author duyu
 */
public interface Observer {
	public void update(WeatherSubject subject);
}
