package com.whayer.wx.observer.customized;

import java.util.ArrayList;
import java.util.List;


public abstract class WeatherSubject {
	
	List<Observer> observers = new ArrayList<Observer>();
	
	public void attach(Observer observer){
		observers.add(observer);
	}
	
	public void detach(Observer observer){
		observers.remove(observer);
	}
	
	/**
	 * 为了定制通知方法,我们将之放到子类中实现
	 * 同时使用protected修饰符,保证只能当前package的子类能访问;若子类跨包也可访问,以保证能重写
	 * 但不能是default(friendly) 因为就算是子类,一旦跨包就不能访问(类似C#的internal,只能本程序集可见)
	 */
	protected abstract void notifyObservers();
}
