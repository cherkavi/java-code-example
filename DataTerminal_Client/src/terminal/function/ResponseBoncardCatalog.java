package terminal.function;

import terminal.transfer.*;
import terminal.transport.Data;
import terminal.transport.Task;
import terminal.transport.Transport;

/** данный класс отвечает за посылку на сервер запроса о текущем каталоге и получения ответа от сервера*/
public class ResponseBoncardCatalog extends Function{
	/** полный путь к каталогу, который нужно сохранить */
	private String field_path_to_directory;
	
	/**
	 * Послать информацию на сервер - имя каталога для хранения на клиенте присланных сервером "очередных" файлов - 
	 * информации об изменениях клиенто в системе БонКлуб
	 * @param path_to_directory - путь к каталогу, который нужно сохранить
	 * @param object_for_process - объект, которому нужно передать процент выполнения задачи  (null - не оповещаем )
	 * @param unique_id - уникальное имя для данного процесса  ( для объекта object_for_process)
	 * @param caption - caption для отображения пользователю имени 
	 */
	public ResponseBoncardCatalog(String path_to_directory,
								  PercentReport object_for_process,
			                      String unique_id,
			                      String caption){
		super(null,
			  object_for_process,
			  unique_id,
			  caption);
		this.field_path_to_directory=path_to_directory;
	}
	
	/**
	 * Получить от сервера имя каталога для хранения на клиенте присланных сервером "очередных" файлов - 
	 * информации об изменениях клиенто в системе БонКлуб 
	 * @param path_to_server полный путь к серверу
	 * @param path_to_directory - путь к каталогу, который нужно передать на сервер
	 * @param object_for_process - объект, которому нужно передать процент выполнения задачи  (null - не оповещаем )
	 * @param unique_id - уникальное имя для данного процесса ( для объекта object_for_process)  
	 *
	public ResponseBoncardCatalog(String path_to_server,
			                     String path_to_directory, 
			                     PercentReport object_for_process,
			                     String unique_id,
			                     String caption ){
		super(path_to_server,object_for_process,unique_id,caption);
		this.field_path_to_directory=path_to_directory;
	}
	*/
	

	@Override
	public void run() {
		debug("run:begin");
		notifyReportPercent(5);
		Transport response=this.sendFirstTask();
		debug("show result");
		try{
			if((response.isDirectionFromServer())&&(response.getTask().isStateIsDone())){
				debug("response is DONE ");
				notifyReportPercent(100);
			}else{
				debug("response is ERROR ");
				notifyReportPercent(-1);
			}
		}catch(NullPointerException ex){
			// response.getTask()==null или response.getTask().getData()==null
			error(ex.getMessage());
			notifyReportPercent(-1);
		}
		debug("run:end");
		
	}

	
	
	/** создать объект, который будет послан на сервер в качестве инициализации запроса на получение каталога */
	@Override
	protected Transport getFirstTransport() {
		Transport request=new Transport(new Task("CLIENT_RESPONSE_BONCARD_CATALOG",
				                        new Data(this.field_path_to_directory))
		                                );
		request.setDirectionFromClient();
		return request;
	}
	
}













