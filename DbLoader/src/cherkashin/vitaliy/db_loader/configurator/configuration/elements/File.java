package cherkashin.vitaliy.db_loader.configurator.configuration.elements;

import java.util.List;

import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

import jxl.Workbook;

public class File {
	public enum EType{
		/** Microsoft Excel */
		XLS;
	}
	/** full path to File */
	private String path;
	/** type of File  */
	private EType type;
	/** sheets collection  */
	private List<Sheet> sheets=null;

	public File(String filePath, String fileType, List<Sheet> sheetsFromNode) {
		this.path=filePath;
		this.type=EType.valueOf(fileType.toUpperCase());
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
	public List<Sheet> getSheets() {
		return sheets;
	}
	/** sheets collection  */
	public void setSheets(List<Sheet> sheets) {
		this.sheets = sheets;
	}
	
	private Workbook workbook=null;
	
	public void open() throws EDbLoaderException{
		try{
			workbook=Workbook.getWorkbook(new java.io.File(this.path));
		}catch(Exception ex){
			throw new EDbLoaderException("Open file:"+this.path+"   Exception:"+ex.getMessage());
		}
	}

	public void close() {
		this.workbook.close();
	}
	
	
}
