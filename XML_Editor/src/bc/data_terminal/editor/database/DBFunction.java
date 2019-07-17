package bc.data_terminal.editor.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;

import oracle.jdbc.internal.OracleCallableStatement;

import org.apache.log4j.*;
import org.w3c.dom.Document;

/** класс, который хранит данные для пользователей */
public class DBFunction {
	private static Logger field_logger = Logger.getLogger(DBFunction.class);
	static {
		field_logger.setLevel(Level.DEBUG);
		BasicConfigurator.configure();
		// if(!field_logger.getAllAppenders().hasMoreElements()){}
	}

	/**
	 * создать файл-шаблон для XML данных, которыми обменивается клиент и
	 * сервер:
	 * 
	 * @param p_id_exchange_file_pattern
	 *            - уникальный идентификатор шаблона
	 * @param p_cd_exchange_file
	 *            - название для возможного HTML.name
	 * @param p_name_exchange_file
	 *            - название файла обмена (win1251)
	 * @param id_task
	 *            - уникальный номер Task(задачи) в нашей системе
	 * @param p_is_input_file
	 *            - 'Y' - файл принимаем от клиента, 'N' - файл выливаем на
	 *            клиента
	 * @param p_delimeter
	 *            - разделитель для CSV файла
	 * @param p_has_comments
	 *            - нужны ли комментарии ('Y','N')
	 * @param p_has_header
	 *            - есть ли заголовок в CSV файле ('Y','N')
	 * @param p_need_log
	 *            - нужно ли логгирование ('Y','N')
	 * @param p_copy_fields_from_pattern
	 *            - ('Y'- скопировать все поля из шаблона
	 *            p_id_exchange_file_pattern)
	 * @return null - файл не создан, либо же файл создан
	 */

	public static String createExchangeFile(String p_id_exchange_file_pattern,// 2
			String p_cd_exchange_file, // 3
			String p_name_exchange_file, // 4
			String p_is_input_file, // 5
			String p_delimeter, // 6
			String p_has_comments, // 7
			String p_has_header,// 8
			String p_need_log, // 9
			String p_id_task // 10
	) {
		String return_value = null;
		Connection connection = null;
		try {
			connection = Connector.getConnection();
			field_logger.debug("createExchangeFile: create statement");
			// String query=
			// "declare l_res number; begin l_res := PACK_BC_APPLET.get_param_value(?, ?, ?,?); end;"
			// ;
			String query = "{? = call PACK_BC_DT.add_exchange_file(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			OracleCallableStatement statement = (OracleCallableStatement) connection
					.prepareCall(query);
			field_logger.debug("createExchangeFile: set parameters");
			statement.registerOutParameter(1, Types.VARCHAR);
			statement.setInt(2, Integer.parseInt(p_id_exchange_file_pattern));
			statement.setString(3, p_cd_exchange_file);
			statement.setString(4, p_name_exchange_file);
			statement.setString(5, p_is_input_file);
			statement.setString(6, p_delimeter);
			statement.setString(7, p_has_comments);
			statement.setString(8, p_has_header);
			statement.setString(9, p_need_log);
			statement.setString(10, p_id_task);
			statement.registerOutParameter(11, Types.VARCHAR);// p_id_exchange_file
			statement.registerOutParameter(12, Types.VARCHAR);// p_error
			field_logger.debug("createExchangeFile: execute procedure ");
			statement.execute();
			if (statement.getString(1).equals("0")) {
				return_value = statement.getString(11);
				field_logger.debug("createExchangeFile: OK");
			} else {
				return_value = null;
				field_logger.debug("createExchangeFile: ERROR");
			}
			statement.close();
			connection.commit();
		} catch (SQLException ex) {
			field_logger.error("createExchangeFile: SQLException:"
					+ ex.getMessage());
		} catch (Exception ex) {
			field_logger.error("createExchangeFile:    Exception:"
					+ ex.getMessage());
		} finally {
			if (connection != null) {
				Connector.closeConnection(connection);
			}
		}
		return return_value;
	}

