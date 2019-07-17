/*
 * Main.java
 *
 * Created on 29 квітня 2008, 23:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package database_to_xml;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author Technik
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame=new JFrame("Выгрузка данных обо всех таблицах и полях базы данных в XML ");
        JDesktopPane desktop=new JDesktopPane();
        JInternalFrame internal_frame=new JInternalFrame();
        internal_frame.getContentPane().setLayout(new java.awt.GridLayout(1,1));
        internal_frame.getContentPane().add(new JPanel_main(desktop));
        internal_frame.setBounds(100,100,300,300);
        internal_frame.setVisible(true);
        desktop.add(internal_frame);
        frame.getContentPane().add(desktop);
        frame.setBounds(0,0,600,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
