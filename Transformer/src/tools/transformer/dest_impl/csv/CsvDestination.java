package tools.transformer.dest_impl.csv;

import com.csvreader.CsvWriter;

import tools.transformer.core.common_model.IModel;
import tools.transformer.core.destination.IDestinationIterator;
import tools.transformer.core.exceptions.ETransformerException;
import tools.transformer.core.instance.IColumnNames;
import tools.transformer.core.instance.IInstance;

public class CsvDestination implements IDestinationIterator{
	private static final String EMPTY_STRING = "";
	private com.csvreader.CsvWriter writer=null;
	private String outputDir;

	/**
	 * important property: outputDir
	 * @param outputDir
	 */
	public void setOutputDir(String outputDir){
		this.outputDir=outputDir.trim();
		String systemFileSeparator=System.getProperty("file.separator");
		if(!this.outputDir.endsWith(systemFileSeparator)){
			this.outputDir=this.outputDir+systemFileSeparator;
		}
	}
	
	public void deInit() throws ETransformerException {
	}

	public void init() throws ETransformerException {
	}

	public void nextIteration(IModel next) throws ETransformerException {
		try{
			this.writer.writeRecord(convertToString(next.getObjects()));
			this.writer.flush();
		}catch(Exception ex){
			throw new ETransformerException("write next iteration Exception:"+ex.getMessage());
		}
	}
	
	private String[] convertToString(Object[] values){
		String[] returnValue=new String[values.length];
		for(int index=0;index<values.length;index++){
			if(values[index]!=null){
				returnValue[index]=values[index].toString();
			}else{
				returnValue[index]=EMPTY_STRING;
			}
		}
		return returnValue;
	}

	public void nextInstanceBegin(IInstance instance) {
		this.writer=new CsvWriter(this.outputDir+instance.getId()+".csv");
		if(instance instanceof IColumnNames){
			try{
				this.writer.writeRecord( ((IColumnNames)instance).getColumnNames());
			}catch(Exception ex){
				throw new ETransformerException("nextInstanceBegin Exception:"+ex.getMessage());
			};
		}
	}

	public void nextInstanceEnd(IInstance instance) {
		this.writer.close();
	}

}
