package cherkashin.vitaliy.db_loader.configurator.configuration.elements;

import java.util.List;

import org.apache.log4j.Logger;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet.ALoaderSheet;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

import jxl.Workbook;


public class File {
	private Logger logger=Logger.getLogger(this.getClass().getName());
	/** enum of available type files for load configuration */
	public enum EType{
		/** Microsoft Excel */
		xls;
	}
	/** full path to File */
	private String path;
	/** type of File  */
	private EType type;
	/** sheets collection  */
	private List<ALoaderSheet> sheets=null;

	public File(String filePath, File.EType fileType, List<ALoaderSheet> sheetsFromNode) {
		this.path=filePath;
		this.type=fileType;
		this.sheets=sheetsFromNode;
	}
	/** full path to File */
	public String getPath() {
		return path;
	}
	/** full path to File */
	public void setPath(String path) {
		this.path = path;
	}
	/** type of File  */
	public EType getType() {
		return type;
	}
	/** type of File  */
	public void setType(EType type) {
		this.type = type;
	}
	/** sheets collection  */
	public List<ALoaderSheet> getSheets() {
		return sheets;
	}
	/** sheets collection  */
	public void setSheets(List<ALoaderSheet> sheets) {
		this.sheets = sheets;
	}
	
	private Workbook workbook=null;
	
	public void open() throws EDbLoaderException{
		try{
			logger.debug("try to open workbook by path: "+this.path);
			workbook=Workbook.getWorkbook(new java.io.File(this.path));
		}catch(Exception ex){
			logger.error("Open file: "+this.path+"  Exception: "+ex.getMessage());
			throw new EDbLoaderException("Open file:"+this.path+"   Exception:"+ex.getMessage());
		}
	}

	public void close() {
		this.workbook.close();
	}
	
	private jxl.Sheet getSheetByName(ALoaderSheet currentSheet) throws EDbLoaderException{
		try{
			return this.workbook.getSheet(currentSheet.getName());
		}catch(Exception ex){
			logger.error("Open Sheet Exception:"+ex.getMessage());
			throw new EDbLoaderException("open sheet exception");
		}
	}
	
	/** open existing sheet  */
	public jxl.Sheet openSheet(ALoaderSheet currentSheet) throws EDbLoaderException{
		return this.getSheetByName(currentSheet);
	}
	
	/** close existing sheet */
	public jxl.Sheet closeSheet(ALoaderSheet currentSheet) throws EDbLoaderException{
		return this.getSheetByName(currentSheet);
	}
	
	
}
