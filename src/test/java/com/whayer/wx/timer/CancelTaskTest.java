package com.whayer.wx.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class CancelTaskTest {
	public static void main(String[] args) throws InterruptedException {
		// 1.创建Timer实例
		Timer timer = new Timer();
		// 2.创建MyTimerTask任务线程实例
		MyTimerTask task1 = new MyTimerTask("任务1");
		MyTimerTask task2 = new MyTimerTask("任务2");
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("当前时间为：" + sf.format(calendar.getTime()));
		
		//task1首次执行是距离当前时间3秒后执行，之后每隔2秒执行一次
		//task1首次执行是距离当前时间2秒后执行，之后每隔1秒执行一次
		timer.schedule(task1, 3000, 2000);
		timer.schedule(task2, 2000, 1000);
		
		//休眠5秒
		Thread.sleep(5000);
		
		System.out.println("定时器取消时间为：" + sf.format(new Date()));
		
		timer.cancel();
		
		System.out.println("所有任务已取消！");
	}
}
