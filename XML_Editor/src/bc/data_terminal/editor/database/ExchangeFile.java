package bc.data_terminal.editor.database;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;

/**
 * класс-обертка, который точно идентифицирует файл обмена (шаблон файла обмена
 * из базы данных) а так же добавлены некоторые утилиты для загрузки данных
 */
public class ExchangeFile {
	private Logger field_logger = Logger.getLogger(this.getClass());
	{
		field_logger.setLevel(Level.DEBUG);
	}
	/**
	 * имена полей из базы данных, по которым могут быть получены значения в
	 * случае положительной загрузки из базы
	 */
	private String[] field_database = new String[] { "ID_EXCHANGE_FILE",
			"CD_EXCHANGE_FILE", "NAME_EXCHANGE_FILE", "IS_INPUT_FILE",
			"DELIMETER", "HAS_COMMENTS", "HAS_HEADER", "NEED_LOG",
			"ID_EXCHANGE_FILE_PATTERN", "CD_EXCHANGE_FILE_PATTERN", "ID_TASK" };
	/** поля со значениями, прочитанными из базы данных */
	private String[] field_values = new String[field_database.length];

	private String field_id_file=null;
	/**
	 * класс-обертка для файла-обмена
	 * 
	 * @param id_file
	 *            - идентификатор файла
	 * */
	public ExchangeFile(String id_file) {
		this.loadFromDataBase(id_file);
		this.field_id_file=id_file;
	}

	/**
	 * класс обертка, следующий оператор для вызова данных по файлу -
	 * loadFromDataBase()
	 */
	public ExchangeFile() {
	}

