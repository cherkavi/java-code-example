import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import ar.com.epere4.java2excel.excelWritter.*;
import ar.com.epere4.java2excel.parser.*;

public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		String path_to_file="c:\\out.xls";
		if(args.length>0){
			path_to_file=args[0];
		}
/*		
		// Get the factory
		Java2ExcelDtdFactory factory = Java2ExcelDtdFactory.getInstance();
		System.out.println("Factory:"+factory);					
		// Use the factory to get the instance to the configurer
		Java2ExcelDtd java2ExcelDtd = factory.defaultJava2ExcelDtd();
							
		// Construct the ExcelWritter
		ExcelWritter excelWritter = new ExcelWritter();
							
		ArrayList<String> list=new ArrayList<String>();
		for(int counter=0;counter<10;counter++){
			list.add((new Integer(counter)).toString());
		}
		// Get your collection ready
		Collection col = list;
		Collection col2 = list;
		// Add the collection to the excel
		// You can add as many of these as you want. Each one 
		// will go to a different excel Sheet.
		excelWritter.addReport(col, java2ExcelDtd);
		excelWritter.addReport(col2, java2ExcelDtd);
		// Finally, save the excel to a File or OutputStream
		try {
			excelWritter.saveTo(new File(path_to_file));
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println("Done:");
*/
		
		//Parser parser = new Parser();
		try{
			Java2ExcelDtdFactory factory = Java2ExcelDtdFactory.getInstance();
			Java2ExcelDtd java2ExcelDtd = factory.readFromFile(new FileInputStream(new File("c:\\Test.xml")));
			ArrayList<String> list=new ArrayList<String>();
			for(int counter=0;counter<10;counter++){
				list.add((new Integer(counter)).toString());
			}
			// Get your collection ready
			Collection col = list;
			

			ExcelWritter excelWritter = new ExcelWritter();

			excelWritter.addReport(list, java2ExcelDtd);
			excelWritter.saveTo(new File(path_to_file));
			System.out.println("Done");
		}catch(Exception ex){
			System.out.println("Exception:"+ex.getMessage());
		}
			
	}

}
