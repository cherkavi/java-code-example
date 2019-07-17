package com.vitaliy.rs485_module;

public class main_class implements Ievent_timer,Ievent_work{
	//public Queue queue;
	public volatile String data="";
	public Processor processor=new Processor(System.out);
	public Timer timer;
	public main_class(){
		// проверка Queue()
		/*this.queue=new Queue();
		this.queue.add("unique_name1",  new AANNTTCCFF(), 100, null);
		this.queue.add("unique_name2", null, 200, null);
		this.queue.add("unique_name3", null, 300, null);
		this.queue.add("unique_name4", null, 400, null);
		this.queue.print();
		this.queue.delete_by_index(this.queue.get_index_by_name_unique("unique_name3"));
		System.out.println("get_name_of_class:"+this.queue.get_name_of_class_by_index(0));
		this.queue.print();
		System.out.println("-----------");
		this.queue.print_by_index(this.queue.get_index_by_name_unique("unique_name4"));
		this.queue.add("unique_name5", null, 500, null);		
		System.out.println("-----------");
		this.queue.set_time_delay_by_unique_name("unique_name4", 0);
		this.queue.print();
		
	
		while(this.queue.isNotEmpty()){
			int index=this.queue.get_index_of_next_task();
			if(index>=0){
				System.out.println("Task:"+this.queue.get_index_of_next_task());
				this.queue.print_by_index(index);
				this.queue.delete_by_index(index);
			}
		}*/
		this.timer=new Timer(1000,this,"%AANN");
		this.timer=new Timer(3000,this,"%ССЕЕ");
		
		this.processor.add_to_queue(new AA0(), 2000, this);
		this.processor.add_to_queue(new AANNTTCCFF(), 4000, this);
		while(true){
			this.processor.response_process(data);
		}
	}
	/**
	 *  метод интерфейса, который будет вызван при срабатывании таймера
	 *  то есть при ответе из порта 
	 */
	public synchronized void event_timer(String event_name) {
		this.data=event_name;
		System.out.println("Event:"+event_name);
	}
	
	public static void main(String args[]){
		new main_class();
	}
	/**
	 *  метод интерфейса, который будет вызван при полученнии ответа от Command
	 *  то есть при ответе из порта 
	 */
	public synchronized void event_work(Command command, String param) {
		System.out.print("Event_work: Command:"+command.get_name()+" Params:"+param);
		if(command!=null){
			System.out.println(" result="+command.response_is_valid(param));
		}
		if(command.get_name().equals("AANNTTCCFF")){
			this.processor.add_to_queue(new AANNTTCCFF(), 4000, this);
		}
		this.data="";
	}
}