	/** загрузка данных из базы данных */
	public boolean loadFromDataBase(String id_file) {
		this.field_id_file=id_file;
		boolean return_value = false;
		Connection connection = null;
		try {
			connection = Connector.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT * FROM V_DT_EXCHANGE_FILE where ID_EXCHANGE_FILE="
							+ id_file);
			if (rs.next()) {
				this.clearValues();
				return_value = this.loadFieldFromResultSet(rs);
				return_value = true;
			} else {
				// return_value=false;
			}
			rs.close();
			statement.close();
		} catch (SQLException ex) {
			field_logger.error("loadFromDataBase SQLException:"
					+ ex.getMessage());
		} catch (Exception ex) {
			field_logger.error("loadFromDataBase    Exception:"
					+ ex.getMessage());
		} finally {
			if (connection != null) {
				Connector.closeConnection(connection);
			}
		}
		return return_value;
	}

	/** загрузить (заголовки в объект) поля из ResultSet, получить их значения по умолчанию */
	private boolean loadFieldFromResultSet(ResultSet rs) {
		boolean return_value = false;
		try {
			for (int counter = 0; counter < this.field_database.length; counter++) {
				this.field_values[counter] = rs
						.getString(this.field_database[counter]);
			}
			return_value = true;
		} catch (SQLException ex) {
			field_logger.error("loadFieldFromResultSet  SQLException:"
					+ ex.getMessage());
			return_value = false;
		}
		return return_value;
	}

	private void clearValues() {
		String empty_field = "";
		for (int counter = 0; counter < this.field_values.length; counter++) {
			this.field_values[counter] = empty_field;
		}
	}
	
	/** получить индекс массива по его значению*/
	private int getIndexElementByValue(String[] array, String value){
		int return_value=(-1);
		for(int counter=0;counter<array.length;counter++){
			if(array[counter].equals(value)){
				return_value=counter;
				break;
			}
		}
		return return_value;
	}
	
	/** сохранить заголовки (полученные из HTML) в указанном ID файла <br>  
	 * @param file_id - идентификационный номер файла, в котором нужно сохранить заголовки 
	 * @param headers - заголовки, которые нужно сохранить
		FUNCTION PACK_BC_DT.update_exchange_file( <br>
     		p_id_exchange_file IN NUMBER<br>2<br>
    		,p_cd_exchange_file IN VARCHAR2<br>3<br>
    		,p_name_exchange_file IN VARCHAR2<br>4<br>
    		,p_is_input_file IN VARCHAR2<br>5<br>
    		,p_delimeter IN VARCHAR2<br>6<br>
    		,p_has_comments IN VARCHAR2<br>7<br>
    		,p_has_header IN VARCHAR2<br>8<br>
    		,p_need_log IN VARCHAR2<br>9<br>
    		,p_id_exchange_file_pattern IN NUMBER<br>10<br>
    		,p_id_task IN NUMBER<br>11<br>    		
    		,p_error_string OUT VARCHAR2<br>12<br>
		) RETURN NUMBER;<br>
	 * */
	public boolean saveHeadersIntoFile(ArrayList<ExchangeFieldsPatternHeadElement> headers, String id_task){
		boolean return_value=false;
		this.fillValuesFromHeadElement(headers);
		Connection connection=null;
		try{
			connection=Connector.getConnection();
			CallableStatement statement=(CallableStatement) connection.prepareCall("{? = call PACK_BC_DT.update_exchange_file(?,?,?,?,?,?,?,?,?,?,?)}");
			statement.registerOutParameter(1, Types.VARCHAR);
/*			field_logger.debug("ID_EXCHANGE_FILE:"+this.field_id_file);
			field_logger.debug("CD_EXCHANGE_FILE:"+getValueByName("CD_EXCHANGE_FILE"));
			field_logger.debug("NAME_EXCHANGE_FILE:"+getValueByName("NAME_EXCHANGE_FILE"));
			field_logger.debug("IS_INPUT_FILE:"+getValueByName("IS_INPUT_FILE"));
			field_logger.debug("DELIMETER:"+getValueByName("DELIMETER"));
			field_logger.debug("HAS_COMMENTS:"+getValueByName("HAS_COMMENTS"));
			field_logger.debug("HAS_HEADER:"+getValueByName("HAS_HEADER"));
			field_logger.debug("NEED_LOG:"+getValueByName("NEED_LOG"));
			field_logger.debug("ID_EXCHANGE_FILE_PATTERN:"+getValueByName("ID_EXCHANGE_FILE_PATTERN"));
*/			
			statement.setInt(2, Integer.parseInt(this.field_id_file)); //"ID_EXCHANGE_FILE",
			statement.setString(3, getValueByName("CD_EXCHANGE_FILE")); 
			statement.setString(4, getValueByName("NAME_EXCHANGE_FILE"));
			statement.setString(5, getValueByName("IS_INPUT_FILE"));
			statement.setString(6, getValueByName("DELIMETER"));
			statement.setString(7, (getValueByName("HAS_COMMENTS").equalsIgnoreCase("true")?"Y":"N"));
			statement.setString(8, (getValueByName("HAS_HEADER").equalsIgnoreCase("true")?"Y":"N"));
			statement.setString(9, (getValueByName("NEED_LOG").equalsIgnoreCase("true")?"Y":"N"));
			statement.setString(10, getValueByName("ID_EXCHANGE_FILE_PATTERN"));
			statement.setString(11, id_task);			
			statement.registerOutParameter(12, Types.VARCHAR);
			statement.execute();
			if(statement.getString(1).equals("0")){
				connection.commit();
				return_value=true;
			}else{
				field_logger.debug("saveHeadersIntoFile execute return error result:"+statement.getString(1)+"    return_error:"+statement.getString(11));
				return_value=false;
			}
		}catch(SQLException ex){
			field_logger.debug("saveHeadersIntoFile: SQLException:"+ex.getMessage());
		}catch(Exception ex){
			field_logger.debug("saveHeadersIntoFile:    Exception:"+ex.getMessage());
		}finally{
			// any happens - close connection
			if(connection!=null){
				Connector.closeConnection(connection);
			}
		}
		return return_value;
	}
	
	/** сохранить по данному файлу все поля 
	 * @param - field_element - поля (прочитанные или распарсенные) в нашем поле
	 * 
	 * */
	public boolean saveFieldsIntoFile(ArrayList<ExchangeFieldsPatternField> fields_element){
		boolean return_value=false;
		if(DBFunction.clearFieldsFromExchangeFile(this.field_id_file)){
			if(DBFunction.fillExchangeFile("1", this.field_id_file, fields_element)){
				return_value=true;
			}else{
				field_logger.warn("saveFieldsIntoFile: fillExchangeFile: error");
			}
		}else{
			field_logger.warn("saveFieldsIntoFile: clearFieldsFromExchangeFile: error");
		}
		return return_value;
	}
	
	/** получить значение параметра из массива field_values по его имени из массива field_database*/
	private String getValueByName(String param_name){
		int index_of_element=(-1);
		for(int counter=0;counter<this.field_database.length;counter++){
			if(this.field_database[counter].equals(param_name)){
				index_of_element=counter;
				break;
			}
		}
		if(index_of_element>=0){
			return this.field_values[index_of_element];
		}else{
			return "";
		}
	}
	
	/** наполнить данные field_values значениями из ArrayList<ExchangeFieldsPatternHeadElement>*/
	private void fillValuesFromHeadElement(ArrayList<ExchangeFieldsPatternHeadElement> headers){
		String current_name;
		int current_index;
		// перебрать все элементы заголовка
		for(int counter=0;counter<headers.size();counter++){
			current_name=headers.get(counter).getName();
			current_index=this.getIndexElementByValue(field_database, current_name);
			// если имя элемента найдено - записать в значение первый из его аттрибутов
			if(current_index>=0){
				// element found
				this.field_values[current_index]=headers.get(counter).getFirstAttributeValue();
			}else{
				// element is not found
			}
		}
	}
	
	
	

	/** получить текст "true" или текст "false" если строки эквивалентны
	 * @param compare_source первая строка для сравнения
	 * @param compare_destination  вторая строка для сравнения
	 * @return "true" или "false" (если один из параметров null - "false")
	 * */
	private String getStringBooleanFromValue(String compare_source, String compare_destination){
		String return_value="false";
		try{
			if(compare_source.equalsIgnoreCase(compare_destination)){
				return_value="true";
			}
		}catch(NullPointerException ex){
			// один из параметров null
		}
		return return_value;
	}
	
	/**
	 * получить в виде HTML данные, которые можно было бы залить клиенту в
	 * качестве объекта отображения
	 * 
	 * @param field_header
	 * @return
	 */
	public StringBuffer getHtml(
			ArrayList<ExchangeFieldsPatternHeadElement> field_header) {
		boolean flag_done = false;
		StringBuffer return_value = new StringBuffer();
		// INFO чтение файла из базы данных - установка заголовков
		try{
			this.getFieldHeaderByName(field_header, "DELIMETER").setAttributeValue("value",this.getValueByFieldName("DELIMETER") );
			this.getFieldHeaderByName(field_header, "HAS_COMMENTS").setAttributeValue("exists",this.getStringBooleanFromValue("Y", this.getValueByFieldName("HAS_COMMENTS")) );
			this.getFieldHeaderByName(field_header, "HAS_HEADER").setAttributeValue("exists", this.getStringBooleanFromValue("Y", this.getValueByFieldName("HAS_HEADER")) );
			this.getFieldHeaderByName(field_header, "NEED_LOG").setAttributeValue("full_log", this.getStringBooleanFromValue("Y", this.getValueByFieldName("NEED_LOG")) );
			
			flag_done=true;
		}catch(NullPointerException ex){
			flag_done=false;
		}

		// получить заголовки
/*		String[] headers = new String[] { "DELIMETER", "HAS_COMMENTS",
				"HAS_HEADER", "NEED_LOG" };
		String[] headers_attributes = new String[] { "value", "exists",
				"exists", "full_log" };
		for (int index = 0; index < headers.length; index++) {
			try {
				this.getFieldHeaderByName(field_header, headers[index]).setAttributeValue(headers_attributes[index],
								this.getValueByFieldName(headers[index]));
			} catch (NullPointerException ex) {
				field_logger.error("getHtml: NullPointerException:"
						+ ex.getMessage());
				flag_done = false;
			}
			flag_done=true;
		}
*/		
		if (flag_done == true) {
			// прочитать все поля из базы данных по данному файлу
			ArrayList<ExchangeFileField> fields=DBFunction.getFieldsFromFile(this.field_id_file);
			if(fields!=null){
				return_value.append("<span id=\"header\">");
				// место наполнения кода HTML
					// наполняем заголовки
				for(int counter=0;counter<field_header.size();counter++){
					return_value.append(field_header.get(counter).getHtmlText());
				}
				return_value.append("</span>");
				return_value.append("<span id=\"fields\">");
					// наполняем поля
				for(int counter=0;counter<fields.size();counter++){
					return_value.append(fields.get(counter).getHtml());
				}
				return_value.append("</span>");
				flag_done=true;
			}else{
				field_logger.error("getHtml: not load fields by id_file:"+this.field_id_file);
				flag_done=false;
			}
			// 
		} else {
			field_logger.error("getHtml: Header is Not Full");
		}

		if (flag_done == true) {
			return return_value;
		} else {
			field_logger.error("Html is not full ");
			return null;
		}
	}

	
	/**
	 * ищет в ArrayList первый объект с указанным именем и возвращает его, либо
	 * возвращает null
	 * 
	 * @param field_header
	 *            - ArrayList из объектов Header, в которых нужно искать объект
	 *            с именем
	 * @param name
	 *            - имя объекта, которое нужно искать
	 * @return возвращает либо null, либо искомый элемент
	 * */
	private ExchangeFieldsPatternHeadElement getFieldHeaderByName(
			ArrayList<ExchangeFieldsPatternHeadElement> field_header,
			String name) {
		ExchangeFieldsPatternHeadElement return_value = null;
		for (int index = 0; index < field_header.size(); index++) {
			if (field_header.get(index).isNameEquals(name)) {
				return_value = field_header.get(index);
				break;
			}
		}
		return return_value;
	}
	
	/** получить объект по его имени  
	 * @param headers - параметры в ArrayList объекте
	 * @param element_name - имя искомого элемента 
	 * @return null - либо возвращает объект, либо возвращает null
	 * */
	public ExchangeFileHeadElement getHeadElementByName(ArrayList<ExchangeFileHeadElement> headers,String element_name){
		ExchangeFileHeadElement return_value=null;
		for(int counter=0;counter<headers.size();counter++){
			if(headers.get(counter).equals(element_name)){
				return_value=headers.get(counter);
				break;
			}
		}
		return return_value;
	}
	

	/**
	 * получить значение поля по его имени
	 * 
	 * @param field_name
	 *            - имя поля из базы данных
	 * */
	private String getValueByFieldName(String field_name) {
		String return_value = "";
		for (int counter = 0; counter < this.field_database.length; counter++) {
			if (this.field_database[counter].equals(field_name)) {
				return_value = this.field_values[counter];
			}
		}
		return return_value;
	}
}
