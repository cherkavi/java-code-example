package bonpay.osgi.service.interf;

/** интерфейс, который регестрирует IExecutor-ы на сервисе Launcher */
public interface IModuleExecutor {
	/** добавить сервис, который можно запускать по имени 
	 * @param executorName - имя-идентификатор
	 * @param executor - запускаемый объект
	 * @return 
	 * <li> <b>true</b> - усшено добавлен</li>
	 * <li> <b>false</b> - ошибка добавления </li>
	 */
	public boolean addModuleExecutor(String executorName, IExecutor executor);
	/** удалить Executor на основании уникального имени */
	public boolean removeModuleExecutor(String executorName);
	/** удалить Executor на основании уникального объекта */
	public boolean removeModuleExecutor(IExecutor executor);
	/** получить Executor по уникальному имени 
	 * @param executorName - уникальное имя исполнителя 
	 * @return null - если данное имя не найдено 
	 */
	public IExecutor getExecutorByName(String executorName);
	/** получить имена всех Executor-ов
	 * @return - все зарегестрированные имена в системе 
	 */
	public String[] getNameOfExecutors();
	
}
