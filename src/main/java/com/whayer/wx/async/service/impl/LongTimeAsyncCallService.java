package com.whayer.wx.async.service.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.whayer.wx.async.service.LongTimeTaskCallback;

/**
 * 任务线程
 * @author duyu
 *
 */
public class LongTimeAsyncCallService {
	private final int CorePoolSize = 4;
    private final int NeedSeconds = 10;
    //创建线程池
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(CorePoolSize);
    public void makeRemoteCallAndUnknownWhenFinish(LongTimeTaskCallback callback){
        System.out.println("完成此任务需要 : " + NeedSeconds + " 秒");
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
            	//在此处手动回调
                callback.callback("长时间异步调用完成.");
            }
        }, NeedSeconds, TimeUnit.SECONDS);
        
    }
}
