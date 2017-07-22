package com.whayer.wx;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VolatileTest {
	private volatile int number = 0;
	
	private Lock lock = new ReentrantLock();

	public int getNumber() {
		return number;
	}
	
	public void increase(){
		/**
		 * number虽然保证了可减刑，但是不是原子操作（读取number，number++，存入新值）
		 * 解决方案：
		 * 1.synchronized
		 * 2.ReentrantLock
		 * 3.AtomicXXX  volatile保证可见性，Atomic保证原子性，正好互补
		 */
		
		//this.number ++;
		
		/*synchronized (this) { //加在方法上也可以
			this.number ++;
		}*/
		
		lock.lock();
		try {
			this.number++;
		} finally {
			lock.unlock();
		}
		
	}
	
	public static void main(String[] args) {
		final VolatileTest volatileTest = new VolatileTest();
		for (int i = 0; i < 500; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					volatileTest.increase();
				}
			}).start();
		}
		
		//一旦还有子线程还在运行,主线程让出CPU
		while (Thread.activeCount() > 1) {
			Thread.yield();
		}
		
		System.out.println("number:" + volatileTest.getNumber());
	}
}
