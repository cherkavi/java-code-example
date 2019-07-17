package terminal.transfer;

/** данный интерфейс предназначен для получения объектом ответа от сервера в виде строки */
public interface GetString {
	/**
	 * Ответ от сервера получен 
	 * @param taskName имя задачи, которая передала данные
	 * @param value значение, которое должно быть передано данной системе
	 */
	public void getString(String taskName, String value);

	/** 
	 * Не получен ответ от сервера, либо полуна ошибка обработки Task.STATE_ERROR
	 * @param taskName имя задачи, которая передала данные
	 * @param value значение, которое должно быть передано данной системе
	 */
	public void getString(String taskName);
	
}
