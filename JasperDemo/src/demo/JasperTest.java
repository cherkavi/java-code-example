package demo;

import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * this is demo Jasper report 
  */
public class JasperTest {
	/** enter point
	 * @param args enter parameters 
	 */
	public static void main(String[] args){
		System.out.println("start test Jasper");
		try {
			// set logger
			org.apache.log4j.BasicConfigurator.configure();
			Logger.getRootLogger().setLevel(Level.INFO);
			JasperReport jasperReport = JasperCompileManager.compileReport("c://report1.jrxml");
			System.out.println("Jasper compiled");
			HashMap<String,String> parameters=new HashMap<String,String>();
			parameters.put("param_1", "Привет, это мой отчет ");

			System.out.println("Jasper printing");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasperReport,
				parameters, 
				new JREmptyDataSource());

			System.out.println("Jasper export");
			JasperExportManager.exportReportToPdfFile(jasperPrint,"c://simple2.pdf");
		} catch (JRException e) {
			System.out.println("error in trying write report:"+e.getMessage());
		}
		System.out.println("end test Jasper");
	}
}
