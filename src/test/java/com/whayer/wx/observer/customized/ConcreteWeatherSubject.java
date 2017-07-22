package com.whayer.wx.observer.customized;

public class ConcreteWeatherSubject extends WeatherSubject{
	
	//晴天,下雨,下雪
	private String weatherContent;
	
	/**
	 * 重写通知规则
	 * 爸爸:需要"下雨"的通知
	 * 妈妈:需要"下雨"或者"下雪"的通知
	 * other:所有天气都通知
	 */
	@Override
	protected void notifyObservers() {
		
		for (Observer observer : observers) {
			
			if("爸爸".equals(observer.getObserverName())) {
				if(this.weatherContent.indexOf("下雨") > -1) {
					observer.update(this);
				}
			}else if("妈妈".equals(observer.getObserverName())) {
				if(this.weatherContent.indexOf("下雨") > -1 || this.weatherContent.indexOf("下雪") > -1) {
					observer.update(this);
				}
			}else{
				observer.update(this);
			}
		}
	}

	public String getWeatherContent() {
		return weatherContent;
	}

	public void setWeatherContent(String weatherContent) {
		this.weatherContent = weatherContent;
		this.notifyObservers();
	}
}
