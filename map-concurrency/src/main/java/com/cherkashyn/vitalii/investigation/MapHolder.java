package com.cherkashyn.vitalii.investigation;

import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

public class MapHolder extends Thread{
	// java.util.ConcurrentModificationException
	// private Set<String> body=new HashSet<String>();
	
	// pass
	// private Set<String> body=new ConcurrentSkipListSet<String>();
	
	private Set<String> body=new CopyOnWriteArraySet<String>();

	private int secondDelay;
	
	public MapHolder(){
		this(1);
	}
	
	public MapHolder(int delayInSeconds){
		this.secondDelay=delayInSeconds;
	}
	
	public Set<String> getValue(){
		return this.body;
	}

	@Override
	public void run() {
		for(String eachValue:this.body){
			System.out.println(MessageFormat.format(">>> {0}", eachValue));
			try {
				TimeUnit.SECONDS.sleep(secondDelay);
			} catch (InterruptedException e) {
			}
		}
		System.out.println(this.body);
	} 
}
