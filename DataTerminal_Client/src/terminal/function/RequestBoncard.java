package terminal.function;

import terminal.transfer.GetFile;
import terminal.transfer.PercentReport;
import terminal.transport.Data;
import terminal.transport.Task;
import terminal.transport.Transport;

/** получение с сервера очередной группы файлов, которая еще не была загружена*/
public class RequestBoncard extends Function{
	/** интерфейс по сохранению файлов */
	GetFile field_get_file;
	/** уникальные идентификаторы для файлов, чтобы можно было получить перечисленные в списке значения */
	String[] field_id_server_files=null;

	/** получить последние не загруженные файлы 
	 * @param get_file - интерфейс по сохранению файлов на диске
	 * @param object_for_process - объект, которому можно сообщать о процессе выполнения
	 * @param unique_id - уникальный идентификатор для данного процесса
	 * @param caption - заголовок для отображения процесса 
	 * */
	public RequestBoncard(GetFile get_file,
						  PercentReport object_for_process, 
						  String unique_id,
						  String caption) {
		super(object_for_process, unique_id, caption);
		this.field_get_file=get_file;
	}
	
	/** получить файлы, которые уже были загружены
	 * @param id_files - идентификаторы файлов, которые пользователь хочет получить
	 * @param get_file - интерфейс по сохранению файлов на диске
	 * @param object_for_process - объект, которому можно сообщать о процессе выполнения
	 * @param unique_id - уникальный идентификатор для данного процесса
	 * @param caption - заголовок для отображения процесса 
	 * */
	public RequestBoncard(String[] id_files, 
			  			  GetFile get_file,
					      PercentReport object_for_process, 
			  			  String unique_id,
			  			  String caption) {
		super(object_for_process, unique_id, caption);
		this.field_get_file=get_file;
		this.field_id_server_files=id_files;
	}

	@Override
	protected Transport getFirstTransport() {
		Transport return_value=new Transport();
		if((this.field_id_server_files!=null)&&(this.field_id_server_files.length>0)){
			// нужно получить файлы для повторной загрузки
			return_value.addTask(new Task("CLIENT_REQUEST_BONCARD",new Data(this.field_id_server_files)));
		}else{
			// нужно получить новые файлы
			return_value.addTask(new Task("CLIENT_REQUEST_BONCARD"));
		}
		return return_value;
	}

	@Override
	public void run() {
		this.notifyReportPercent(5);
		Transport response=this.sendFirstTask();
		if(response!=null){
			if(response.getTask()!=null){
				if(response.getTask().getData()!=null){
					try{
						for(int counter=0;counter<response.getTask().getDataCount();counter++){
							debug("counter:"+counter);
							this.field_get_file.getFile("CLIENT_REQUEST_BONCARD", 
									    			    response.getTask().getData(counter).getDataName(), 
									    			    (byte[])response.getTask().getData(counter).getObject()
									    			    );
							notifyReportPercent((100-5)/response.getTask().getDataCount()*(1+counter));
						}
						notifyReportPercent(100);
					}catch(Exception ex){
						error(" exception GetFile:"+ex.getMessage());
						notifyReportPercent(-1);
					}
				}else{
					error("server Transport.Task.Data is null");
					notifyReportPercent(-1);
				}
			}else{
				error("server Transport.Task is null");
				notifyReportPercent(-1);
			}
		}else{
			error("server Transport null");
			notifyReportPercent(-1);
		}
	}
}
