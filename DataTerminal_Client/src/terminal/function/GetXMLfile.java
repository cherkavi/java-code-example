package terminal.function;

import terminal.transfer.GetFile;
import terminal.transport.Data;
import terminal.transport.Task;
import terminal.transport.Transport;

/** класс который является функцией получения файла XML с разрешениями отображения визуальных элементов */
public class GetXMLfile extends Function{
	private String field_login;
	private String field_password;
	/** объект, содержащий интерфейс, которому нужно передать файл после получения */
	private GetFile field_get_file;
	
	/** 
	 * Послать запрос на сервер и получить от него XML файл с разрешениями
	 * @param login логин клиента
	 * @param password пароль клиента 
	 * @param get_file объект, которому следует передать управление 
	 */
	public GetXMLfile(String login, 
					  String password,
					  GetFile get_file) {
		super(null, "GetXMLfile", "Получение файла XML");
		this.field_login=login;
		this.field_password=password;
		this.field_get_file=get_file;
	}

	@Override
	protected Transport getFirstTransport() {
		Task task=new Task("GET_XML_FILE");
		task.addData(new Data("login",this.field_login));
		task.addData(new Data("password",this.field_password));
		return new Transport(task);
	}

	@Override
	public void run() {
		try{
			Transport response=sendFirstTask();
			if(response!=null){
				if(response.getTask()!=null){
					if(response.getTask().getData()!=null){
						debug("Data Name: "+response.getTask().getData().getDataName());
						this.field_get_file.getFile("GetXMLfile", 
													response.getTask().getData().getDataName(), 
													(byte[])response.getTask().getData().getObject()
													);
					}else{
						error("response.Task not consists Data");
					}
				}else{
					error("response not consists Task ");
				}
			}else{
				error("response is null");
			}
		}catch(Exception ex){
			error("run Exception:"+ex.getMessage());
		}
	}
	
}
