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
	private static Logger field_logger=Logger.getLogger("ShowReport");
	private static String field_path_to_report="c:\\temp_report.jrxml";
	static{
		field_logger.setLevel(Level.ERROR);
		//org.apache.log4j.BasicConfigurator.configure();
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		field_logger.debug("begin");
		try{
			System.out.println("Compile report");
			JasperReport jasper_report=JasperCompileManager.compileReport(field_path_to_report);
			System.out.println("Fill report:"+jasper_report);
			HashMap parameters=new HashMap();
			parameters.put("parameter_1", "temp_value");
			// IMPORTANT !!!   -    Bug into Jasper - не работает при компиляции без DataSource:
			//JasperPrint jasper_print=JasperFillManager.fillReport(jasper_report,parameters);
			JasperPrint jasper_print=JasperFillManager.fillReport(jasper_report,
																  parameters,
																  new JREmptyDataSource());
			System.out.println("out report:"+jasper_print);
			// Export to PDF
			JasperExportManager.exportReportToPdfFile(jasper_print,"c:\\temp_report.pdf");
			// 	Export to Java 2D
				// создать рисунок
			BufferedImage buffered_image =new BufferedImage(500,400, BufferedImage.TYPE_INT_RGB);
				// получить 2D контекст для рисунка
			Graphics2D g2d=buffered_image.createGraphics();

			JRGraphics2DExporter exporter=new JRGraphics2DExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper_print);
				// экспортировать в контекст рисунка
			exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, g2d);
			exporter.setParameter(JRExporterParameter.PAGE_INDEX, new Integer(0));
			exporter.exportReport();
			// сохранить полученное изображение в файл
			ImageIO.write(buffered_image, "BMP",new File("c:\\out.bmp"));
			ImageIO.write(buffered_image, "JPG",new File("c:\\out.jpg"));

			
            // Export to Excel
			/*JExcelApiExporter exporter=new JExcelApiExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper_print);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "c:\\temp_report.xls");
            exporter.exportReport();*/
			System.out.println("report created ");
		}catch(Exception ex){
			field_logger.error("JasperReport Exception:"+ex.getMessage());
			System.out.println("report fail");
		}
		field_logger.debug("end");
	}
}
