package com.whayer.wx.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * 业务线程（提供给Timer后台线程进行调度）
 * @author duyu
 */
public class MyTimerTask extends TimerTask{

	//私有变量
	private String name;
	private int count = 0; //计数器

	//构造函数
	public MyTimerTask(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		if(count < 3){
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			System.out.println("【"+name+"】任务执行时间：" + sf.format(calendar.getTime()));
		}else{
			//cancel方法继承于TimerTask类
			cancel();
			System.out.println("当前任务【"+ name +"】已取消");
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
