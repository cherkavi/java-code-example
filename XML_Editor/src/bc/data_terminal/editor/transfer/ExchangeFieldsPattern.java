package bc.data_terminal.editor.transfer;

import java.util.ArrayList;
import java.util.HashMap;


import org.apache.log4j.*;

import bc.data_terminal.editor.database.DBFunction;
import bc.data_terminal.editor.database.ExchangeFieldsPatternField;
import bc.data_terminal.editor.database.ExchangeFieldsPatternHeadAttribute;
import bc.data_terminal.editor.database.ExchangeFieldsPatternHeadElement;
import bc.data_terminal.editor.database.ExchangeFile;
import bc.data_terminal.editor.utility.toHtmlConverter;


/**
 * класс, который отвечает за создание шаблонов
 * */
public class ExchangeFieldsPattern extends XmlExchange {
	private Logger field_logger = Logger.getLogger(this.getClass());
	{
		field_logger.setLevel(Level.DEBUG);
		/*
		 * try{ //Layout layout=new PatternLayout();// format output //Layout
		 * layout=new SimpleLayout();// format output
		 * //field_logger.addAppender(new FileAppender(layout,"c:\\log.txt"));
		 * }catch(Exception ex){ }
		 */

		if (!field_logger.getAllAppenders().hasMoreElements()) {
			BasicConfigurator.configure();
		}
	}

	private ArrayList<ExchangeFieldsPatternHeadElement> field_header = new ArrayList<ExchangeFieldsPatternHeadElement>();
	{
		field_header.add(new ExchangeFieldsPatternHeadElement("DELIMETER",
				new ExchangeFieldsPatternHeadAttribute("value", new String[] {
						",", ".", ";" })));
		field_header.add(new ExchangeFieldsPatternHeadElement("HAS_COMMENTS",
				new ExchangeFieldsPatternHeadAttribute("exists", new String[] {
						"true", "false" })));
		field_header.add(new ExchangeFieldsPatternHeadElement("HAS_HEADER",
				new ExchangeFieldsPatternHeadAttribute("exists", new String[] {
						"true", "false" })));
		field_header.add(new ExchangeFieldsPatternHeadElement("NEED_LOG",
				new ExchangeFieldsPatternHeadAttribute("full_log",
						new String[] { "true", "false" })));

	}

