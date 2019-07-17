package cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import jxl.common.Logger;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.File;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.column.IColumnListHolder;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.dictionary.Dictionary;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;


/** 
 * Builder for {@link ALoaderSheet}
 * */
public class LoaderSheetBuilder {
	private Logger logger=Logger.getLogger(this.getClass());
	private HashMap<ALoaderSheet.ESheetAttributes, Object> property=new HashMap<ALoaderSheet.ESheetAttributes, Object>();

	/**
	 * set property for element
	 * @param key - name of property
	 * @param value - property value
	 */
	public void setProperty(ALoaderSheet.ESheetAttributes key, Object value){
		property.put(key, value);
	}
	
	public void reset(){
		this.property.clear();
	}
	
	/** get all property as String value for debug output */
	public String getDebugInfoAboutProperty(){
		StringBuilder returnValue=new StringBuilder();
		Iterator<ALoaderSheet.ESheetAttributes> iterator=this.property.keySet().iterator();
		while(iterator.hasNext()){
			ALoaderSheet.ESheetAttributes key=iterator.next();
			Object value=this.property.get(key);
			returnValue
			.append("   Key:")
			.append(key)
			.append("   Value:")
			.append(value)
			.append("\n");
		}
		return returnValue.toString();
	}
	/** check string for Empty */
	private boolean isEmpty(String value){
		if(value==null)return true;
		if(value.trim().equals(""))return true;
		return false;
	}
	
	/** 
	 * create value<br /> 
	 * before create you must set all settings for elements {@link LoaderSheetBuilder#setProperty(String, String)}
	 * @return
	 * @throws - if create {@link ALoaderSheet} is impossible
	 */
	public ALoaderSheet<?> build(IColumnListHolder columnsHolder, 
								 List<Dictionary> dictionaryList) throws EDbLoaderException{
		try{
			// read all parameters
			File.EType fileType=(File.EType)property.get(ALoaderSheet.ESheetAttributes.file_type);
			String  sheetName=(String)property.get(ALoaderSheet.ESheetAttributes.name);
			String tableName=(String)property.get(ALoaderSheet.ESheetAttributes.table_name);
			Integer startRow=(Integer)property.get(ALoaderSheet.ESheetAttributes.start_row);
			String dictionaryName=(String)property.get(ALoaderSheet.ESheetAttributes.dictionary_name);
			String dictionaryValue=(String)property.get(ALoaderSheet.ESheetAttributes.dictionary_value);
			
			// create element by type
			switch(fileType){
				case xls:{
					if(isEmpty(tableName)&&isEmpty(dictionaryName)){
						throw new EDbLoaderException("TableName or DictionaryName is not set");
					}
					if(isEmpty(tableName)){
						// is dictionary
						Dictionary dictionary=getDictionaryByName(dictionaryList, dictionaryName);
						if(dictionary==null){
							throw new EDbLoaderException("Dictionary does not find for Sheet name="+sheetName+"   Dictionary name="+dictionaryName);
						}
						return new ExcelLoaderSheet(sheetName,
													dictionary,
												    dictionaryValue,
							    					startRow, 
							    					columnsHolder);
					}else{
						// is table
						return new ExcelLoaderSheet(sheetName, 
												    tableName, 
												    startRow, 
												    columnsHolder);
					}
				}
				default:{
					throw new EDbLoaderException("file Type does not recognized:"+fileType);
				}
			}
		}catch(EDbLoaderException ex){
			logger.error("Build Loader Exception:"+ex.getMessage());
			throw ex;
		}catch(Exception ex){
			logger.error("Build Exception:"+ex.getMessage());
			throw new EDbLoaderException(ex.getMessage());
		}
	}

	/** get dictionary from List of Dictionary by Name */
	private Dictionary getDictionaryByName(List<Dictionary> dictionaryList, String dictionaryName) {
		if(dictionaryList==null)return null;
		if(dictionaryName==null)return null;
		for(Dictionary dictionary:dictionaryList){
			if(dictionary.getName().equals(dictionaryName)){
				return dictionary;
			}
		}
		return null;
	}
}
