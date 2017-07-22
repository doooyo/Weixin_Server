package com.whayer.wx.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

public class Test {
	public static void main(String[] args) {
		//1.创建Timer实例
		Timer timer = new Timer();
		//2.创建MyTimerTask任务线程实例
		MyTimerTask myTimerTask = new MyTimerTask("任务1");
		//3.通过Timer定时定频率调用MyTimerTask线程
		
		//delay: 指首次运行是在当前时间2秒后
		//period:调度的频率,即隔多久调用一次
		
		//timer.schedule(myTimerTask, 2000L, 1000L); //timer.schedule(task, delay, period);
		
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("当前时间为：" + sf.format(calendar.getTime()));
		//设置为3秒后
		calendar.add(Calendar.SECOND, 3);
		
		/**
		 * 1.schedule(TimerTask task, Date time)   
		 * 当前时间3秒后执行
		 */
		//myTimerTask.setName("schedule1");
		//timer.schedule(myTimerTask, calendar.getTime());
		
		/**
		 * 2.schedule(TimerTask task, Date firstTime, long period)   
		 * 当前时间3秒后执行,并每隔2秒执行一次
		 */
		//myTimerTask.setName("schedule2");
		//timer.schedule(myTimerTask, calendar.getTime(), 2000);
		
		/**
		 * 3.schedule(TimerTask task, long delay)   
		 * 当前时间3秒后执行,并每隔2秒执行一次
		 */
		myTimerTask.setName("schedule3");
		timer.schedule(myTimerTask, 2000);
		
		/**
		 * 4.schedule(TimerTask task, long delay, long period) 
		 * 当前时间等待3000毫秒后执行首次任务，之后每隔2000毫秒重复执行
		 */
		//myTimerTask.setName("schedule4");
		//timer.schedule(myTimerTask, 3000, 2000);
		
		/**
		 * 5.scheduleAtFixedRate(TimerTask task, Date firstTime, long period) 
		 * 当前时间3秒后执行,并每隔2秒执行一次
		 */
		//myTimerTask.setName("scheduleAtFixedRate1");
		//timer.scheduleAtFixedRate(myTimerTask, calendar.getTime(), 2000);
		
		/**
		 * 6.scheduleAtFixedRate(TimerTask task, long delay, long period) 
		 * 当前时间等待3秒后执行,并每隔2秒执行一次
		 */
		//myTimerTask.setName("scheduleAtFixedRate2");
		//timer.scheduleAtFixedRate(myTimerTask, 3000, 2000);
		
		System.out.println("计划执行时间为：" + sf.format(myTimerTask.scheduledExecutionTime()));
		
	}
}
