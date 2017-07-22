package com.whayer.wx.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduleTest {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("当前时间为：" + sf.format(calendar.getTime()));

		//计划执行时间早于当前时间5秒
		calendar.add(Calendar.SECOND, -6);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
		//timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("计划执行时间：" + sf.format(scheduledExecutionTime()));
			}
		}, calendar.getTime(), 2000);
	}
}
