package com.whayer.wx.observer.common;

/**
 * 具体观察者
 * @author duyu
 *
 */
public class ConcreteObserver implements Observer{
	
	private String observerState;

	/**
	 * 接到通知,则更新目标对象状态到观察者状态中
	 */
	@Override
	public void update(Subject subject) {
		this.observerState = ((ConcreteSubject)subject).getSubjectState();
	}

	public String getObserverState() {
		return observerState;
	}

	public void setObserverState(String observerState) {
		this.observerState = observerState;
	}
}
