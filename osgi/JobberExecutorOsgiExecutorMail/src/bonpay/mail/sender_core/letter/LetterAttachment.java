package bonpay.mail.sender_core.letter;

/** объект, который идентифицирует присоединенные файлы  */
public class LetterAttachment {
	private String pathToFile;
	private String originalFileName;
	
	/** объект, который идентифицирует присоединенные файлы  
	 * @param originalName - оригинальное имя файла
	 * @param pathToFile - полный путь к файлу
	 */
	public LetterAttachment(String originalName, String pathToFile){
		this.originalFileName=originalName;
		this.pathToFile=pathToFile;
	}

	/** полный путь к файлу */
	public String getPathToFile() {
		return pathToFile;
	}

	/** оригинальное название файла  */
	public String getOriginalFileName() {
		return originalFileName;
	}

	/** установить оригинальное имя файла  */
	public void setOriginalFileName(String name) {
		this.originalFileName=name;
	}
	
}
