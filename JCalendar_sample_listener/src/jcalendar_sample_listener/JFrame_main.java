/*
 * JFrame_main.java
 *
 * Created on 8 травня 2008, 17:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jcalendar_sample_listener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import com.toedter.calendar.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author Technik
 */
public class JFrame_main extends JFrame{
    /** Creates a new instance of JFrame_main */
    public JFrame_main() {
        super("JCalendar listener demo");
        this.setBounds(100,100,200,100);
        JDateChooser field_calendar=new JDateChooser(new java.util.Date(),"dd.MM.yyyy");
        field_calendar.addPropertyChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Property:"+evt.getPropertyName()+" Old Value:"+evt.getOldValue()+"   New Value:"+evt.getNewValue());
            }
        });
        field_calendar.addPropertyChangeListener("date",new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("date is new:"+evt.getNewValue());
            }
        });

        
        JTextField field_textfield=new JTextField();
        field_textfield.addPropertyChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt){
                System.out.println("Property:"+evt.getPropertyName()+" Old Value:"+evt.getOldValue()+"   New Value:"+evt.getNewValue());
            }
        });
        field_textfield.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                System.out.println("JTextField actionevent");
            }
        });
        field_textfield.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
                System.out.println("Key typed:"+e.getKeyChar());
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                if(e.getSource() instanceof JTextField){
                    System.out.println("Text for read:"+((JTextField)e.getSource()).getText());
                }
            }
        });
        
        JButton field_jbutton=new JButton();
        field_jbutton.addPropertyChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("PropertyName:"+evt.getPropertyName()+"    OldValue:"+evt.getOldValue()+"   NewValue:"+evt.getNewValue());
            }
        });
        
        this.getContentPane().setLayout(new java.awt.GridLayout(3,1));
        this.getContentPane().add(field_calendar);
        this.getContentPane().add(field_textfield);
        this.getContentPane().add(field_jbutton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
}
