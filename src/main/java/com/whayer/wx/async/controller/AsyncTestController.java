package com.whayer.wx.async.controller;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.whayer.wx.async.service.LongTimeTaskCallback;
import com.whayer.wx.async.service.impl.LongTimeAsyncCallService;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.BaseController;

/**
 * 【Callable】:spring mvc 内部交由TaskExcutor执行
 * 【DeferredResult】: DeferredResult则由其他线程(自己实现的)执行返回结果,需要自己手动去回调
 * 【WebAsyncTask】:核心也是Callable,只是包装了【timeout回调】以及【complete回调】
 * @author duyu
 *
 */
@Controller
@RequestMapping("/async")
public class AsyncTestController  extends BaseController{
	
	/**
	 * 
	 * 首先,我们来测试如果不使用【Callable】,【DeferredResult】,【WebAsyncTask】等异步请求
	 * 而是直接开启另一个线程来处理耗时任务,看是否
	 * @return
	 */
	@RequestMapping(value="/test0", method=RequestMethod.GET)  
	@ResponseBody
    public String test0() {  
		System.out.println("请求开始时间：" + X.nowString());
		System.out.println("/test2 调用！thread id is : " + Thread.currentThread().getId());
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(6000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("异步任务完成时间：" + X.nowString());
				System.out.println("/异步调用完成！thread id is : " + Thread.currentThread().getId());
			}
		}).start();
		
		System.out.println("请求返回时间：" + X.nowString());
		return "请求完成";
    }  
	
	/**
	 * 返回Callable, springmvc处理过程如下
	 * 1.当controller返回值是Callable的时候，springmvc就会启动一个线程将Callable交给TaskExecutor去处理
	 * 2.然后DispatcherServlet还有所有的spring拦截器都退出主线程，然后把response保持打开的状态
	 * 3.当Callable执行结束之后，springmvc就会重新启动分配一个request请求，
	 *   然后DispatcherServlet就重新调用和处理Callable异步执行的返回结果，然后返回视图
	 * @return
	 */
	@RequestMapping(value="/test1", method=RequestMethod.GET)  
	@ResponseBody
    public Callable<String> test1() {  
  
        return new Callable<String>() {  
            public String call() throws Exception {  
  
                // Do some work..  
                Thread.sleep(6000L);  
                //如果不指定@ResponseBody,则跳转的是资源页面;反之是直接返回到请求的Body部分
                //@ResponseBody一般用于处理json string xml等
                return "/login";  
            }  
        };  
    }  
	
	/**
	 * 返回DeferredResult,springmvc的处理过程同Callable
	 * 不同点：Callable是由springmvc自动交由内部的TaskExecutor
	 *       而DeferredResult则由其他线程(自己实现的)执行返回结果
	 * @return
	 */
	@RequestMapping(value="/test2", method = RequestMethod.GET)
	@ResponseBody
	public DeferredResult<String> test2(){
	    DeferredResult<String> deferredResult = new DeferredResult<String>();
	    System.out.println("/test2 调用！thread id is : " + Thread.currentThread().getId());
	    LongTimeAsyncCallService longTimeAsyncCallService = new LongTimeAsyncCallService();
	    //记录请求处理线程开始时间
	    System.out.println(X.nowString());
	    longTimeAsyncCallService.makeRemoteCallAndUnknownWhenFinish(new LongTimeTaskCallback() {
			
			@Override
			public void callback(Object result) {
				System.out.println("异步调用执行完成, thread id is : " + Thread.currentThread().getId());
				//手动调用回调函数,并将处理结果扔给DeferredResult,它会自行分配其他空闲请求线程继续处理
				deferredResult.setResult(result.toString());
				//记录异步线程处理完成时间
				System.out.println(X.nowString());
			}
		});
	    return deferredResult;
	}
	
	/**
	 * WebAsyncTask 其核心也是一个Callable,即SpringMVC内部交由TaskExecutor处理;
	 * 再包裹一层的目的是为了更好的支持业务; 比如超时时间
	 * @return
	 */
	@RequestMapping(value="/test3", method = RequestMethod.GET)
	@ResponseBody
	public WebAsyncTask<String> test3(){
	    System.out.println("/test3 被调用 thread id is : " + Thread.currentThread().getId());
	    
	    Callable<String> callable = new Callable<String>() {
	    	
	    	@Override
	        public String call() throws Exception {
	        	
	            Thread.sleep(3000); //假设是一些长时间任务
	           
	            System.out.println("执行成功 thread id is : " + Thread.currentThread().getId());
	            return "执行任务成功结果";
	        }
	    };
	    return new WebAsyncTask<String>(callable);
	}
	
	/**
	 * 利用WebAsyncTask模拟超时
	 * @return
	 */
	@RequestMapping(value="/test4", method = RequestMethod.GET)
	@ResponseBody
	public WebAsyncTask<String> test4(){
	    System.out.println("/test4 被调用 thread id is : " + Thread.currentThread().getId());
	    
	    Callable<String> callable = new Callable<String>() {
	    	
	    	@Override
	        public String call() throws Exception {
	        	
	            Thread.sleep(3000); //假设是一些长时间任务
	           
	            System.out.println("执行成功 thread id is : " + Thread.currentThread().getId());
	            
	            //超时后,任务会继续执行,只是不会返回执行结果给用户
	            return "执行任务成功结果";
	        }
	    };
	    
	    //----------------加入超时(2s), 任务耗时为3s---------------
	    WebAsyncTask<String> asyncTask = new WebAsyncTask<String>(2000, callable);
	    asyncTask.onTimeout(
	            new Callable<String>() {
	                public String call() throws Exception {
	                    
	                    System.out.println("执行超时 thread id is ：" + Thread.currentThread().getId());
	                    
	                    return "执行超时";
	                }
	            }
	    );
	    
	    //超时后任务会继续执行,直到完成并调用onComplete回调函数
	    asyncTask.onCompletion(
	            new Runnable() {
					@Override
					public void run() {
						System.out.println("任务完成");
					}
				}
	    );
	    //-------------------------------------
	    return asyncTask;
	}
}
