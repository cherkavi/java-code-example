/*
 * JFrame_main.java
 *
 * Created on 13 травня 2008, 17:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package html_table_decode;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;        
import java.io.*;
import javax.swing.table.TableModel;
/**
 *
 * @author Technik
 */
public class JFrame_main extends JFrame{
    /** панель на которой будут размещены все компоненты*/
    private JPanel field_panel_main;
    /** кнопка активации начала сканирования*/
    private JButton field_button_scan;
    /** кнопка активации экспорта из таблицы*/
    private JButton field_button_export;
    /** путь к файлу*/
    private JTextField field_textfield_path_scan;
    /** путь к файлу, в который нужно складировать информацию*/
    private JTextField field_textfield_path_export;
    /** таблица, в которой храняться все данные*/
    private JTable field_table_main;
    /** Creates a new instance of JFrame_main */
    public JFrame_main() {
        super();
        this.create_and_location_components();
        this.setBounds(100,100,500,500);
        this.setTitle("Сканирование таблицы HTML из файла");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
    }
    /** создать и расставить компоненты*/
    private void create_and_location_components(){
        // создать компоненты
        this.field_button_scan=new JButton("Scan");
        this.field_button_export=new JButton("Export");
        this.field_panel_main=new JPanel(new BorderLayout());
        this.field_table_main=new JTable();
        this.field_textfield_path_scan=new JTextField("D:\\all_cartridge.csv");
        this.field_textfield_path_export=new JTextField("D:\\all_cartridge_export.csv");
        // назначить слушателей
        this.field_button_scan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                on_button_scan_click();
            }
        });
        this.field_button_export.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_export_click();
            }
        });
        // расставить компоненты
        JPanel panel_wrap_textfield=new JPanel(new GridLayout(1,1));
        panel_wrap_textfield.setBorder(javax.swing.BorderFactory.createTitledBorder("Путь к файлу с HTML таблицей"));
        panel_wrap_textfield.add(this.field_textfield_path_scan);
        JPanel panel_scan=new JPanel(new GridLayout(2,1));
        panel_scan.add(panel_wrap_textfield);
        panel_scan.add(this.field_button_scan);

        JPanel panel_wrap_textfield_export=new JPanel(new GridLayout(1,1));
        panel_wrap_textfield_export.setBorder(javax.swing.BorderFactory.createTitledBorder("Путь к для экспорта"));
        panel_wrap_textfield_export.add(this.field_textfield_path_export);
        JPanel panel_export=new JPanel(new GridLayout(2,1));
        panel_export.add(panel_wrap_textfield_export);
        panel_export.add(this.field_button_export);
        
        this.field_panel_main.add(panel_scan,BorderLayout.NORTH);
        this.field_panel_main.add(new JScrollPane(this.field_table_main),BorderLayout.CENTER);
        this.field_panel_main.add(panel_export,BorderLayout.SOUTH);
        this.getContentPane().add(this.field_panel_main);
    }
    /** реакция на нажатие кнопки сканирования*/
    private void on_button_scan_click(){
        // проверка на существование файла
        File file=new File(this.field_textfield_path_scan.getText());
        if(file.exists()){
            try{
                // чтение данных из файла
                FileReader file_reader=new FileReader(file);
                BufferedReader buffered_reader=new BufferedReader(file_reader);
                StringBuffer string_buffer=new StringBuffer();
                String temp_string;
                while( (temp_string=buffered_reader.readLine())!=null){
                    string_buffer.append(temp_string);
                }
                file_reader.close();
                // создать таблицу из данных в файле                
                this.field_table_main.setModel((new Decode_HTML_Table("<tr","</tr>","<td","</td>",string_buffer)).get_table_model());
                this.field_table_main.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            }catch(IOException iox){
                System.out.println("JFrame_main#on_button_scan_click: IOException:"+iox.getMessage());
                javax.swing.JOptionPane.showMessageDialog(this,"Ошибка чтения данных из файла","Ошибка", JOptionPane.ERROR_MESSAGE);
            }catch(Exception ex){
                System.out.println("JFrame_main#on_button_scan_click: Exception:"+ex.getMessage());
                javax.swing.JOptionPane.showMessageDialog(this,"Не удалось преобразовать файл в таблицу","Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            javax.swing.JOptionPane.showMessageDialog(this,"файл не прочитан","Ошибка",JOptionPane.ERROR_MESSAGE);
        }
    }
    /** реакция на нажатие клавиши экспорта*/
    private void on_button_export_click(){
        try{
            TableModel_to_ODF exporter=new TableModel_to_ODF();
            //exporter.set_valid_column(new int[]{0,1,2,3});
            //exporter.set_column_header(new String[]{"one","two","three","four"});
            exporter.to_calc(this.field_table_main.getModel(),"table");
        }catch(Exception ex){
            System.out.println("Export to ODF Exception:"+ex.getMessage());
        }
        
    }
}
