import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import java.util.HashMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.log4j.*;

/** 
 * Данный класс отображает возможность вывода отчета Jasper Report в графические файлы 
 * @author cherkashinv
 */
public class ShowReport {
	private static String field_path_to_report="c:\\jasper_report_subreport.jrxml";
	static{
		org.apache.log4j.BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.DEBUG);
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		System.out.println("begin");
		try{
			System.out.println("    Compile report");
			JasperReport jasper_report=JasperCompileManager.compileReport(field_path_to_report);
			System.out.println("    Fill report:"+jasper_report);
			HashMap parameters=new HashMap();
			parameters.put("SUBREPORT_DIR", "c:\\");
			parameters.put("show_subreport_1", new Boolean(false));
			parameters.put("DataSource_sub1", new JREmptyDataSource());
			parameters.put("show_subreport_2", new Boolean(true));
			parameters.put("DataSource_sub2", new JREmptyDataSource());
			
			// IMPORTANT !!!   -    Bug into Jasper - не работает при компиляции без DataSource:
			//JasperPrint jasper_print=JasperFillManager.fillReport(jasper_report,parameters);
			JasperPrint jasper_print=JasperFillManager.fillReport(jasper_report,
																  parameters,
																  new JREmptyDataSource());
			System.out.println("    out report:"+jasper_print);
			// Export to PDF
			JasperExportManager.exportReportToPdfFile(jasper_print,"c:\\temp_report.pdf");
			System.out.println("report created ");
		}catch(Exception ex){
			System.out.println("report fail");
		}
		System.out.println("end");
	}
}