	/**
	 * отправить XML файл на сервер и получить ответ место обмена HTML клиента с
	 * сервером
	 * */
	public Data sendXmlToServer(Data data) {
		field_logger.debug("get data Key:" + data.getKey());
		field_logger.debug("get data Value:" + data.getValue());
		Data return_value = new Data();

		// INFO получить Task
		if(data.getKey().equals("function_load_task")){
			field_logger.debug("sendXmltoServer: function:" + data.getKey());
			field_logger.debug("sendXmltoServer: Name:" + data.getValue());
			if(data.getValue().equals("")){
				// filter is empty
				return_value.setValue(toHtmlConverter.convertMapToSelectBody(DBFunction.getTaskAll()));
			}else{
				// filter is not empty
				return_value.setValue(toHtmlConverter.convertMapToSelectBody(DBFunction.getTaskAll()));
			}
		}
		// INFO удалить указанный шаблон
		if (data.getKey().equals("function_delete_pattern")) {
			field_logger.debug("sendXmltoServer: function:" + data.getKey());
			field_logger.debug("sendXmltoServer: Name:" + data.getValue());
			if (DBFunction.deleteExchangeFile(null, data.getValue())) {
				return_value.setKey(data.getKey());
				return_value.setValue(toHtmlConverter
						.convertMapToSelectBodyWithEmpty(DBFunction
								.getFilesPattern()));
			} else {
				return_value.setKey("alert");
				return_value.setValue("error in delete key");
			}
		}

		// INFO server создать шаблон
		if (data.getKey().equals("function_pattern_create")) {
			field_logger.debug("sendXmltoServer: function:" + data.getKey());
			field_logger.debug("sendXmltoServer: Name:" + data.getValue());
			StringBuffer answer = new StringBuffer();
			// header
			answer.append("<span id=\"header\">");
			for (int counter = 0; counter < field_header.size(); counter++) {
				answer.append(field_header.get(counter).getHtmlText());
			}
			answer.append("</span>");
			// body <span id="fields" уже внедрен>
			return_value.setKey(data.getKey());
			answer.append(this.convert(DBFunction.getFieldsPattern()));
			return_value.setValue(answer.toString());
			return_value.setValue2(toHtmlConverter
					.convertMapToSelectBody(DBFunction.getTaskAll()));
		}

		// INFO server открыть шаблон
		if (data.getKey().equals("function_pattern_open")) {
			field_logger.debug("sendXmltoServer: function:" + data.getKey());
			field_logger.debug("sendXmltoServer: Name:" + data.getValue());

			StringBuffer html_file = this.getHtmlByPatternFile(data.getValue());
			if (html_file != null) {
				// положительный результат - HTML вид файла
				return_value.setKey(data.getKey());
				return_value.setValue(html_file.toString());
				return_value.setValue2(toHtmlConverter
						.convertMapToSelectBodyWithSelected(DBFunction
								.getTaskAll(), DBFunction
								.getTaskIdByExchangeFile(data.getValue())));
			} else {
				// отрицательный результат - сообщение пользователю об ошибке
				return_value.setKey("alert");
				return_value.setValue("Error");
			}
		}

		// INFO server копировать шаблон
		if (data.getKey().equals("function_pattern_copy")) {
			field_logger.debug("sendXmltoServer: function:" + data.getKey());
			//field_logger.debug("sendXmltoServer: pattern_source:"+data.getValue
			// ());
			// field_logger.debug("sendXmltoServer: pattern_destination:"+data.
			// getValue2());

			HashMap<String, String> patterns = DBFunction.getFilesPattern();
			if (patterns.containsKey(data.getValue2())) {
				return_value.setKey("alert");
				return_value.setValue("Pattern exists");
			} else {
				patterns.put(data.getValue2(), data.getValue2());
				return_value.setKey(data.getKey());
				return_value.setValue(toHtmlConverter
						.convertMapToSelectBodyWithEmpty(patterns));
			}
		}

		// INFO server сохранить шаблон
		if (data.getKey().equals("function_save_pattern")) {
			field_logger.debug("sendXmltoServer: function:" + data.getKey());
			field_logger.debug("sendXmltoServer: isCreate :" + data.getValue());
			field_logger.debug("sendXmltoServer: Name:" + data.getValue2());
			// field_logger.debug("sendXmltoServer: Value:"+data.getValue3());
			ExchangeFieldsPatternParser parser = new ExchangeFieldsPatternParser(
					data.getValue(), data.getValue2(), data.getValue3(),data.getValue4());
			String save_result = parser.getResult();
			if (save_result.equals("")) {
				return_value.setKey(data.getKey());
				return_value.setValue(toHtmlConverter
						.convertMapToSelectBodyWithEmpty(DBFunction
								.getFilesPattern()));
			} else {
				return_value.setKey("alert");
				return_value.setValue(save_result);
			}

		}

		// произошло изменение задачи ( выбран клиент, выбрана задача -
		// загрузить файл XML)
		/*
		 * if(data.getKey().equals("task_change")){ // изменение клиента -
		 * послать файл XML return_value.setKey("xml_body"); String
		 * html_kod=this.getHtmlFromUserFromTask(data.getValue(),
		 * data.getValue2() ).toString(); field_logger.debug(html_kod);
		 * return_value.setValue(html_kod); };
		 */
		return return_value;
	}

	/**
	 * конвертирование полученных из базы данных значений полей Fields в HTML
	 * формат, пригодный для отображения
	 */
	private StringBuffer convert(ArrayList<ExchangeFieldsPatternField> data) {
		StringBuffer return_value = new StringBuffer();
		return_value.append("<span id=\"fields\">");
		for (int counter = 0; counter < data.size(); counter++) {
			return_value.append(data.get(counter).getHtml());
		}
		return_value.append("</span>");
		return return_value;
	}

	/**
	 * пришел запрос на открытие файла: <br>
	 * получить заголовок файла и получить его поля
	 * 
	 * @param id_file
	 *            - идентификатор файла
	 * @return null - в случае, если не удалось по каким-то причинам загрузить
	 *         данные HTML или же StringBuffer с данными
	 */
	private StringBuffer getHtmlByPatternFile(String id_file) {
		StringBuffer return_value = null;
		// открыть файл и получить его заголовок
		ExchangeFile file = new ExchangeFile();
		if (file.loadFromDataBase(id_file)) {
			// попытка преобразования ExchangeFile
			return_value = file.getHtml(this.field_header);
		}
		// по файлу получить его поля
		return return_value;
	}

}