	/**
	 * получить список всех связок файл-задача по данному терминалу
	 * @param terminal_id
	 *            уникальный идентификатор терминала
	 * */
	public static ArrayList<String[]> getTaskAndFileByTerminal(
			String terminal_id) {
		ArrayList<String[]> return_value = new ArrayList<String[]>();
		Connection connection = null;
		try {
			connection = Connector.getConnection();
			ResultSet rs = connection
					.createStatement()
					.executeQuery(
							" select v_dt_exchange_file.ID_EXCHANGE_FILE, v_dt_exchange_file.id_task, name_exchange_file, v_dt_exchange_file.CD_TASK from V_DT_EXCHANGE_FILE  where id_task in ( select id_task from v_dt_term_exchange_file where id_term="+ terminal_id + ")");
			while (rs.next()) {
				return_value.add(new String[] {
						rs.getString("ID_EXCHANGE_FILE"),
						rs.getString("id_task"),
						rs.getString("name_exchange_file"),
						rs.getString("CD_TASK")});
			}
		} catch (SQLException ex) {
			field_logger.error("getTaskAndFileByTerminal: SQLException"
					+ ex.getMessage());
		} catch (Exception ex) {
			field_logger.error("getTaskAnddFileByTerminal: Exception"
					+ ex.getMessage());
		} finally {
			if (connection != null) {
				Connector.closeConnection(connection);
			}
		}
		return return_value;
	}

	/**
	 * получить список всех связок файл-задача по данному терминалу
	 * @param terminal_id
	 *            уникальный идентификатор терминала
	 * */
	public static ArrayList<String[]> getTaskAndFileOutOfTerminal(
			String terminal_id) {
		ArrayList<String[]> return_value = new ArrayList<String[]>();
		Connection connection = null;
		try {
			connection = Connector.getConnection();
			ResultSet rs = connection
					.createStatement()
					.executeQuery(
							" select v_dt_exchange_file.ID_EXCHANGE_FILE, v_dt_exchange_file.id_task, name_exchange_file, v_dt_exchange_file.CD_TASK from V_DT_EXCHANGE_FILE  where id_task not in ( select id_task from v_dt_term_exchange_file where id_term="+ terminal_id + ")");
			while (rs.next()) {
				return_value.add(new String[] {
						rs.getString("ID_EXCHANGE_FILE"),
						rs.getString("id_task"),
						rs.getString("name_exchange_file")+" : "+rs.getString("CD_TASK")});
			}
		} catch (SQLException ex) {
			field_logger.error("getTaskAndFileByTerminal: SQLException"
					+ ex.getMessage());
		} catch (Exception ex) {
			field_logger.error("getTaskAnddFileByTerminal: Exception"
					+ ex.getMessage());
		} finally {
			if (connection != null) {
				Connector.closeConnection(connection);
			}
		}
		return return_value;
	}
	
	
	/** добавить файл-задачу по терминалу, добавить отношение(сопряжение) файл-терминал */
	public static boolean addExchangeFileToTerminal(String terminal_id, String exchange_file_id, String task_id){
		/*// Фукнція признаяає файл обміну терміналу
		FUNCTION PACK_BC_DT.set_exchange_file_to_term(
     		 p_id_exchange_file IN NUMBER
    		,p_id_term IN NUMBER
    		,p_date_beg IN DATE
    		,p_date_end IN DATE
    		,p_error_string OUT VARCHAR2
		) RETURN NUMBER;
		 */
		boolean return_value=false;
		Connection connection=null;
		try{
			connection=Connector.getConnection();
			connection.setAutoCommit(true);
			CallableStatement statement = connection.prepareCall("{?= call PACK_BC_DT.set_exchange_file_to_term(?,?,?,?,?)}");
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setInt(2, Integer.parseInt(exchange_file_id));
			statement.setInt(3, Integer.parseInt(terminal_id));
			statement.setDate(4, new java.sql.Date((new java.util.Date()).getTime()));
			statement.setNull(5,Types.DATE);
			statement.registerOutParameter(6, Types.VARCHAR);
			statement.execute();
			if(statement.getInt(1)==0){
				field_logger.debug("addExchangeFileToTerminal: OK");
				return_value=true;
			}else{
				field_logger.error("addExchangeFileToTerminal prepareCall return not 0:"+statement.getInt(1));
			}
			return_value = true;
		}catch(SQLException ex){
			field_logger.error("addExchangeFileToTerminal: SQLException:"+ex.getMessage());
		}catch(Exception ex){
			field_logger.error("addExchangeFileToTerminal:    Exception:"+ex.getMessage());
		}finally{
			if(connection!=null){
				Connector.closeConnection(connection);
			}
		}
		return return_value;
	}
	
