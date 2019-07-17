package reporter;

import java.io.File;
import java.sql.ResultSet;

/** класс который отображает строки ResultSet согласно файлу шаблону с переданными в него переменными */
public class HtmlReport {
	private File patternFile;
	/** отображение ResultSet на основании шаблона с данными 
	 * @param pathToPattern - путь к файлу-шаблону, дл€ заполнени€ данными
	 * @throws Exception если не удалось обнаружить файл-шаблон
	 * */
	public HtmlReport(String pathToPattern) throws Exception {
		patternFile=new File(pathToPattern);
		if(patternFile.exists()==false){
			throw new Exception("File not exists:"+pathToPattern);
		}
	}

	/** вывести отчет из ResultSet в указанный файл 
	 * @param resultSet - набор данных, по которому нужно создать отчет 
	 * @param pathToFile - файл, в котором должны эти данные быть сохранены 
	 * @throw Exception - если возникла кака€-либо ошибка   
	 * */
	public void outReportToFile(ResultSet resultSet,String pathToFile ) throws Exception {
	}
	
	
	
}
