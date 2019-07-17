package cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet;

import java.util.ArrayList;
import java.util.List;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.column.Column;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.column.ConstColumn;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.dictionary.Dictionary;
import cherkashin.vitaliy.db_loader.writer.ColumnDataAdapter;

/** part of kind of Sheet - Dictionary*/
public class DictionaryKindOfSheet extends ABridgeKindOfSheet{
	public static String PROPERTY_DICTIONARY_TABLE_NAME="property_dictionary_table_name";
	public static String PROPERTY_DICTIONARY_COLUMN_NAME="property_dictionary_column_name";
	public static String PROPERTY_DICTIONARY_COLUMN_VALUE="property_dictionary_column_value";
	private ColumnDataAdapter addValue=null;
	private Dictionary dictionary=null;
	private String dictionaryValue=null;
	private ConstColumn column=null;
	/**
	 * Dictionary
	 * @param dictionaryColumnName - column in Database for set DictionaryValue
	 * @param dictionaryValue - dictionaryValue for set 
	 */
	public DictionaryKindOfSheet(Dictionary dictionary, String dictionaryValue) {
		this.dictionary=dictionary;
		this.dictionaryValue=dictionaryValue;
		this.column=new ConstColumn(dictionary.getDbField());
		this.addValue=new ColumnDataAdapter(this.column, dictionaryValue);
	}

	@Override
	public List<ColumnDataAdapter> getColumnDataAdapters(List<ColumnDataAdapter> returnValue) {
		returnValue.add(this.addValue);
		return returnValue;
	}

	@Override
	public Object getProperty(String property) {
		if(PROPERTY_DICTIONARY_TABLE_NAME.equals(property)){
			return this.dictionary.getDbTable();
		}
		if(PROPERTY_DICTIONARY_COLUMN_NAME.equals(property)){
			return this.dictionary.getDbField();
		}
		if(PROPERTY_DICTIONARY_COLUMN_VALUE.equals(property)){
			return this.dictionaryValue;
		}
		return null;
	}

	@Override
	public List<Column> getColumns(List<Column> columns) {
		ArrayList<Column> list=new ArrayList<Column>();
		list.addAll(columns);
		list.add(this.column);
		return list;
	}
}
