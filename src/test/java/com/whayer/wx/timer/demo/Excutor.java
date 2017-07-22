package com.whayer.wx.timer.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

public class Excutor {
	public static void main(String[] args) {
		Timer timer = new Timer();
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("当前时间为：" + sf.format(calendar.getTime()));
		
		DancingRobot dr = new DancingRobot();
		WaterRobot wr = new WaterRobot(timer);
		
		timer.schedule(dr, calendar.getTime(), 2000);
		timer.scheduleAtFixedRate(wr, calendar.getTime(), 1000);
	}
}
