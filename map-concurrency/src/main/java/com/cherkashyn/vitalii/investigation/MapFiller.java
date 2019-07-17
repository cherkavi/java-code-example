package com.cherkashyn.vitalii.investigation;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MapFiller extends Thread{

	private static final int MAX_COUNTER = 10;
	private Set<String> body;
	private int delay;
	
	public MapFiller(Set<String> value){
		this(value,1);
	}
	
	public MapFiller(Set<String> value, int secondsDelay){
		this.body=value;
		this.delay=secondsDelay;
	}
	
	@Override
	public void run() {
		for(int index=0;index<MAX_COUNTER; index++){
			String valueForAdd=Integer.toString(index);
			System.out.println("try to add "+valueForAdd);
			this.body.add(valueForAdd);
			System.out.println(String.format("value was added %s ",valueForAdd));
			try {
				TimeUnit.SECONDS.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
		
	}
}
