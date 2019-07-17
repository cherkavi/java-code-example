package bc.data_terminal.editor.transfer;

import java.util.ArrayList;


import org.apache.log4j.*;

import bc.data_terminal.editor.database.DBFunction;


/** 
 * класс, который отвечает за создание шаблонов  
 * */
public class ExchangeTerminalTask extends XmlExchange{
	private Logger field_logger=Logger.getLogger(this.getClass());
/*	{
		field_logger.setLevel(Level.DEBUG);
		if(!field_logger.getAllAppenders().hasMoreElements()){
			BasicConfigurator.configure();
		}
	}
*/	
	/** отправить XML файл на сервер и получить ответ 
	 * место обмена HTML клиента с сервером 
	 * */
	public Data sendXmlToServer(Data data){
		field_logger.debug("get data Key:"+data.getKey());
		field_logger.debug("get data Value:"+data.getValue());
		// Функции клиента на стороне сервера 
		Data return_value=data;
		
		// получить все задачи по данному терминалу 
		if(data.getKey().equals("function_terminal_select")){
			field_logger.debug("sendXmltoServer: terminal_id:"+data.getValue());
			field_logger.debug("sendXmltoServer: table_id:"+data.getValue2());
			return_value.setValue(getTaskPatternHtmlTable(data.getValue(), data.getValue2()));
		}
		// INFO отобразить в виде тэга SELECT файлы-задачи, которые можно присоединить к данным 
		if(data.getKey().equals("function_show_insert")){
			field_logger.debug("function_show_insert: Terminal:"+data.getValue());
			return_value.setValue(convertTaskAndFileToCombobox(DBFunction.getTaskAndFileOutOfTerminal(data.getValue())));
		}
		
		// INFO добавить к терминалу файл-задачу 
		if(data.getKey().equals("function_insert")){
			field_logger.debug("Terminal:"+data.getValue());
			field_logger.debug("ID_File:"+data.getValue2());
			field_logger.debug("ID_Task:"+data.getValue3());
			DBFunction.addExchangeFileToTerminal(data.getValue(), data.getValue2(), data.getValue3());
			return_value.setValue(getTaskPatternHtmlTable(data.getValue(),"table_exists"));
		}
		// INFO функция удаления данных 
		if(data.getKey().equals("function_delete")){
			field_logger.debug("function_delete: Terminal:"+data.getValue());
			field_logger.debug("function_delete: ID_file:"+data.getValue2());
			DBFunction.deleteExchangeFileFromTerminal(data.getValue(),data.getValue2());
			return_value.setValue(getTaskPatternHtmlTable(data.getValue(),"table_exists"));
		}
		
/*		if(data.getKey().equals("function_terminal_select")){
			field_logger.debug("sendXmltoServer: function:"+data.getKey());
			field_logger.debug("sendXmltoServer: Value:"+data.getValue());
			field_logger.debug("sendXmltoServer: Value2:"+data.getValue2());
			field_logger.debug("sendXmltoServer: Value3:"+data.getValue3());
			data.setValue(get);
		}
*/		
		return return_value;
	}

	
	/** конвертировать полученные значения String[] для отображения в ComboBox для выбора файла */
	private String convertTaskAndFileToCombobox(ArrayList<String[]> data){
		StringBuffer return_value=new StringBuffer();
		for(int counter=0;counter<data.size();counter++){
			return_value.append("<OPTION ");
			return_value.append(" value=\""); // id_exchange_file
			return_value.append(data.get(counter)[0]);
			return_value.append("\") ");

			return_value.append(" id_task=\""); // id_task
			return_value.append(data.get(counter)[1]);
			return_value.append("\") ");

			return_value.append(">");
			return_value.append(data.get(counter)[2]);
			return_value.append("</OPTION>");			
		}
		field_logger.debug("convertTaskAndFileToCombobox: "+return_value.toString());
		return return_value.toString();
	}
	
	/** получить в виде таблицы список задач и шаблонов по этим задачам для терминала */
	private String getTaskPatternHtmlTable(String terminal_id, String table_id){
		StringBuffer return_value=new StringBuffer();
		String table_width="400";
		String table_width_half="200";
		
		return_value.append("<TABLE id=\""+table_id+"\"  width=\""+table_width+"\"  ");
		// header
		return_value.append("<TR>");
		return_value.append("<TD>");
		return_value.append("	<TABLE width=\""+table_width+"\"border=2 >");
		return_value.append("		<TR>");
		return_value.append("			<TD width=\""+table_width_half+"\"> ");
		return_value.append("				<CENTER>File Name</CENTER>");
		return_value.append("			</TD>");
		return_value.append("			<TD>");
		return_value.append("				<CENTER>Task Name</CENTER>");
		return_value.append("			</TD>");
		return_value.append("		</TR>");
		return_value.append("	</TABLE>");
		return_value.append("</TD>");
		return_value.append("</TR>");
		// data
		ArrayList<String[]> data=DBFunction.getTaskAndFileByTerminal(terminal_id);
		for(int counter=0;counter<data.size();counter++){
			return_value.append("<TR>");
			return_value.append("<TD>");
			return_value.append(convertStringArrayToField(data.get(counter),table_width));
			return_value.append("</TD>");
			return_value.append("</TR>");
		}
		return_value.append("</TABLE>");
		// manager
		return_value.append("<TABLE id=\""+table_id+"_manager\" width=\""+table_width+"\">");
		return_value.append("	<TR>");
		return_value.append("		<TD>");
		return_value.append("			<center><INPUT TYPE=\"button\" value=\"Add new\" onclick=\"add_new_task_pattern()\"></center>");
		return_value.append("		</TD>");
		return_value.append("	</TR>");
		
		return_value.append("</TABLE >");
		return return_value.toString();
	}

	/** конвертировать одну запись из базы данных в таблицу HTML с элементами управления */
	private StringBuffer convertStringArrayToField(String[] data,String table_width){
		StringBuffer return_value=new StringBuffer();
		try{
			return_value.append("<TABLE  border=1 width=\""+table_width+"\" id_file=\""+data[0]+"\" id_task=\""+data[1]+"\" >");
			return_value.append("<TR>");
			return_value.append("<TD>");
			return_value.append(data[2]);
			return_value.append("</TD>");
			return_value.append("<TD>");
			return_value.append(data[3]);
			return_value.append("</TD>");
			return_value.append("<TD>");
			return_value.append("<input type=\"button\" value=\"Delete\" onclick=\"delete_task_pattern(this)\">");
			return_value.append("</TD>");
			
			return_value.append("</TR>");
			return_value.append("</TABLE>");
		}catch(Exception ex){
			field_logger.error("convertStringArrayToField Exception:"+ex.getMessage());
		}
		return return_value;
	}
	
	
}	














