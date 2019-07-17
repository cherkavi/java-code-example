/*
 * JFrame_main.java
 *
 * Created on 7 квітня 2008, 7:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package open_office;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import java.util.HashMap;
import javax.swing.*;
import java.io.*;
/**
 *
 * @author Technik
 */
public class JFrame_main extends JFrame{
    JButton field_button_execute;
    
    /** Creates a new instance of JFrame_main */
    public JFrame_main() {
        super("OpenOffice");

        this.create_and_location_components();

        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setBounds(100,100,200,50);
        this.setVisible(true);
    }
    
    private void create_and_location_components(){
        // создание элементов
        this.field_button_execute=new JButton("Do it!");
        JPanel panel_main=new JPanel(new BorderLayout());
        // добавить слушателей
        this.field_button_execute.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_click();
            }
        });
        // расставить элементы
        panel_main.add(this.field_button_execute,BorderLayout.NORTH);
        this.getContentPane().add(panel_main);
    }
    
    /** соединение с OpenOffice, задание внутренним переменным значений, печать, закрытие*/
    private void example_writer(){
        new OpenOffice_writer_example();
    }
    /** соединение с OpenOffice работа с Calc*/
    private void example_calc(){
        new OpenOffice_calc_example();
    }
    /** соединение с OperOffice используя TCP/IP соединение на порт 8100*/
    private void example_connection(){
        OpenOffice_FirstConnection firstConnection1 = new OpenOffice_FirstConnection();
        try {
            firstConnection1.useConnection();
        }
        catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
    private void on_button_click(){
        System.out.println("Click");
        //example_writer();
        //example_connection();
        example_calc();
    }
}
