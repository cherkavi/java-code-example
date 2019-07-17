package terminal.function;

import terminal.transfer.GetTask;
import terminal.transfer.PercentReport;
import terminal.transport.Task;
import terminal.transport.Transport;

/** загрузить Log Бон карт с сервера */
public class RequestLogBoncard extends Function{
	
	/** объект, который содержит интерфейс обработки Task с полученными значениями в представление таблицы */
	private GetTask field_task_table;
	
	/**
	 * Загрузить лог загрузок новых/измененных БонКарт с сервера
	 * @param task_table - объект, который реализует интерфейс преобразования Task в JTable
	 * @param object_for_process объект, который уведомляется о процессе выполнения
	 * @param unique_id - уникальный идентификатор для уведомления о выполненном процессе 
	 * @param caption - заголовок для вывода информации о процессе 
	 */
	public RequestLogBoncard(GetTask task_table,
							 PercentReport object_for_process,
							 String unique_id, 
							 String caption) {
		super(object_for_process, 
			  unique_id, 
			  caption);
		this.field_task_table=task_table;
	}

	@Override
	protected Transport getFirstTransport() {
		Transport return_value=new Transport(new Task("CLIENT_REQUEST_LOG_BONCARD"));
		return return_value;
	}

	@Override
	public void run() {
		notifyReportPercent(5);
		Transport request=this.sendFirstTask();
		if((request!=null)&&(request.getTask()!=null)){
			this.field_task_table.getTask(request.getTask());
			notifyReportPercent(100);
		}else{
			notifyReportPercent(-1);
		}
	}
}
