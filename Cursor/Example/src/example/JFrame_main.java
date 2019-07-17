/*
 * JFrame_main.java
 *
 * Created on 21 травня 2008, 15:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.*;
/**
 *
 * @author Technik
 */
public class JFrame_main extends JFrame{
    
    /** Creates a new instance of JFrame_main */
    public JFrame_main() {
        super("Курсор пример");
        JButton button_set=new JButton("SET");
        JButton button_unset=new JButton("UNSET");
        button_set.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_set_click();
            }
        });
        button_unset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_unset_click();
            }
        });
        JPanel panel_main=new JPanel(new BorderLayout());
        panel_main.add(button_set,BorderLayout.NORTH);
        panel_main.add(button_unset,BorderLayout.SOUTH);
        this.getContentPane().add(panel_main);
        this.setBounds(100,100,200,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    private void on_button_set_click(){
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }
    private void on_button_unset_click(){
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
