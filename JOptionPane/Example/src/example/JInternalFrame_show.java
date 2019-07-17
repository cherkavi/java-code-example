/*
 * JInternalFrame_show.java
 *
 * Created on 5 травня 2008, 22:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
/**
 *
 * @author Technik
 */
public class JInternalFrame_show extends JFrame
                                 implements WindowListener{
    /** родительское окно, куда осуществляется передача управления*/
    private modal_close field_parent_frame;

    /** Creates a new instance of JInternalFrame_show */
    public JInternalFrame_show(modal_close parent_frame) {
        super("JInternalFrame Modal Window Example");
        this.field_parent_frame=parent_frame;
        this.setBounds(100,100,600,400);
        JDesktopPane desktop=new JDesktopPane();
        this.add(desktop);
        this.addWindowListener(this);
        this.setVisible(true);
        new JInternalFrame_example(desktop);
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        this.setVisible(false);
        this.field_parent_frame.modal_result(0);
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
    
}
