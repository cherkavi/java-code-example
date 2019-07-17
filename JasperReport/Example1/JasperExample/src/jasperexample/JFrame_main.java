/*
 * JFrame_main.java
 *
 * Created on 21 березн€ 2008, 6:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jasperexample;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.*;

import java.sql.*;
/**
 *
 * @author Technik
 */
public class JFrame_main extends JFrame{
    
    /** Creates a new instance of JFrame_main */
    public JFrame_main() {
        super("Jasper example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel_main=new JPanel();
        panel_main.setLayout(new java.awt.BorderLayout());
        JLabel label=new JLabel("Click on button for run report");
        JButton button=new JButton("run");
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_click();
            }
        });
        panel_main.add(label,BorderLayout.NORTH);
        panel_main.add(button,BorderLayout.SOUTH);
        this.getContentPane().add(panel_main);
        this.setBounds(100,100,300,70);
        this.setVisible(true);
    }
    
    public HashMap get_parameters(){
        HashMap return_value=new HashMap();
        return_value.put("FIELD_TITLE","title of field");
        return_value.put("FIELD_2","hello");
        //return_value.put("textField-1","head of page");
        return return_value;
    }
    
    public void static_report(){
        try{
            System.out.println("begin work");
            // создать объект JasperReport на основании шаблона
            net.sf.jasperreports.engine.JasperReport report=
                    net.sf.jasperreports.engine.JasperCompileManager.compileReport(".//untitled_report_1.jrxml");
            
            // заполнить данными объект JasperReport
            JasperPrint report_print=JasperFillManager.fillReport(report,this.get_parameters(),new JREmptyDataSource());
            // экспортировать объект JasperReport
            net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfFile(report_print,".//out.pdf");
            
            this.show_file(".//out.pdf");
            System.out.println("end work");
        }catch(Exception e){
            System.out.println("Error in create Jasper Report "+e.getMessage());
        }
    }
    
    public void database_report() {
        java.sql.Connection connection=null;

        String user="SYSDBA";
        String password="masterkey";
        String driverName = "org.firebirdsql.jdbc.FBDriver";
        String database_protocol="jdbc:firebirdsql://";
        String database_dialect="?sql_dialect=3";
        String database_server=null;
        String database_port=null;
        String databaseURL = "jdbc:firebirdsql://localhost:3050/D://BAGS//DataBase//BAGS.GDB?sql_dialect=3";        
        try{
            
            System.out.println("begin");
            Class.forName(driverName);
            //System.out.println("?ю€?™ър Єюхфэшэхэш†="+databaseURL);
            connection=java.sql.DriverManager.getConnection(databaseURL,user,password);
            Statement statement=connection.createStatement();
            ResultSet resultset=statement.executeQuery("SELECT KOD,NAME FROM ASSORTMENT");
            //JasperReport jasper_report=JasperCompileManager.compileReport(".//classic.jrxml ");
            JasperReport jasper_report=JasperCompileManager.compileReport(".//untitled_report_1.jrxml");
            
            JasperPrint jasper_print=JasperFillManager.fillReport(jasper_report,
                                                                  new HashMap(),
                                                                  new JRResultSetDataSource(resultset));
            // Export to PDF
            JasperExportManager.exportReportToPdfFile(jasper_print,".//out.pdf");
            // Export to XLS
                JExcelApiExporter exporter=new JExcelApiExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper_print);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, ".//example.xls");
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                exporter.exportReport();
                
            this.show_file(".//example.xls");
            System.out.println("END");
        }catch(SQLException sqlexception){
            System.out.println("не удалось подключитьс€ к базе данных");
        }catch(ClassNotFoundException classnotfoundexception){
            System.out.println("не найден класс");
        }catch(Exception e){
            System.out.println("Other error;"+e.getMessage());
        }
    }
    public void dynamic_report(){
        ArrayList array_list=new ArrayList();
        HashMap temp_one=new HashMap();
        temp_one.put("KOD",new Integer(10));
        temp_one.put("NAME","value - значение");
        array_list.add(temp_one);
        array_list.add(temp_one);
        array_list.add(temp_one);   
        System.out.println("begin");

        try{
            //JasperReport jasper_report=JasperCompileManager.compileReport(".//classic.jrxml");
            JasperReport jasper_report=JasperCompileManager.compileReport(".//report_3.jrxml");
            /*JasperPrint jasper_print=JasperFillManager.fillReport(jasper_report,
                                                                  new HashMap(),
                                                                  new JRMapCollectionDataSource(array_list));*/
            JasperPrint jasper_print=JasperFillManager.fillReport(jasper_report,
                                                                  new HashMap(),
                                                                  //new JRMapArrayDataSource(new Object[]{temp_one,temp_one,temp_one})
                                                                  new JRMapArrayDataSource(array_list.toArray()));
            JasperExportManager.exportReportToPdfFile(jasper_print,".//out.pdf");
            this.show_file(".//out.pdf");
            // создать объект дл€ экспорта в ODF формат
            net.sf.jasperreports.engine.export.oasis.JROdtExporter odt_exporter=new net.sf.jasperreports.engine.export.oasis.JROdtExporter();
            // задать параметр который скажет что выводить
            odt_exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasper_print);
            // задать CHARSET
            odt_exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8"); 
            // задать параметр который скажет куда выводить
            odt_exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,".//out.odt");

            // сделать экспорт
            odt_exporter.exportReport();

            this.show_file("./out.odt");
        }catch(Exception e){
            System.out.println("Jasper error: "+e.getMessage());
        }
        System.out.println("END");
    }
    
    private void subreport(){
        java.sql.Connection connection=null;

        String user="SYSDBA";
        String password="masterkey";
        String driverName = "org.firebirdsql.jdbc.FBDriver";
        String database_protocol="jdbc:firebirdsql://";
        String database_dialect="?sql_dialect=3";
        String database_server=null;
        String database_port=null;
        String databaseURL = "jdbc:firebirdsql://localhost:3050/D://Cloth_Shop//DataBase//cloth.gdb?sql_dialect=3";        
        StringBuffer query=new StringBuffer();
        try{
            query.append("SELECT \n");
            query.append("	ARTICLE.NAME ARTICLE_NAME, \n");
            query.append("        SUM(COMMODITY.QUANTITY) QUANTITY \n");
            query.append("FROM COMMODITY \n");
            query.append("INNER JOIN ARTICLE ON ARTICLE.KOD=COMMODITY.KOD_ARTICLE \n");
            query.append("    INNER JOIN SUPPLIER ON SUPPLIER.KOD=ARTICLE.KOD_SUPPLIER \n");
            query.append("    INNER JOIN KIND ON KIND.KOD=ARTICLE.KOD_KIND \n");
            query.append("    LEFT JOIN PRICE ON PRICE.KOD=ARTICLE.KOD_PRICE \n");
            query.append("    LEFT JOIN SIZE_TABLE ON SIZE_TABLE.KOD=ARTICLE.KOD_SIZE_TABLE \n");
            query.append("WHERE COMMODITY.KOD_POINT_SOURCE=0 \n");
            query.append("GROUP BY \n");
            query.append("        ARTICLE.NAME \n");
            query.append("HAVING SUM(COMMODITY.QUANTITY)>0 \n");
            System.out.println("begin");
            Class.forName(driverName);
            
            connection=java.sql.DriverManager.getConnection(databaseURL,user,password);
            Statement statement=connection.createStatement();
            //System.out.println(query.toString());
            ResultSet resultset=statement.executeQuery(query.toString());
            
            JasperReport jasper_report=JasperCompileManager.compileReport(".//crosstab_1.jrxml");
            
            HashMap parameters=new HashMap();
            parameters.put("THIS_PATH_TO_DATABASE",databaseURL);
            parameters.put("THIS_LOGIN",user);
            parameters.put("THIS_PASSWORD",password);
            JasperPrint jasper_print=JasperFillManager.fillReport(jasper_report,
                                                                  parameters,
                                                                  new JRResultSetDataSource(resultset));
            // Export to PDF
            JasperExportManager.exportReportToPdfFile(jasper_print,".//out.pdf");
            // Export to XLS
                JExcelApiExporter exporter=new JExcelApiExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper_print);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, ".//example.xls");
		//exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                exporter.exportReport();
                
            this.show_file(".//example.xls");
            this.show_file("./out.pdf");
            System.out.println("END");
        }catch(SQLException sqlexception){
            System.out.println("не удалось подключитьс€ к базе данных:"+sqlexception.getMessage());
        }catch(ClassNotFoundException classnotfoundexception){
            System.out.println("не найден класс:"+classnotfoundexception.getMessage());
        }catch(Exception e){
            System.out.println("Other error;"+e.getMessage());
        }
    }
    
    private void crosstab(){
        java.sql.Connection connection=null;

        String user="SYSDBA";
        String password="masterkey";
        String driverName = "org.firebirdsql.jdbc.FBDriver";
        String database_protocol="jdbc:firebirdsql://";
        String database_dialect="?sql_dialect=3";
        String database_server=null;
        String database_port=null;
        String databaseURL = "jdbc:firebirdsql://localhost:3050/D://Cloth_Shop//DataBase//cloth.gdb?sql_dialect=3";        
        StringBuffer query=new StringBuffer();
        try{
            query.append("SELECT \n");
            query.append("	ARTICLE.NAME ARTICLE_NAME, \n");
            query.append("        SUM(COMMODITY.QUANTITY) QUANTITY \n");
            query.append("FROM COMMODITY \n");
            query.append("INNER JOIN ARTICLE ON ARTICLE.KOD=COMMODITY.KOD_ARTICLE \n");
            query.append("    INNER JOIN SUPPLIER ON SUPPLIER.KOD=ARTICLE.KOD_SUPPLIER \n");
            query.append("    INNER JOIN KIND ON KIND.KOD=ARTICLE.KOD_KIND \n");
            query.append("    LEFT JOIN PRICE ON PRICE.KOD=ARTICLE.KOD_PRICE \n");
            query.append("    LEFT JOIN SIZE_TABLE ON SIZE_TABLE.KOD=ARTICLE.KOD_SIZE_TABLE \n");
            query.append("WHERE COMMODITY.KOD_POINT_SOURCE=0 \n");
            query.append("GROUP BY \n");
            query.append("        ARTICLE.NAME \n");
            query.append("HAVING SUM(COMMODITY.QUANTITY)>0 \n");
            System.out.println("begin");
            Class.forName(driverName);
            
            connection=java.sql.DriverManager.getConnection(databaseURL,user,password);
            Statement statement=connection.createStatement();
            //System.out.println(query.toString());
            ResultSet resultset=statement.executeQuery(query.toString());
            
            JasperReport jasper_report=JasperCompileManager.compileReport(".//crosstab_size.jrxml");
            
            HashMap parameters=new HashMap();
            parameters.put("THIS_PATH_TO_DATABASE",databaseURL);
            parameters.put("THIS_LOGIN",user);
            parameters.put("THIS_PASSWORD",password);
            JasperPrint jasper_print=JasperFillManager.fillReport(jasper_report,
                                                                  parameters,
                                                                  new JRResultSetDataSource(resultset));
            // Export to PDF
            JasperExportManager.exportReportToPdfFile(jasper_print,".//out.pdf");
            // Export to XLS
                JExcelApiExporter exporter=new JExcelApiExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper_print);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, ".//example.xls");
		//exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                exporter.exportReport();
                
            this.show_file(".//example.xls");
            this.show_file("./out.pdf");
            System.out.println("END");
        }catch(SQLException sqlexception){
            System.out.println("не удалось подключитьс€ к базе данных:"+sqlexception.getMessage());
        }catch(ClassNotFoundException classnotfoundexception){
            System.out.println("не найден класс:"+classnotfoundexception.getMessage());
        }catch(Exception e){
            System.out.println("Other error;"+e.getMessage());
        }
    }
    
    /** открыть файл в среде Windows XP сопоставленной с данным типом этого файла программой*/
    public void show_file(String path_to_file){
        try{
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+path_to_file);
        }catch(Exception e){
            System.out.println("Error run File:"+path_to_file);
        }
    }
    public void on_button_click(){
        static_report();
        //database_report();
        //dynamic_report();
        //subreport();
        //crosstab();
    }
}





