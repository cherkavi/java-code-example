/*
 * JFrame_main.java
 *
 * Created on 29 квітня 2008, 23:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package database_to_xml;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author Technik
 */
public class JPanel_main extends JPanel{
    java.sql.Connection field_connection=null;    
    /** ссылка на рабочий стол*/
    JDesktopPane field_desktop;
    /** путь к базе данных*/
    private JTextField field_path_to_base;
    /** имя пользователя */
    private JTextField field_login;
    /** пароль к базе данных*/
    private JTextField field_password;
    /** кнопка запуска анализа*/
    private JButton field_button_do_it;
    /** путь к файлу XML*/
    private JTextField field_path_to_xml;
    
    /** JInternalFrame сообщающий об ошибке */
    private JInternalFrame field_internal_frame_error;
    /** JInternalFrame сообщающий об успешном завершении*/
    private JInternalFrame field_internal_frame_ok;
    /** объект-источник и объект-приемник файла XML*/
    org.w3c.dom.Document field_document;
    /** Creates a new instance of JFrame_main */
    public JPanel_main(JDesktopPane desktop) {
        super();
        this.field_desktop=desktop;
        create_and_place_components();
    }
    
    /** создать и расставить визуальные компоненты*/
    private void create_and_place_components(){
        // создание элементов
        this.field_path_to_base=new JTextField("D:\\Battery_Shop\\Database\\battery_shop.gdb");
        this.field_login=new JTextField("SYSDBA");
        this.field_password=new JTextField("masterkey");
        this.field_button_do_it=new JButton("analize");
        this.field_path_to_xml=new JTextField("c:\\battery_shop.xml");
        // назначение обработчиков событий
        this.field_button_do_it.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_do_it_click();
            }
        });
        // расстановка элементов
        JPanel panel_path_to_base=this.get_panel_wrap(this.field_path_to_base,"Путь к базе данных");
        JPanel panel_login=this.get_panel_wrap(this.field_login,"Имя пользователя");
        JPanel panel_password=this.get_panel_wrap(this.field_password,"Пароль");
        JPanel panel_path_to_xml=this.get_panel_wrap(this.field_path_to_xml,"Путь к XML");
        this.setLayout(new java.awt.GridLayout(5,1));
        this.add(panel_path_to_base);
        this.add(panel_login);
        this.add(panel_password);
        this.add(this.field_button_do_it);
        this.add(panel_path_to_xml);
    }
    
    /** возвращает панель-обертку*/
    private JPanel get_panel_wrap(JComponent component,String title){
        JPanel return_value=new JPanel(new java.awt.GridLayout(1,1));
        return_value.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
        return_value.add(component);
        return return_value;
    }
    
    /** реакция нажатия на кнопку*/
    private void on_button_do_it_click(){
        if((this.field_path_to_base.getText().trim().equals(""))||(this.field_path_to_xml.getText().trim().equals(""))){
            this.field_desktop.add(this.get_frame_error("Error in filling binding fields"),0);
        }else{
            // попытка соединиться с базой данных
            if((connect_to_firebird()==true)&&(save_to_xml_from_connection()==true)){
                // попытка прочитать все данные и сохранить их в файле
                this.field_desktop.add(this.get_frame_ok(),0);
            }else{
                this.field_desktop.add(this.get_frame_error("Error connecting to database"),0);
            }
        }
    }
    /** создать файл XML из соединения с базой данных
     * connection!=null
     */
    private boolean save_to_xml_from_connection(){
        boolean return_value=false;
        try{
            // создать XML документ
            javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
            document_builder_factory.setValidating(false);
            document_builder_factory.setIgnoringComments(true);
            javax.xml.parsers.DocumentBuilder document_builder=document_builder_factory.newDocumentBuilder();
            this.field_document=document_builder.newDocument();
            java.sql.DatabaseMetaData meta_data=this.field_connection.getMetaData();
            // получить список всех таблиц (tables.getString("TABLE_NAME"))
            ResultSet tables=meta_data.getTables(this.field_connection.getCatalog(),null,null,new String[]{"TABLE"});
            // список NODE
            ArrayList<org.w3c.dom.Node> node_list=new ArrayList();
            while(tables.next()){
                node_list.add(get_node_from_table(tables.getString("TABLE_NAME")));
            }
            // создать документ XML
            org.w3c.dom.ProcessingInstruction processing_instruction= this.field_document.createProcessingInstruction("xml-stylesheet", "type=\"text/css\" href=\"http://www.domain.tld/style.css\" media=\"all\"");  
            org.w3c.dom.Element root_element=this.field_document.createElement("DATABASE");
            this.field_document.appendChild(processing_instruction);
            this.field_document.appendChild(root_element);
            org.w3c.dom.Element name_element=this.field_document.createElement("NAME");

            name_element.appendChild(this.field_document.createTextNode("database"));
            root_element.appendChild(name_element);
            root_element.appendChild(this.field_document.createTextNode("\n"));
            for(org.w3c.dom.Node current_node:node_list){
                root_element.appendChild(current_node);
                root_element.appendChild(this.field_document.createTextNode("\n"));
            }
            // сохранить документ XML
            if((!this.field_path_to_xml.getText().trim().equals(""))){
                // нужно сохранить данные в файл
                try{
                    javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
                    javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();  
                    javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(this.field_document); // Pass in your document object here  
                    java.io.FileWriter out=new java.io.FileWriter(this.field_path_to_xml.getText());
                    //string_writer = new Packages.java.io.StringWriter();  
                    javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
                    transformer.transform(dom_source, stream_result);  
                    //xml_string = string_writer.toString(); // At this point you could use the Servoy XMLReader plugin to read your XML String.  
                    //application.output(xml_string);                  
                    out.flush();
                    out.close();
                    return_value=true;
                }catch(Exception ex){
                    this.field_desktop.add(this.get_frame_error("Export to file Error"),0);
                }
            }
            
        }catch(Exception ex){
            this.field_desktop.add(this.get_frame_error("MetaData Error"+ex.getMessage()),0);
        }
        return return_value;
    }
    /** создать Node из java.sql.Connection и TABLE_NAME*/
    private org.w3c.dom.Node get_node_from_table(String table_name){
        org.w3c.dom.Node return_value=null;
        try{
            java.sql.Statement statement=this.field_connection.createStatement();
            java.sql.ResultSet table=statement.executeQuery("select * from "+table_name);

            org.w3c.dom.Element node_root_table=this.field_document.createElement("TABLE");
            org.w3c.dom.Element node_field=null;
            org.w3c.dom.Element node_temp=null;

            node_temp=this.field_document.createElement("NAME");
            node_temp.appendChild(this.field_document.createTextNode(table_name));
            node_root_table.appendChild(this.field_document.createTextNode("\n\t"));
            node_root_table.appendChild(node_temp);
            node_root_table.appendChild(this.field_document.createTextNode("\n"));
            for(int index=0;index<table.getMetaData().getColumnCount();index++){
                node_field=this.field_document.createElement("FIELD");

                node_field.appendChild(this.field_document.createTextNode("\n\t\t"));
                node_temp=this.field_document.createElement("NAME");
                node_temp.appendChild(this.field_document.createTextNode(table.getMetaData().getColumnName(index+1)));
                node_field.appendChild(node_temp);
                node_field.appendChild(this.field_document.createTextNode("\n"));
                
                node_field.appendChild(this.field_document.createTextNode("\t\t"));
                node_temp=this.field_document.createElement("PRIMARY_KEY");
                node_temp.appendChild(this.field_document.createTextNode("false"));
                node_field.appendChild(node_temp);
                node_field.appendChild(this.field_document.createTextNode("\n"));
                
                node_field.appendChild(this.field_document.createTextNode("\t\t"));
                node_temp=this.field_document.createElement("TYPE");
                node_temp.appendChild(this.field_document.createTextNode(table.getMetaData().getColumnClassName(index+1)));
                node_field.appendChild(node_temp);
                node_field.appendChild(this.field_document.createTextNode("\n"));
                
                node_field.appendChild(this.field_document.createTextNode("\t\t"));
                node_temp=this.field_document.createElement("LENGTH");
                if(table.getMetaData().getColumnClassName(index+1).indexOf(".String")>0){
                    node_temp.appendChild(this.field_document.createTextNode(Integer.toString(table.getMetaData().getColumnDisplaySize(index+1))));
                }else{
                    node_temp.appendChild(this.field_document.createTextNode("0"));
                }
                node_field.appendChild(node_temp);
                node_field.appendChild(this.field_document.createTextNode("\n"));
                
                node_field.appendChild(this.field_document.createTextNode("\t\t"));
                node_temp=this.field_document.createElement("IS_DATE");
                if(table.getMetaData().getColumnClassName(index+1).indexOf(".sql.Date")>0){
                    node_temp.appendChild(this.field_document.createTextNode("true"));
                }else{
                    node_temp.appendChild(this.field_document.createTextNode("false"));
                }
                
                node_field.appendChild(node_temp);
                node_field.appendChild(this.field_document.createTextNode("\n"));
                
                node_field.appendChild(this.field_document.createTextNode("\t\t"));
                node_temp=this.field_document.createElement("IS_TIMESTAMP");
                if(table.getMetaData().getColumnClassName(index+1).indexOf(".sql.Timestamp")>0){
                    node_temp.appendChild(this.field_document.createTextNode("true"));
                }else{
                    node_temp.appendChild(this.field_document.createTextNode("false"));
                }
                node_field.appendChild(node_temp);
                node_field.appendChild(this.field_document.createTextNode("\n"));
                
                node_field.appendChild(this.field_document.createTextNode("\t"));
                
                node_root_table.appendChild(this.field_document.createTextNode("\t"));
                node_root_table.appendChild(node_field);
                node_root_table.appendChild(this.field_document.createTextNode("\n"));
            }
            return_value=node_root_table;
        }catch(Exception ex){
            System.out.println("Error getting Node from table:"+table_name);
        }
        return return_value;
    }
    /** соединиться с базой данных*/
    public boolean connect_to_firebird(){
        boolean return_value=false;
        java.sql.Driver driver=null;
        java.sql.Statement statement=null;
        java.sql.ResultSet rs=null;
        java.sql.ResultSetMetaData rs_metadata=null;
        String query;
        //String databaseURL = "jdbc:firebirdsql:local:d:/work/sadik/sadik.gdb?sql_dialect=3";
        String databaseURL = "jdbc:firebirdsql://localhost:3050/"+this.field_path_to_base.getText()+"?sql_dialect=3";
        String user = this.field_login.getText();
        String password = this.field_password.getText();
        String driverName = "org.firebirdsql.jdbc.FBDriver";

        try{
            System.out.println("try to connect driver");
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            //org.firebirdsql.pool.FBWrappingDataSource dataSource=new org.firebirdsql.pool.FBWrappingDataSource();
            this.field_connection=java.sql.DriverManager.getConnection(databaseURL,user,password);
            System.out.println("firebird connected");
            return_value=true;
        }
        catch(Exception e){
            System.out.println("Error \n"+e.getMessage());
            return_value=false;
        }
        return return_value;
    }
    
    
    
    /** возвращает текст ошибки в окне*/
    private JInternalFrame get_frame_error(String title){
        this.field_internal_frame_error=new JInternalFrame("Error",false,true,false,false);
        this.field_internal_frame_error.getContentPane().add(new JLabel(title));
        int frame_width=300;
        int frame_height=100;
        this.field_internal_frame_error.setBounds(this.field_desktop.getWidth()/2-frame_width/2,
                                                  this.field_desktop.getHeight()/2-frame_height/2,
                                                  frame_width,
                                                  frame_height);
        this.field_internal_frame_error.setVisible(true);
        return this.field_internal_frame_error;
    }
    /** возвращает подтверждение того,что данные были успешно переброшены*/
    private JInternalFrame get_frame_ok(){
        this.field_internal_frame_ok=new JInternalFrame("OK",false,true,false,false);
        int frame_width=100;
        int frame_height=100;
        this.field_internal_frame_ok.setBounds(this.field_desktop.getWidth()/2-frame_width/2,
                                               this.field_desktop.getHeight()/2-frame_height/2,
                                               frame_width,
                                               frame_height);
        JButton button_ok=new JButton("OK");
        button_ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_close_internal_frame_ok();
            }
        });
        this.field_internal_frame_ok.add(button_ok);
        this.field_internal_frame_ok.setVisible(true);
        return this.field_internal_frame_ok;
    }
    /** */
    private void on_close_internal_frame_ok(){
        if(this.field_internal_frame_ok.isVisible()){
            try{
                this.field_internal_frame_ok.setClosed(true);
            }catch(PropertyVetoException pve){
                
            }
        }
    }
    
}






