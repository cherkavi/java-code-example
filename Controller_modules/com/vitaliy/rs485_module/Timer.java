package com.vitaliy.rs485_module;
/**
 * Организовывает задержку в милисекундах и через заданное время вызывает событие объекта 
 */
public class Timer implements Runnable{
	private Thread thread=null;
	private int miliseconds=0;
	private Ievent_timer object_event=null;
	private String event_name="";
	/**
	 * @param _miliseconds - время задержки в милисекундах 
	 * @param _object_event - объект, у которого будет вызван метод интерфейса Ievent_timer
	 * @param _event_name - имя события, которое будет передано в реализацию интерфейса Ievent_timer
	 */
	public Timer(int _miliseconds,Ievent_timer _object_event, String _event_name){
		if(_miliseconds!=0){
			if(_miliseconds<0){
				this.miliseconds=_miliseconds*(-1);
			}
			else {
				this.miliseconds=_miliseconds;
			}
			this.object_event=_object_event;
			this.event_name=_event_name;
			this.thread=new Thread(this);
			this.thread.start();
		}
		else{
			// Time=0
			if(_object_event!=null){
				_object_event.event_timer(_event_name);
			}
		}
	}
	public Timer(int _miliseconds){
		this(_miliseconds,null,null);
	}
	/**
	 * возвращает true, если таймер находится в состоянии отсчета
	 * @return
	 */
	public boolean isRun(){
		return this.thread.isAlive();
	}
	/**
	 * прерывает таймер из состояния отсчета
	 */
	public void breakTimer(){
		if(this.thread.isAlive()){
			this.thread.interrupt();
		}
	}
	public void run(){
		try{
			Thread.sleep(this.miliseconds);
			// отработка необходимого действия
			if(this.object_event!=null){
				this.object_event.event_timer(this.event_name);
			}
			System.out.println("Timer.run timer end:"+this.event_name);
		}
		catch(Exception e){
			System.out.println("Timer.run timer "+this.event_name+" interrupted:"+e.getMessage());
		}
		
	}
}
