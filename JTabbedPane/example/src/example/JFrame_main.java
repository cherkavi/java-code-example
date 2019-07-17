/*
 * JFrame_main.java
 *
 * Created on 4 червня 2008, 9:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Technik
 */
public class JFrame_main extends JFrame{
    private JTabbedPane field_tabbed;
    
    /** Creates a new instance of JFrame_main */
    public JFrame_main() {
        super("TabbedPane Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        create_and_place_components();
        this.setBounds(100,100,300,300);
        this.setVisible(true);
    }
    
    private void create_and_place_components(){
        this.field_tabbed=new JTabbedPane(JTabbedPane.BOTTOM,
                                          JTabbedPane.WRAP_TAB_LAYOUT);
        JPanel panel_first=new JPanel(new BorderLayout());
        panel_first.setBorder(javax.swing.BorderFactory.createTitledBorder("First panel"));
        panel_first.setToolTipText("this is first panel");
        
        JPanel panel_second=new JPanel(new BorderLayout());
        panel_second.setBorder(javax.swing.BorderFactory.createTitledBorder("Second panel"));
        panel_second.setToolTipText("this is second panel");
        
        //this.field_tabbed.add(panel_first,"first");
        //this.field_tabbed.add(panel_second,"second");

        //this.field_tabbed.addTab("first",panel_first);
        //this.field_tabbed.addTab("second",panel_second);
        
        this.getContentPane().add(this.field_tabbed);
    }
    
}