	/** удалить из терминала файл-задачу, удалить отношение(сопряжение) файл-терминал */
	public static boolean deleteExchangeFileFromTerminal(String terminal_id, String exchange_file_id){
		/*FUNCTION PACK_BC_DT.unset_exchange_file_from_term(
	     p_id_exchange_file IN NUMBER
	     ,p_id_term IN NUMBER
	     ,p_error_string OUT VARCHAR2
	 	) RETURN NUMBER;*/
		boolean return_value=false;
		Connection connection=null;
		try{
			connection=Connector.getConnection();
			connection.setAutoCommit(true);
			CallableStatement statement = connection.prepareCall("{?= call PACK_BC_DT.unset_exchange_file_from_term(?,?,?)}");
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setInt(2, Integer.parseInt(exchange_file_id));
			statement.setInt(3, Integer.parseInt(terminal_id));
			statement.registerOutParameter(4, Types.VARCHAR);
			statement.execute();
			if(statement.getInt(1)==0){
				field_logger.debug("deleteExchangeFileFromTerminal: OK");
				return_value=true;
			}else{
				field_logger.error("deleteExchangeFileFromTerminal prepareCall return not 0:"+statement.getInt(1));
			}
			return_value = true;
		}catch(SQLException ex){
			field_logger.error("deleteExchangeFileFromTerminal: SQLException:"+ex.getMessage());
		}catch(Exception ex){
			field_logger.error("deleteExchangeFileFromTerminal:    Exception:"+ex.getMessage());
		}finally{
			if(connection!=null){
				Connector.closeConnection(connection);
			}
		}
		return return_value;
	}
	
	/*
	 * public static HashMap<String, String> getTerminals() { HashMap<String,
	 * String> return_value = new HashMap<String, String>();
	 * return_value.put("terminal_1", "#1 terminal");
	 * return_value.put("terminal_2", "#2 terminal");
	 * return_value.put("terminal_3", "#3 terminal");
	 * return_value.put("terminal_4", "#4 terminal");
	 * return_value.put("terminal_5", "#5 terminal"); return return_value; }
	 */
	/*
	 * получить список шаблонов для данной системы
	 * XML_Editor/index_fields_pattern.jsp
	 */
	public static HashMap<String, String> getFilesPattern() {
		HashMap<String, String> return_value = new HashMap<String, String>();
		Connection connection = Connector.getConnection();
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select * from V_DT_EXCHANGE_FILE order by 1 desc");
			while (rs.next()) {
				return_value.put(rs.getString("ID_EXCHANGE_FILE"), rs
						.getString("NAME_EXCHANGE_FILE"));
			}
		} catch (SQLException ex) {
			field_logger.error("getFieldsPattern: SQLException:"
					+ ex.getMessage());
		} catch (Exception ex) {
			field_logger.error("getFieldsPatter: Exception:" + ex.getMessage());
		}
		Connector.closeConnection(connection);
		return return_value;
	}

	/**
	 * получить ID_Task из ID_EXCHANGE_FILE
	 * 
	 * @param id_exchange_file
	 * @return либо ID_TASK либо пустую строку
	 */
	public static String getTaskIdByExchangeFile(String exchange_file) {
		String return_value = null;
		Connection connection = null;
		try {
			connection = Connector.getConnection();
			ResultSet rs = connection.createStatement().executeQuery(
					"select id_task from v_dt_exchange_file where id_exchange_file="
							+ exchange_file);
			if (rs.next()) {
				return_value = rs.getString(1);
			}
		} catch (SQLException ex) {
			field_logger.error("getTaskIdByExchangeFile: SQLException:"
					+ ex.getMessage());
		} catch (Exception ex) {
			field_logger.error("getTaskIdByExchangeFile:    Exception:"
					+ ex.getMessage());
		} finally {
			if (connection != null) {
				Connector.closeConnection(connection);
			}
		}
		return (return_value == null) ? "" : return_value;
	}

	/**
	 * получить список всех доступных задач
	 */
	public static HashMap<String, String> getTaskAll() {
		HashMap<String, String> return_value = new HashMap<String, String>();
		Connection connection = Connector.getConnection();
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select * from D_DT_TASK order by 1 desc");
			while (rs.next()) {
				return_value.put(rs.getString("id_task"), rs
						.getString("cd_task"));
			}
		} catch (SQLException ex) {
			field_logger.error("getTaskAll: SQLException:" + ex.getMessage());
		} catch (Exception ex) {
			field_logger.error("getTaskAll: Exception:" + ex.getMessage());
		}
		Connector.closeConnection(connection);
		return return_value;
	}

	/**
	 * получить все готовые шаблоны файлов для XML_Editor/index_visual.jsp
	 */
