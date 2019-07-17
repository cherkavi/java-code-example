package terminal.function;

import terminal.transfer.PercentReport;
import terminal.transport.Data;
import terminal.transport.Task;
import terminal.transport.Transport;
import java.io.*;

/** отправка на сервлет файла */
public class ResponseOperation extends Function{
	/** полный путь к файлу, который нужно передать на сервер*/
	private String field_path_to_file;
	
	public ResponseOperation(String path_to_file,
							 PercentReport object_for_process,
							 String unique_id, 
							 String caption) {
		super(object_for_process, unique_id, caption);
		this.field_path_to_file=path_to_file;
	}

	@Override
	protected Transport getFirstTransport() {
		Transport return_value=null;
		try{
			return_value=new Transport(new Task("CLIENT_RESPONSE_OPERATION"));
			File file=new File(this.field_path_to_file);
			if(file.exists()){
				return_value.getTask().addData(new Data(file.getName(),this.getZipBytesFromFile(field_path_to_file)));
			}else{
				// файл не найден, отправка Object=null
				return_value.getTask().addData(new Data(null));
			}
		}catch(Exception ex){
			error("getFirstTransport: Exception:"+ex.getMessage());
			return_value=null;
		}
		return return_value;
	}

	@Override
	public void run() {
		notifyReportPercent(5);
		Transport response=this.sendFirstTask();
		try{
			if((response!=null)&&(response.isDirectionFromServer())){
				debug("File is sended");
				if(response.getTask().isStateIsDone()){
					debug("File is saved ");
					notifyReportPercent(100);
				}else{
					debug("File not saved ");
					notifyReportPercent(-1);
				}
			}else{
				notifyReportPercent(-1);
			}
		}catch(Exception ex){
			notifyReportPercent(-1);
		}
	}
	
}
