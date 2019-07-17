package com.test.jobbers;

import java.util.concurrent.locks.ReentrantLock;

import com.test.jmx_controller.AttributeJobberName;
import com.test.jmx_controller.INotificationJobber;


public class JobberManager {
	private IJobber currentJobber=null;
	private boolean flagExecuting=false;
	private ReentrantLock lock=new ReentrantLock();
	
	public void sequencyRunAllJobbers(IJobber[] jobbers){
		this.flagExecuting=true;
		System.out.println("begin");
		for(IJobber job:jobbers){
			if(lock.isLocked()){
				lock.unlock();
			}
			if(notificationProxyObject!=null){
				if(this.currentJobber!=null){
					notificationProxyObject.changeJobberState("stop");
				}
			}
			
			// prepare new jobber for start 
			this.currentJobber=job;
			// notifiy about new jobber name 
			if(notificationProxyObject!=null){
				notificationProxyObject.changeJobberName(this.currentJobber.getClass().getName());
			}
			// change name of attribute 
			if(this.attributeJobberName!=null){
				this.attributeJobberName.setJobberName(this.currentJobber.getClass().getName());
			}
			if(notificationProxyObject!=null){
				notificationProxyObject.changeJobberState("start");
			}
			job.start();
			
			lock.lock();
		}
		lock.unlock();
		
		this.currentJobber=null;
		System.out.println("-end-");
		this.flagExecuting=false;
	}
	
	public String getName() {
		return this.currentJobber.getClass().getName();
	}

	public boolean isExecuted() {
		return this.flagExecuting;
	}

	public void stopJobber(String name) {
		if(this.currentJobber!=null){
			this.lock.lock();
			if(this.currentJobber.getClass().getName().equals(name)){
				this.currentJobber.stop();
			}
			this.lock.unlock();
		}
	}

	private AttributeJobberName attributeJobberName;
	public void setAttributeJobberName(AttributeJobberName attributeName) {
		this.attributeJobberName=attributeName;
	}

	private INotificationJobber notificationProxyObject=null;
	public void setNotificationJobber(INotificationJobber notificationProxyObject) {
		this.notificationProxyObject=notificationProxyObject;
	}

}
