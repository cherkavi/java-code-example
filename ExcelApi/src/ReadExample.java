import jxl.*; 
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.*;

public class ReadExample {
	public static void main(String[] args){
		try{
			String pathToFile="d:\\temp.xls";
			write(pathToFile);
			read(pathToFile);
			
		}catch(Exception ex){
			System.err.println("ReadExample Exception:"+ex.getMessage());
		}
	}
	
	private static void write(String pathToFile) throws Exception{
		// settings for workbook
		WorkbookSettings settings=new WorkbookSettings();
		settings.setEncoding("WINDOWS-1251");
		// create and open workbook
		WritableWorkbook workbook=Workbook.createWorkbook(new File(pathToFile),settings);
		// create sheet into workbook
		WritableSheet sheet=workbook.createSheet("лист один", 0);
		// cell format 
		CellView cellView=new CellView();
		cellView.setSize(10000);
		// set cell format
		sheet.setColumnView(0, cellView);
		// add cell into sheet
		sheet.addCell(new Label(0,0,"это временное значение"));
		// write workbook
		workbook.write();
		workbook.close();
	}
	
	private static void read(String pathToFile) throws Exception{
		Workbook workbook=Workbook.getWorkbook(new File(pathToFile));
		Sheet sheet=workbook.getSheet(0);
		Cell[] columnOne=sheet.getColumn(0);
		for(int counter=0;counter<columnOne.length;counter++){
			System.out.println(sheet.getCell(0,counter).getContents());
		}
		workbook.close();
	}
}
