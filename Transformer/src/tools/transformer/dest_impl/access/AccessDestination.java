package tools.transformer.dest_impl.access;

import java.io.File;
import java.io.IOException;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

import tools.transformer.core.common_model.IModel;
import tools.transformer.core.destination.IDestinationIterator;
import tools.transformer.core.exceptions.ETransformerException;
import tools.transformer.core.instance.IColumnNames;
import tools.transformer.core.instance.IInstance;
import tools.transformer.core.instance.ISqlColumnTypes;

public class AccessDestination implements IDestinationIterator{
	private String outputFileName;
	/**
	 * need to set this property: outputFileName
	 * @param name
	 */
	public void setOutputFileName(String name){
		this.outputFileName=name;
	}
	
	private void removeFileIfExists(String pathToFile){
		try{
			File file=new File(pathToFile);
			file.delete();
		}catch(Exception ex){
		}
	}

	private Database database=null;
	
	public void init() throws ETransformerException {
		// create Database 
		this.removeFileIfExists(outputFileName);
		try{
			database=Database.create(new File(this.outputFileName));
		}catch(Exception ex){
			throw new ETransformerException("#init Exception: "+ex.getMessage());
		}
	}

	public void deInit() throws ETransformerException {
		// close Database
		try {
			this.database.close();
		} catch (IOException e) {
		}
	}
	
	public void nextIteration(IModel next) throws ETransformerException {
		// write record to current table
		try{
			this.currentTable.addRow(next.getObjects());
		}catch(Exception ex){
			throw new ETransformerException("Write record to table Exception:"+ex.getMessage());
		}
	}

	// create table 
	private Table currentTable=null;

	public void nextInstanceBegin(IInstance instance) {
		if( (instance instanceof ISqlColumnTypes)&&(instance instanceof IColumnNames) ){
			try{
				TableBuilder tableBuilder=new TableBuilder(removeSpecialCharacters(instance.getId()));
				int[] columnTypes=((ISqlColumnTypes)instance).getColumnTypes();
				String[] columnNames=((IColumnNames)instance).getColumnNames();
				if(columnTypes.length!=columnNames.length){
					throw new ETransformerException("check length of ColumnNames and ColumnTypes ");
				}
				for(int index=0;index<columnTypes.length;index++){
					tableBuilder.addColumn(new ColumnBuilder(columnNames[index]).setSQLType(columnTypes[index]).toColumn());
				}
				this.currentTable=tableBuilder.toTable(database);
			}catch(Exception ex){
				throw new ETransformerException("Create table Exception: "+ex.getMessage());
			}
		}else{
			throw new ETransformerException("can't create table without fields, need to implements ISqlColumnTypes && IColumnNames interface");
		}
	}

	private String removeSpecialCharacters(String id){
		return id.replaceAll("[^a-zA-Z]", "");
	}
	
	public void nextInstanceEnd(IInstance instance) {
		// close table - nothing 
	}

}
