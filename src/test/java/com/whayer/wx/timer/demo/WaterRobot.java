package com.whayer.wx.timer.demo;

import java.util.Timer;
import java.util.TimerTask;

public class WaterRobot extends TimerTask{

	private Timer timer;
	private int bucketCapacity = 0;
	
	public WaterRobot(Timer inputTimer) {
		this.timer = inputTimer;
	}
	
	@Override
	public void run() {
		if(bucketCapacity < 5){
			System.out.println("加1L水到桶里");
			bucketCapacity++;
		}else{
			System.out.println("当前取消的任务数量为：" + timer.purge());
			cancel();
			System.out.println("加水任务被取消");
			System.out.println("当前取消的任务数量为：" + timer.purge());
			System.out.println("当前桶的容量为：" + bucketCapacity + "L");
			//等待两秒，终止timer所有任务
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timer.cancel();
		}
	}
}
