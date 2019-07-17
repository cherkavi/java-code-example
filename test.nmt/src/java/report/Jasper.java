
package report;
import java.sql.*;
import java.util.HashMap;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import org.apache.log4j.Logger;
/**
 * класс, который обеспечивает экспорт данных с помощью JasperReport в различные форматы
 */
public class Jasper {
    public static int TYPE_PDF=0;
    public static int TYPE_XLS=1;
    public static int TYPE_RTF=2;
    /**
     * 
     * @param pathToPattern full path to jrxml report
     * @param resultset ResultSet of data
     * @param stream 
     * @return
     */
    public static boolean createReport(String pathToPattern,
                                       ResultSet resultset,
                                       String path_to_export_file,
                                       int type) {
        boolean return_value=false;
        Logger field_logger=Logger.getRootLogger();
        try{
            field_logger.debug("compile report");
            JasperReport jasper_report=JasperCompileManager.compileReport(pathToPattern);
            field_logger.debug("create print");
            JasperPrint jasper_print=JasperFillManager.fillReport(jasper_report,
                                                                  new HashMap(),
                                                                  new JRResultSetDataSource(resultset));

            
            field_logger.debug("export report ");
            if(type==TYPE_PDF){
                JasperExportManager.exportReportToPdfFile(jasper_print,path_to_export_file);
                return_value=true;
            }
            if(type==TYPE_XLS){
                JExcelApiExporter exporter=new JExcelApiExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper_print);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path_to_export_file);
                exporter.exportReport();
                return_value=true;
            }
            if(type==TYPE_RTF){
                JRRtfExporter exporter=new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper_print);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path_to_export_file);
                exporter.exportReport();
                return_value=true;
            }
            //JasperExportManager.exportReportToPdfStream(jasper_print, stream);
        }catch(JRException jrex) {
            field_logger.error("report JRException:"+jrex.getMessage());
            return_value=false;
        }catch(Exception ex){
            field_logger.error("report Exception:"+ex.getMessage());
            return_value=false;
        }
        return return_value;
    }
}
