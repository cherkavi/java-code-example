package com.vitaliy.rs485_module;
import java.io.*;
/**
 * отвечает за посылку и обработку команд, постановку команд в очередь
 * контролирует поступающие ответы, или же не поступающие
 * точка входа -  
 */
public class Processor{
	private Queue queue;// очередь команд
	private OutputStream outputstream;
	private Timer timer;// если таймер в состоянии пуска (Timer.isRun()), значит мы ждем приход ответа из порта
	private int delay_wait_answer=200;// время ожидания ответа из порта в милисекундах
	private int unique_number=0;// счетчик уникальных команд
	private int task_index=(-1);
	private Command current_command=null;
	private Ievent_work current_event_work=null;

	/**
	 * поток, в который будем записывать команды
	 * @param _outputstream
	 */
	public Processor(OutputStream _outputstream){
		this.outputstream=_outputstream;
		this.queue=new Queue();
	}
	/**
	 * Добавить задачу в очередь задач
	 */
	public void add_to_queue(Command object_of_command,int time_delay,Ievent_work _event_work){
		//(String _name_unique, Command _object_of_command, int _time_delay,Ievent_work _event_work){
		String unique_name="";
		try{
			String temp_name=object_of_command.getClass().getName().trim();
			int dot_position=temp_name.lastIndexOf(".");
			if(dot_position>0){
				unique_name=this.get_unique_name(temp_name.substring(dot_position+1));
			}
			else{
				unique_name=this.get_unique_name(temp_name);
			}
		}
		catch(Exception e){
			unique_name=this.get_unique_name("");
		};
		
		if(time_delay<0){
			time_delay=0;
		}
		this.queue.add(unique_name, object_of_command, time_delay, _event_work);
	}
	/**
	 * удаление задачи из очереди и из текущего объекта
	 * @param delete_index
	 */
	public void delete_from_queue(int delete_index){
		this.queue.delete_by_index(this.task_index);// удаляем задачу из очереди
		this.current_command=null;
		this.current_event_work=null;
		if(this.timer.isRun()){
			this.timer.breakTimer();
		}
		this.timer=null;
		System.out.println("Processor.delete has_next_task?"+this.queue.length());
	}
	/**
	 * Получить уникальное имя команды для распознавания
	 * вид уникального имени:  [имя команды][.][цифровое значение]
	 */
	private String get_unique_name(String command_name){
		if(unique_number>32000){
			unique_number=0;
		}
		return command_name+"."+(++this.unique_number);
	}
	/**
	 * Запись команды в OutputStream
	 * @param s
	 * @return
	 */
	private boolean send_command(String s){
		boolean result=false;
		try{
			this.outputstream.write(s.getBytes());
			this.outputstream.write(13);
			this.outputstream.write(10);
			this.outputstream.flush();
		}
		catch(Exception e){
			result=false;
		}
		return result;
	}
	/**
	 * Получить объект команды по его уникальному индексу
	 */
	private Command get_command_from_queue(int index_of_command){
		return this.queue.get_object_of_command_by_index(index_of_command);
	}
	/**
	 * Точка входа в объект - обработка входящих сигналов - ответа из порта 
	 */
	public void response_process(String response){
		// мы ожидаем ответа из порта?
		if((this.timer!=null)&&(this.timer.isRun())){
			if(response.length()>0){
				// пришел не пустой ответ - нужно обработать входящие данные
				if(this.current_event_work!=null){
					System.out.println("Processor.response_process: пришел не пустой ответ"+response);
					this.current_event_work.event_work(this.get_command_from_queue(this.task_index),response);
				}
				// удаляем задачу
				this.delete_from_queue(this.task_index);
			}
			else {
				// ответа нет - ожидаем 
			}
		}
		else {
			// нет, не ожидаем - можно посылать новую команду - задачи не было
			// или время вышло - модуль не ответил, 
			if(this.current_command==null){
				// задачи не было - можно добавлять новую задачу в очередь
				if(queue.has_next_task()){
					// задача есть - добавляем
					this.task_index=this.queue.get_index_of_next_task();
					this.current_command=this.queue.get_object_of_command_by_index(this.task_index);
					this.current_event_work=this.queue.get_object_of_work_by_index(this.task_index);
					// команда взята из очереди?
					if(this.current_command!=null){
						// да, команда взята из очереди 
						   //- устанавливаем таймер
						this.timer=new Timer(this.delay_wait_answer);
						   //- посылаем данные в порт
						System.out.println("Посылка данных в порт:");
						this.send_command(this.current_command.get_command());
					}
					else{
						// нет команды для отправки- нужно удалить данную команду и ожидать ответа
						this.delete_from_queue(this.task_index);
					}
				}
				else{
					// задачи нет, ожидаем пока задача будет добавлена в очередь
				}
			}
			else {
				// current_command!=null - модуль не ответил
				   // пришел пустой ответ - 
				if(this.current_event_work!=null){
					this.current_event_work.event_work(this.get_command_from_queue(this.task_index),"");
					System.out.println("модуль не ответил");
				}
				// удаляем задачу из очереди
				this.delete_from_queue(this.task_index);
			}
		}
	}
	
}
