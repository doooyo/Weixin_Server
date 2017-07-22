package com.whayer.wx.observer.common;

/**
 * 具体目标对象，负责把有关状态存入相应的观察者对象中
 * @author duyu
 *
 */
public class ConcreteSubject extends Subject{
	
	//目标状态
	private String subjectState;

	public String getSubjectState() {
		return subjectState;
	}

	public void setSubjectState(String subjectState) {
		this.subjectState = subjectState;
		//目标一旦变化,立马通知所有观察者
		notifyObservers();
	}
}
