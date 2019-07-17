/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jasperreport_example;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReport;
/**
 *
 * @author First
 */
public class JFrame_main extends JFrame{
    JTextField field_path;
    public JFrame_main(){
        super("jasper test");
        this.init_components();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 200, 70);
        this.setVisible(true);
    }
    
    private void init_components(){
        this.field_path=new JTextField("c://empty_temp.pdf");
        JButton field_button=new JButton("export to");
        
        field_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_click();
            }
        });
        
        JPanel panel_main=new JPanel();
        panel_main.setLayout(new BorderLayout());
        panel_main.add(field_path,BorderLayout.NORTH);
        panel_main.add(field_button,BorderLayout.SOUTH);
        this.getContentPane().add(panel_main);
    }
    
    private void on_button_click(){
        try{
            JasperReport jasper_report=JasperCompileManager.compileReport("c://Empty_report.jrxml");
            
            JasperPrint jasper_print=JasperFillManager.fillReport(jasper_report,
                                                                  new HashMap(),
                                                                  new JREmptyDataSource());
            // Export to PDF
            JasperExportManager.exportReportToPdfFile(jasper_print,this.field_path.getText());
        }catch(Exception ex){
            System.out.println("Exception in crate:"+ex.getMessage());
        }
    }
}