/*	public static HashMap<String, String> getPatternAll() {
		HashMap<String, String> return_value = new HashMap<String, String>();
		return_value.put("pattern_0", "pattern_0");
		return_value.put("pattern_1", "pattern_1");
		return_value.put("pattern_2", "pattern_2");
		return_value.put("pattern_3", "pattern_3");
		return_value.put("pattern_4", "pattern_4");
		return_value.put("pattern_5", "pattern_5");
		return_value.put("pattern_6", "pattern_6");
		return_value.put("pattern_7", "pattern_7");
		return_value.put("pattern_8", "pattern_8");
		return_value.put("pattern_9", "pattern_9");
		return return_value;
	}
*/
	/**
	 * получить поля для основного шаблона в виде массивов элементов создание
	 * нового шаблона для
	 * http://localhost:8080/XML_Editor/index_fields_pattern.jsp
	 * */
	public static ArrayList<ExchangeFieldsPatternField> getFieldsPattern() {
		ArrayList<ExchangeFieldsPatternField> return_value = new ArrayList<ExchangeFieldsPatternField>();
		Connection connection = Connector.getConnection();
		try {
			ResultSet rs = connection
					.createStatement()
					.executeQuery(
							"select * from V_DT_EXCHANGE_FILE_PAT_FIELDS order by ID_FIELD_PATTERN");
			while (rs.next()) {
				return_value.add(new ExchangeFieldsPatternField(rs));
			}
		} catch (SQLException ex) {
			field_logger.error("getFieldsPattern: SQLException:"
					+ ex.getMessage());
		} catch (Exception ex) {
			field_logger.error("getFieldsPatter: Exception:" + ex.getMessage());
		}
		Connector.closeConnection(connection);
		return return_value;
	}

	/**
	 * получить поля файла по его номеру
	 * 
	 * @param file_id
	 *            - уникальный идентификатор поля
	 * @return возвращает все поля данного файла либо null, если произошла
	 *         какая-либо ошибка
	 * */
	public static ArrayList<ExchangeFileField> getFieldsFromFile(String file_id) {
		boolean flag_valid = false;
		ArrayList<ExchangeFileField> return_value = new ArrayList<ExchangeFileField>();
		Connection connection = null;
		try {
			connection = Connector.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT * FROM V_DT_EXCHANGE_FILE_FIELDS where ID_EXCHANGE_FILE="
							+ file_id + " order by ORDER_NUMBER");
			while (rs.next()) {
				return_value.add(new ExchangeFileField(rs));
			}
			rs.close();
			statement.close();
			flag_valid = true;
		} catch (SQLException ex) {
			flag_valid = false;
		} catch (Exception ex) {
			flag_valid = false;
		} finally {
			if (connection != null) {
				Connector.closeConnection(connection);
			}
		}
		if (flag_valid == true) {
			return return_value;
		} else {
			return null;
		}

	}

	/** получить список пользователей в виде <id><name> */
	public static HashMap<String, String> getUsers() {
		Connection connection = Connector.getConnection();
		HashMap<String, String> return_value = new HashMap<String, String>();
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select ID_TERM, JUR_PRS_NAME from vc_dt_term_all");
			field_logger.debug("getUsers: begin:");
			while (rs.next()) {
				field_logger.debug("getUsers: next:");
				return_value.put(rs.getString(1), rs.getString(1) + ":"
						+ rs.getString(2));
			}
			field_logger.debug("getUsers: end:");
			rs.close();
			field_logger.debug("OK");
		} catch (SQLException ex) {
			field_logger.error("getUsers SQLException: " + ex.getMessage());
		}
		Connector.closeConnection(connection);
		return return_value;
	}

	/** получить список доступных задач по конкретному пользователю */
	public static HashMap<String, String> getTasks(String user_name) {
		Connection connection = Connector.getConnection();
		HashMap<String, String> return_value = new HashMap<String, String>();
		try {
			PreparedStatement ps = connection
					.prepareStatement("select v_dt_term_task.id_task, task_table.name_task from v_dt_term_task inner join v_dt_task task_table on (v_dt_term_task.id_task=task_table.id_task) where v_dt_term_task.id_term=?");
			ps.setString(1, user_name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return_value.put(rs.getString(1), rs.getString(2));
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			field_logger.error("getTasks: SQLException:" + ex.getMessage());
		}
		Connector.closeConnection(connection);
		return return_value;
	}

	/** сохранить данные из XML отображения в HTML виде на клиент */
	public static boolean saveVisualXmlElement(String user_id, String task_id,
			String boolean_string) {
		boolean return_value = false;
		String value_for_save;
		if (boolean_string.equalsIgnoreCase("true")) {
			value_for_save = "Y";
		} else {
			value_for_save = "N";
		}
		field_logger.debug("saveVisualXmlElement:   user_id:" + user_id
				+ "     task_id:" + task_id + "     value:" + value_for_save);
		Connection connection = Connector.getConnection();

		try {
			connection.setAutoCommit(true);
			CallableStatement statement = connection
					.prepareCall("{call PACK_BC_DT.set_term_task_visible(?,?,?)}");
			statement.setString(1, user_id);
			statement.setString(2, task_id);
			statement.setString(3, value_for_save);
			statement.execute();
			return_value = true;
		} catch (SQLException ex) {
			field_logger.error("saveVisualXmlElement SQLException:"
					+ ex.getMessage());
		} catch (Exception ex) {
			field_logger.error("saveVisualXmlElement    Exception:"
					+ ex.getMessage());
		}
		;
		Connector.closeConnection(connection);
		return return_value;
	}

	/**
	 * получить XML файл, который отвечает за внешний вид интерфейса
	 * 
	 * @param user_name
	 *            имя пользователя, по которому необходимо получить данный
	 *            интерфейс
	 * */
	public static Document getVisualXmlByUser(String user_name) {
		VisualXml xml = new VisualXml(user_name);
		return xml.getVisualXml();
	}

	public static void main(String[] args) {
		Document doc = getVisualXmlByUser("111");
		try {
			javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory
					.newInstance();
			javax.xml.transform.Transformer transformer = transformer_factory
					.newTransformer();
			javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(
					doc); // Pass in your document object here
			java.io.FileWriter out = new java.io.FileWriter(
					"c:\\temp_error.xml");
			// string_writer = new Packages.java.io.StringWriter();
			javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(
					out);
			transformer.transform(dom_source, stream_result);
		} catch (Exception ex) {
			field_logger.error("main: " + ex.getMessage());
		}

	}

	/**
	 * очистить все поля файла по File_id
	 */
	public static boolean clearFieldsFromExchangeFile(String file_id) {
		boolean return_value = false;
		Connection connection = null;
		try {
			connection = Connector.getConnection();
			CallableStatement statement = connection
					.prepareCall("{?=call PACK_BC_DT.delete_all_exch_file_fields(?,?)}");
			statement.registerOutParameter(1, Types.VARCHAR);// result
			statement.setInt(2, Integer.parseInt(file_id));
			statement.registerOutParameter(3, Types.VARCHAR);// error
			statement.execute();
			if (statement.getString(1).equals("0")) {
				return_value = true;
			} else {
				field_logger.error("clearFieldsFromExchangeFile:"
						+ statement.getString(3));
			}
		} catch (SQLException ex) {
			field_logger.error("clearFieldsFromExchangeFile: SQLException:"
					+ ex.getMessage());
		} catch (Exception ex) {
			field_logger.error("clearFieldsFromExchangeFile:    Exception:"
					+ ex.getMessage());
		} finally {
			Connector.closeConnection(connection);
		}
		return return_value;
	}

	/**
	 * 
	 * @param id_exchange_file_pattern
	 *            идентификатор шаблонов, по которому происходила закачка данных
	 * @param new_file_id
	 *            номер файла, по которому нужно сделать сохранение данных
	 * @param fields_element
	 *            элементы, которые содержат все данные из HTML-XML
	 * @return возвращает положительный результат в случае успешного наполнения
	 *         полей данными
	 */
	public static boolean fillExchangeFile(String id_exchange_file_pattern,
			String new_file_id,
			ArrayList<ExchangeFieldsPatternField> fields_element) {
		boolean return_value = false;
		Connection connection = null;
		try {
			connection = Connector.getConnection();
			ResultSet rs;
			PreparedStatement ps = connection
					.prepareStatement("select * from V_DT_EXCHANGE_FILE_PAT_FIELDS where id_exchange_file_pattern=? and id_field_pattern=?");
			ArrayList<ExchangeFileField> data = new ArrayList<ExchangeFileField>(
					fields_element.size());

			boolean all_fields_is_done = true;
			int order_counter = 0;
			for (int counter = 0; counter < fields_element.size(); counter++) {
				if (fields_element.get(counter).isChecked()) {
					try {
						field_logger.debug("UniqueId():"
								+ fields_element.get(counter).getUniqueId());
						order_counter++;
						ps.setString(1, id_exchange_file_pattern);
						ps.setString(2, fields_element.get(counter)
								.getUniqueId());
						rs = ps.executeQuery();
						if (rs.next()) {
							data.add(new ExchangeFileField(rs, order_counter));
						} else {
							field_logger
									.error("fillExchangeFile: NOT FOUND  ID_Exchange_file_pattern="
											+ id_exchange_file_pattern
											+ "   id_field_pattern="
											+ fields_element.get(counter)
													.getUniqueId());
							all_fields_is_done = false;
							break;
						}
					} catch (SQLException ex) {
						field_logger
								.error("fillExchangeFile: Execute procedure:"
										+ ex.getMessage());
					}
				}
			}
			ps.close();
			if (all_fields_is_done == false) {
				throw new Exception("fields read error");
			}

			// here we have ExchangeFileFields[]
			// Connector.closeConnection(connection);
			// connection=Connector.getConnection();
			// connection.setAutoCommit(false);
			boolean all_fields_is_save = true;
			ExchangeFileField current_value;
			CallableStatement statement = connection
					.prepareCall("{? = call PACK_BC_DT.add_exchange_file_field(?,?,?,?,?,?,?,?,?,?)}");
			for (int counter = 0; counter < data.size(); counter++) {
				try {
					statement.registerOutParameter(1, Types.VARCHAR);
					field_logger.debug("file_id:"
							+ Integer.parseInt(new_file_id));
					statement.setInt(2, Integer.parseInt(new_file_id));
					current_value = data.get(counter);
					field_logger
							.debug("p_cd_field:" + current_value.p_cd_field);
					statement.setString(3, current_value.p_cd_field);
					field_logger.debug("p_name_field:"
							+ current_value.p_name_field);
					statement.setString(4, current_value.p_name_field);
					field_logger.debug("p_format_field:"
							+ current_value.p_format_field);
					statement.setString(5, current_value.p_format_field);
					field_logger.debug("p_order_number:"
							+ current_value.p_order_number);
					statement.setInt(6, Integer
							.parseInt(current_value.p_order_number));
					field_logger.debug("p_requeired:"
							+ current_value.p_required);
					statement.setString(7, current_value.p_required);
					field_logger.debug("p_id_excachange_file_pattern:"
							+ current_value.p_id_exchange_file_pattern);
					statement
							.setInt(
									8,
									Integer
											.parseInt(current_value.p_id_exchange_file_pattern));
					field_logger.debug("p_id_field_pattern:"
							+ current_value.p_id_field_pattern);
					statement.setInt(9, Integer
							.parseInt(current_value.p_id_field_pattern));
					statement.registerOutParameter(10, Types.INTEGER);
					statement.registerOutParameter(11, Types.VARCHAR);
					field_logger.debug("execute procedure");
					statement.execute();
					/*
					 * connection.createStatement().executeUpdate("{?=call PACK_BC_DT.add_exchange_file_field(1,'CD_FILE','CD_FILE','NM','java.lang.String',1,'Y',1,1,'')}"
					 * );
					 */
					field_logger.debug("procedure return "
							+ statement.getString(1).equals("0"));
					if (statement.getString(1).equals("0")) {
						field_logger.debug("fillExchangeFile: field is save ");
					} else {
						field_logger
								.debug("fillExchangeFile: field is not save ");
						all_fields_is_save = false;
						break;
					}
					return_value = true;
				} catch (SQLException ex) {
					all_fields_is_save = false;
					field_logger.error("fillExchangeFill SQLException:"
							+ ex.getMessage());
				}
			}
			if (all_fields_is_save == false) {
				connection.rollback();
				field_logger
						.error("fillExchangeFile all fields is not saving - delete file #"
								+ new_file_id);
				return_value = false;
				throw new Exception("fields save error");
			} else {
				return_value = true;
			}
			connection.commit();
		} catch (SQLException ex) {
			field_logger.error("fillExchangeFile: SQLException"
					+ ex.getMessage());
			// deleteExchangeFile(connection,new_file_id);
		} catch (Exception ex) {
			// deleteExchangeFile(connection,new_file_id);

			field_logger.error("fillExchangeFile:    Exception"
					+ ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (connection != null) {
				Connector.closeConnection(connection);
			}
		}
		return return_value;
	}

	/**
	 * удалить файл-шаблон с заданным ID
	 * 
	 * @param connection
	 *            - текущее соединение с базой данных, если == null, тогда нужно
	 *            создать новое соединение и вернуть его в пул в конце
	 * @param id_file_for_delete
	 *            - идентификатор файла, который необходимо удалить
	 * */
	public static boolean deleteExchangeFile(Connection connection,
			String id_file_for_delete) {
		boolean return_value = false;
		Connection this_connection = null;
		if (connection == null) {
			this_connection = Connector.getConnection();
		} else {
			this_connection = connection;
		}
		try {
			CallableStatement statement = this_connection
					.prepareCall("{? = call PACK_BC_DT.delete_exchange_file(?,?)}");
			statement.registerOutParameter(1, Types.VARCHAR);
			statement.setString(2, id_file_for_delete);
			statement.registerOutParameter(3, Types.VARCHAR);
			statement.execute();
			if (statement.getString(1).equals("0")) {
				field_logger.debug("deleteExchangeFile file is deleted");
			} else {
				field_logger.error("deleteExchangeFile is not deleted:#"
						+ id_file_for_delete);
			}
			this_connection.commit();
			return_value = true;
		} catch (Exception ex) {
			field_logger
					.error("deleteExchangeFile Exceptin:" + ex.getMessage());
			return_value = false;
		} finally {
			if (connection == null) {
				Connector.closeConnection(this_connection);
			}
		}
		return return_value;
	}

}
