/*
 * JInternalFrame_main.java
 *
 * Created on 21 травня 2008, 16:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;
import javax.swing.*;
import javax.swing.event.*;
/**
 *
 * @author Technik
 */
public class JInternalFrame_main extends JFrame{
    
    /** Creates a new instance of JInternalFrame_main */
    public JInternalFrame_main() {
        super("Курсор пример");
        JDesktopPane desktop=new JDesktopPane();
        this.getContentPane().add(desktop);
        
        this.getContentPane().add(desktop);
        this.setBounds(100,100,200,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        new JInternalFrame_current(desktop);
    }
    
}
