/**  

* <p>Title: ExtThreadPool.java</p>  

* <p>Description: </p>  

* @author haitao.zhang  

* @date 2019年8月2日 下午11:43:57 

* @version 1.0  

*/
package com.zht.ext_thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**  

* <p>Title: ExtThreadPool</p>  

* <p>Description:自定义线程池  固定五个线程，无界队列  设置 beforeExecute、afterExecute、terminated用来监控任务执行</p>  

* @author haitao.zhang  

* @date 2019年8月2日 下午11:43:57 

*/
public class ExtThreadPool {
	public static class MyTask implements Runnable{
		public String name;
		
		public MyTask(String name) {
			this.name=name;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("正在执行"+":Thread ID:"+Thread.currentThread().getId()+",Task name="+name+"时间："+System.currentTimeMillis());
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args)throws InterruptedException {
		ExecutorService es=new ThreadPoolExecutor(5,5,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()) {

			@Override
			protected void beforeExecute(Thread t, Runnable r) {
				// TODO Auto-generated method stub
				//super.beforeExecute(t, r);
				System.out.println("准备执行"+((MyTask)r).name);
			}

			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				// TODO Auto-generated method stub
//				super.afterExecute(r, t);
				System.out.println("执行完成"+((MyTask)r).name);
			}

			@Override
			protected void terminated() {
				// TODO Auto-generated method stub
//				super.terminated();
				System.out.println("线程池退出");
			}
			
		};
		
		for(int i=0;i<5;i++) {
			MyTask mt=new MyTask("线程"+(i+1));
			es.execute(mt);
			Thread.sleep(10);
		}
		es.shutdown();
	}
}
