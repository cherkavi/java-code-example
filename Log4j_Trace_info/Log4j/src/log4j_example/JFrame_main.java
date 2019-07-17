/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package log4j_example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author USER
 */
public class JFrame_main extends JFrame{
    private Logger field_logger;
    
    public JFrame_main(){
        super("Log4J Example");
        this.setBounds(100, 100, 300, 160);
        create_and_place_component();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        // -----------------------------------------
        // без файла конфигурации - корневой логгер
        // Logger create;
        //this.field_logger=Logger.getRootLogger();
        //BasicConfigurator.configure();

        // без файла конфигурации, логгер с настройками вывода
        this.field_logger=Logger.getLogger(JFrame_main.class);
        this.field_logger.setLevel(Level.INFO);
        //this.field_logger.addAppender();

        // ----------------------------------------
        // с файлом конфигурации
       // this.field_logger=Logger.getRootLogger();
       
    }

    /** create and place components*/
    private void create_and_place_component() {
        JLabel label_1=new JLabel("Label_1");
        JLabel label_2=new JLabel("Label_2");
        JButton button_1=new JButton("button_1");
        JButton button_2=new JButton("button_2");
        JTextField textfield_1=new JTextField(20);
        JTextField textfield_2=new JTextField(20);
        
        JPanel panel_main=new JPanel();
        GroupLayout group_layout=new GroupLayout(panel_main);
        panel_main.setLayout(group_layout);
        GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
        GroupLayout.SequentialGroup group_layout_vertical=group_layout.createSequentialGroup();
        group_layout_horizontal.addGroup(
                group_layout.createParallelGroup()
                .addComponent(label_1)
                .addComponent(textfield_1)
                .addComponent(label_2)
                .addComponent(textfield_2)
                .addComponent(button_1)
                .addComponent(button_2)
                   );
        group_layout_vertical.addGroup(group_layout.createParallelGroup()
            .addComponent(label_1));
        group_layout_vertical.addGroup(group_layout.createParallelGroup()
            .addComponent(textfield_1));
        group_layout_vertical.addGroup(group_layout.createParallelGroup()
            .addComponent(label_2));
        group_layout_vertical.addGroup(group_layout.createParallelGroup()
            .addComponent(textfield_2));
        group_layout_vertical.addGroup(group_layout.createParallelGroup()
            .addComponent(button_1));
        group_layout_vertical.addGroup(group_layout.createParallelGroup()
            .addComponent(button_2));
        
        button_1.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e) {
                 button_1_click();
            }
        });
        button_2.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e) {
                button_2_click();
            }
        });
        
        textfield_1.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
                textfield_1_keytyped();
            }

            public void keyPressed(KeyEvent e) {
                
            }

            public void keyReleased(KeyEvent e) {
                
            }
        });
        textfield_2.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
                textfield_2_keytyped();
            }

            public void keyPressed(KeyEvent e) {
                
            }

            public void keyReleased(KeyEvent e) {
                
            }
        });
        label_1.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                label_1_click();
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
        });
        label_2.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                label_2_click();
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
        });
                
                
        group_layout.setHorizontalGroup(group_layout_horizontal);
        group_layout.setVerticalGroup(group_layout_vertical);
        
        this.getContentPane().add(panel_main);
    }
    
    private void button_1_click(){
        this.field_logger.debug("debug: button_1");
        this.field_logger.info("debug: button_1");
    }
    private void button_2_click(){
        this.field_logger.debug("debug: button_2");
        this.field_logger.info("debug: button_2");
    }
    private void label_1_click(){
        this.field_logger.debug("debug: label_1");
        this.field_logger.info("debug: label_1");
    }
    private void label_2_click(){
        this.field_logger.debug("debug: label_2");
        this.field_logger.info("debug: label_2");
    }
    private void textfield_1_keytyped(){
        this.field_logger.debug("debug: textfield_1");
        this.field_logger.info("debug: textfield_1");
    }
    private void textfield_2_keytyped(){
        this.field_logger.debug("debug: textfield_2");
        this.field_logger.info("debug: textfield_2");
    }
}






