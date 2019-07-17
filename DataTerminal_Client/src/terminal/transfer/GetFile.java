package terminal.transfer;

/** интерфейс, который отвечает за преобразование полученных данных от сервера в файлы */
public interface GetFile {
	/** 
	 * получение файлов в виде byte[] и их преобразование в файлы на диске
	 * @param taskName - имя задачи, которая инициирует данный процесс
	 * @param pathName - путь к каталогу, в котором должно произойти сохранение
	 * @param fileName - имя файла, в котором должны содержаться данные
	 * @param data - данные в виде массива байт
	 * */
	public void getFile(String taskName, 
						String pathName, 
						String fileName, 
						byte[] data);

	/** 
	 * получение файлов в виде byte[] и их преобразование в файлы на диске
	 * @param taskName - имя задачи, которая инициирует данный процесс
	 * @param fileName - имя файла, в котором должны содержаться данные
	 * @param data - данные в виде массива байт
	 * */
	public void getFile(String taskName, String fileName, byte[] data);
}
