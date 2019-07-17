package terminal.function;

import terminal.transfer.GetFile;
import terminal.transfer.PercentReport;
import terminal.transport.Task;
import terminal.transport.Transport;


public class RequestSettings extends Function{

	/** интерфейс, который содержит функции по сохранению полученного файла на диске */
	private GetFile field_get_file;
	
	/** посылка на сервер запроса о текущих настройках для данной системы 
	 * @param getFile - интерфейс для сохранения полученных файлов на диске
	 * @param object_for_process - интерфейс для вывода служебной информации 
	 * @param unique_id - уникальный идентификатор задачи для вывода процесса выполнения 
	 * @param caption - заголовок для данной задачи 
	 **/
	public RequestSettings(GetFile getFile,
						   PercentReport object_for_process, 
						   String unique_id,
						   String caption) {
		super(object_for_process, unique_id, caption);
		this.field_get_file=getFile;
	}

	@Override
	protected Transport getFirstTransport() {
		return new Transport(new Task("CLIENT_REQUEST_SETTINGS"));
	}

	@Override
	public void run() {
		this.notifyReportPercent(5);
		Transport response=this.sendFirstTask();
		if(response!=null){
			if(response.getTask()!=null){
				if(response.getTask().getData()!=null){
					try{
						this.field_get_file.getFile("CLIENT_REQUEST_SETTINGS", 
							    response.getTask().getData().getDataName(), 
							    (byte[])response.getTask().getData().getObject());
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
