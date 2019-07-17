/*
 * JInternalFrame_current.java
 *
 * Created on 21 травня 2008, 16:07
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
public class JInternalFrame_current extends JInternalFrame{
    
    /** Creates a new instance of JInternalFrame_current */
    public JInternalFrame_current(JDesktopPane desktop) {
        super("JInternalFrame",false,false,false);
        this.setBounds(1,1,190,190);
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
        
        desktop.add(this);
        this.setVisible(true);
    }
    private void on_button_set_click(){
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }
    private void on_button_unset_click(){
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
}
