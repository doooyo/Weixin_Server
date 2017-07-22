package com.whayer.wx.observer.common;

import java.util.ArrayList;
import java.util.List;


/**
 * 被观察目标对象
 * 提供注册，删除观察者
 * @author duyu
 *
 */
public class Subject {
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	public void attach(Observer observer){
		observers.add(observer);
	}
	
	public void detach(Observer observer){
		observers.remove(observer);
	}
	
	public void notifyObservers(){
		for (Observer observer : observers) {
			observer.update(this);
		}
	}
}
