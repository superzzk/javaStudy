package com.zzk.study.util.concurrent.thread_pool;

import util.concurrent.blockingqueue.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;

public class SimpleThreadPool {
	private SimpleBlockingQueue taskQueue = null;
	private List<PoolThread> threads = new ArrayList<PoolThread>();
	private boolean isStopped = false;

	public SimpleThreadPool(int noOfThreads, int maxNoOfTasks){
		taskQueue = new SimpleBlockingQueue(maxNoOfTasks);

		for(int i=0; i<noOfThreads; i++){
			threads.add(new PoolThread(taskQueue));
		}
		for(PoolThread thread : threads){
			thread.start();
		}
	}

	public synchronized void  execute(Runnable task) throws Exception{
		if(this.isStopped) throw
				new IllegalStateException("ThreadPool is stopped");

		this.taskQueue.enqueue(task);
	}

	public synchronized void stop(){
		this.isStopped = true;
		for(PoolThread thread : threads){
			thread.doStop();
		}
	}

	class PoolThread extends Thread {

		private SimpleBlockingQueue taskQueue = null;
		private boolean       isStopped = false;

		public PoolThread(SimpleBlockingQueue queue){
			taskQueue = queue;
		}

		public void run(){
			while(!isStopped()){
				try{
					Runnable runnable = (Runnable) taskQueue.dequeue();
					runnable.run();
				} catch(Exception e){
					//log or otherwise report exception,
					//but keep pool thread alive.
				}
			}
		}

		public synchronized void doStop(){
			isStopped = true;
			this.interrupt(); //break pool thread out of dequeue() call.
		}

		public synchronized boolean isStopped(){
			return isStopped;
		}
	}


}
