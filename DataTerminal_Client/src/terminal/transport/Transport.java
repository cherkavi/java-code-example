package terminal.transport;

import java.io.Serializable;
import java.util.ArrayList;

public class Transport implements Serializable{
	/** */
	private static final long serialVersionUID = 1L;
	/** хранилище для задач */
	private ArrayList<Task> field_task=new ArrayList<Task>();
	
	/** флаг направления - от сервера к клиенту, либо от клиента к серверу */
	private int field_flag_direction=0;
	private static int DIRECTION_FROM_CLIENT=0;
	private static int DIRECTION_FROM_SERVER=1;
	
	
	/** Транспорт для общения между клиентом и сервером */
	public Transport(){
	}
	
	/** Транспорт для общения между клиентом и сервером */
	public Transport(Task task){
		this.addTask(task);
	}
	
	/** добавить задачу в пакет */
	public void addTask(Task task){
		this.field_task.add(task);
	}
	
	/** получить первую задчу из списка, если она есть 
	 * @return List<Task>[0] или null, если список задач пуст 
	 *  
	 */
	public Task getTask(){
		if(this.getTaskCount()>0){
			return this.getTask(0);
		}else{
			return null;
		}
	}
	
	/** получить задачу из пакета */
	public Task getTask(int number){
		if((number<this.getTaskCount())&&(number>=0)){
			return this.field_task.get(number);
		}else{
			return null;
		}
	}
	
	/** получить кол-во задач */
	public int getTaskCount(){
		return this.field_task.size();
	}
	
	/** установить направление от Клиента к серверу */
	public void setDirectionFromClient(){
		this.field_flag_direction=Transport.DIRECTION_FROM_CLIENT;
	}
	
	/** установить направление от Сервера к клиенту */
	public void setDirectionFromServer(){
		this.field_flag_direction=Transport.DIRECTION_FROM_SERVER;
	}
	
	/** направление от Клиента к серверу ?*/
	public boolean isDirectionFromClient(){
		return (this.field_flag_direction==Transport.DIRECTION_FROM_CLIENT);
	}
	
	/** направление от Сервера к клиенту ?*/
	public boolean isDirectionFromServer(){
		return (this.field_flag_direction==Transport.DIRECTION_FROM_SERVER);
	}
}
