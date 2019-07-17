/*
 * Main.java
 *
 * Created on 18 ќкт€брь 2007 г., 15:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jaspertest;

import java.io.*;
import java.io.FileInputStream;
import java.util.HashMap;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import it.businesslogic.ireport.barcode.*;
/**
 *
 * @author root
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
	public static void main(String[] args){
		main_frame temp_main_frame=new main_frame();
                temp_main_frame.setVisible(true);
                String xml_filename="Untitled_report_1.jrxml";
                System.out.println("start test Jasper");
		/*try {
			try{
				File f=new File("reports/"+xml_filename);
				FileInputStream fileinputstream=new FileInputStream(f);
				while(fileinputstream.available()>0){
					byte[] temp_byte=new byte[100];
					int length=fileinputstream.read(temp_byte);
					String temp_string=new String(temp_byte,0,length);
					System.out.println(temp_string);
				}
			}
			catch(Exception e){
				System.out.println("Error in open file output stream "+e.getMessage());
			}*/
                try{
                it.businesslogic.ireport.barcode.BcImage.getBarcodeImage(13,"1234231",true,false,null,0,0);
                JasperReport jasperReport = JasperCompileManager.compileReport("reports/"+xml_filename);
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasperReport,
				new HashMap(), 
				new JREmptyDataSource());

		JasperExportManager.exportReportToPdfFile(jasperPrint,"reports/example.pdf");

                JExcelApiExporter exporter=new JExcelApiExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "reports/example.xls");
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                
		exporter.exportReport();
                
		} catch (JRException e) {
                    System.out.println("Error: "+e.getMessage());
                    //e.printStackTrace();
		}
		
		System.out.println("end test Jasper");
    }
    
}
