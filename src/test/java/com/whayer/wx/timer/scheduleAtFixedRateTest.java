package com.whayer.wx.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class scheduleAtFixedRateTest {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("当前时间为：" + sf.format(calendar.getTime()));

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
		//timer.schedule(new TimerTask() {
			@Override
			public void run() {
				//模拟任务执行3s
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("计划执行时间：" + sf.format(scheduledExecutionTime()));
			}
		}, calendar.getTime(), 2000);
	}
}
