package terminal.function;

import terminal.transfer.*;
import terminal.transport.Task;
import terminal.transport.Transport;

/** данный класс отвечает за посылку на сервер запроса о текущем каталоге и получения ответа от сервера*/
public class RequestBoncardCatalog extends Function{
	/** объект, которому нужно передать полученный каталог */
	private GetString field_return_response=null;
	
	
	/**
	 * Получить от сервера имя каталога для хранения на клиенте присланных сервером "очередных" файлов - 
	 * информации об изменениях клиенто в системе БонКлуб
	 * @param object_for_answer - объект, которому нужно передать результат выполнения
	 * @param object_for_process - объект, которому нужно передать процент выполнения задачи  (null - не оповещаем )
	 * @param unique_id - уникальное имя для данного процесса  ( для объекта object_for_process)
	 * @param caption - caption для отображения пользователю имени 
	 */
	public RequestBoncardCatalog(GetString object_for_answer, 
			                     PercentReport object_for_process,
			                     String unique_id,
			                     String caption){
		super(null,object_for_process,unique_id,caption);
		this.field_return_response=object_for_answer;
	}
	
	/*
	 * Получить от сервера имя каталога для хранения на клиенте присланных сервером "очередных" файлов - 
	 * информации об изменениях клиенто в системе БонКлуб 
	 * @param path_to_server полный путь к серверу
	 * @param object_for_answer - объект, которому нужно передать результат выполнения
	 * @param object_for_process - объект, которому нужно передать процент выполнения задачи  (null - не оповещаем )
	 * @param unique_id - уникальное имя для данного процесса ( для объекта object_for_process)  
	 *
	public RequestBoncardCatalog(String path_to_server,
			                     GetString object_for_answer, 
			                     PercentReport object_for_process,
			                     String unique_id,
			                     String caption ){
		super(path_to_server,object_for_process,unique_id,caption);
		this.field_return_response=object_for_answer;
	}
	*/
	

	@Override
	public void run() {
		debug("run:begin");
		notifyReportPercent(5);
		Transport response=this.sendFirstTask();
		try{
			if((response.isDirectionFromServer())&&(response.getTask().isStateIsDone())){
				
				debug("response is DONE:"+(String)response.getTask().getData().getObject());
				this.field_return_response.getString(this.getUniqueId(), 
													(String)response.getTask().getData().getObject());
				
				notifyReportPercent(100);
			}else{
				debug("response is ERROR ");
				this.field_return_response.getString(this.getCaption());
				notifyReportPercent(-1);
			}
		}catch(NullPointerException ex){
			// response.getTask()==null или response.getTask().getData()==null
			this.field_return_response.getString(this.getCaption());
			error(ex.getMessage());
			notifyReportPercent(-1);
		}
		debug("run:end");
	}

	
	
	/** создать объект, который будет послан на сервер в качестве инициализации запроса на получение каталога */
	@Override
	protected Transport getFirstTransport() {
		Transport request=new Transport(new Task("CLIENT_REQUEST_BONCARD_CATALOG"));
		request.setDirectionFromClient();
		return request;
	}
	
}













