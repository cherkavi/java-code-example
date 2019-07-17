package com.test.jobbers;

import java.util.concurrent.TimeUnit;

public class SecondJobber implements IJobber{
	private String name=this.getClass().getName();
	private Thread startThread=null;
	
	private int seconds;
	
	public SecondJobber(int seconds){
		this.seconds=seconds;
	}
	

	@Override
	public void start()  {
		this.startThread=Thread.currentThread();
		System.out.println("Begin:"+this);
		try {
			TimeUnit.SECONDS.sleep(this.seconds);
		} catch (InterruptedException e) {
			System.err.println(this+" InterruptedException: ");
			e.printStackTrace();
		}
		System.out.println("End:"+this);
		this.startThread=null;
	}


	@Override
	public String toString() {
		return this.name;
	}


	@Override
	public void stop() {
		try{
			System.out.println("Thread: "+Thread.currentThread()+"  try to interrupt: "+this.startThread);
			this.startThread.interrupt();
		}catch(Exception ex){
			System.out.println(this+" Stop Exception");
		}
	}

	
}
