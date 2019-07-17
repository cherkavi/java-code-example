/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package junit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import org.apache.log4j.Logger;
/**
 *
 * @author First
 */
public class JFrame_main extends JFrame{
    Logger field_logger=Logger.getLogger(this.getClass());
    int field_x=10;
    int field_y=10;
    
    public int calculate(){
        return this.field_x+this.field_y;
    }
    
    public JFrame_main(){
        super("test frame");
        field_logger.debug("create components");
        init_components();
        field_logger.debug("set JFrame visible");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,200);
        this.setVisible(true);
    }
    
    /**
     *  создание компонентов
     */
    private void init_components(){
        field_logger.debug("create visual element's");
        JLabel label_1=new JLabel("this is label");
        final JTextField textfield_1=new JTextField();
        JButton button_1=new JButton("this is button");
        
        
        field_logger.debug("adding listener's");
        label_1.addMouseListener(new MouseListener(){

            public void mouseClicked(MouseEvent e) {
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
        
        textfield_1.addKeyListener(new KeyListener(){

            public void keyTyped(KeyEvent e) {
                field_logger.debug("key typed");
            }

            public void keyPressed(KeyEvent e) {
                field_logger.debug("key pressed");
            }

            public void keyReleased(KeyEvent e) {
                field_logger.debug("key released");
            }
        });
        
        button_1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                field_logger.debug("button_pressed");
            }
        });
        field_logger.debug("placing component's");
        JPanel panel_main=new JPanel();
        GroupLayout group_layout=new GroupLayout(panel_main);
        panel_main.setLayout(group_layout);
        GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
        GroupLayout.SequentialGroup group_layout_vertical=group_layout.createSequentialGroup();
        group_layout_horizontal.addGroup(group_layout.createParallelGroup()
                                         .addComponent(label_1,GroupLayout.Alignment.CENTER)
                                         .addComponent(textfield_1,GroupLayout.Alignment.CENTER)
                                         .addComponent(button_1,GroupLayout.Alignment.CENTER)
                );
        group_layout_vertical.addGroup(group_layout.createParallelGroup()
                                        .addComponent(label_1));
        group_layout_vertical.addGroup(group_layout.createParallelGroup()
                                        .addComponent(textfield_1));
        group_layout_vertical.addGroup(group_layout.createParallelGroup()
                                        .addComponent(button_1));
        group_layout.setHorizontalGroup(group_layout_horizontal);
        group_layout.setVerticalGroup(group_layout_vertical);
        this.getContentPane().add(panel_main);
    }
    
}
