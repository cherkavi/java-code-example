package terminal.transfer;

import terminal.transport.Task;

/** интерфейс, которому передается результирующий Task, который получен в ответ на запрос*/
public interface GetTask {
	/** передать для дальнейшей обработки полученный Task*/
	public void getTask(Task task);
}
