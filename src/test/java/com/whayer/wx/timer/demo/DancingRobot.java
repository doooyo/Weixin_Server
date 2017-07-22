package com.whayer.wx.timer.demo;

import java.text.SimpleDateFormat;
import java.util.TimerTask;

public class DancingRobot extends TimerTask {

	@Override
	public void run() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		System.out.println("跳舞计划执行时间：" + sf.format(scheduledExecutionTime()));
	}
}
