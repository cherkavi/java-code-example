package gui;

/** общий объект для всех графических окон, который несет в себе необходимые ссылки на объекты */
public class CommonObject {
	/** путь к файлу XML, который содержит необходимые настройки */
	private String PathToXml;
	/**
	 * @return the pathToXml
	 */
	public String getPathToXml() {
		return PathToXml;
	}
	/**
	 * @param pathToXml the pathToXml to set
	 */
	public void setPathToXml(String pathToXml) {
		PathToXml = pathToXml;
	}

	/** общий объект для всех графических окон, который несет в себе необходимые ссылки на объекты */
	public CommonObject(){
		
	}
}
