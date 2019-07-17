/*
 * JInternalFrame_example.java
 *
 * Created on 5 травня 2008, 22:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
/**
 *
 * @author Technik
 */
public class JInternalFrame_example extends JInternalFrame
                                    implements InternalFrameListener{
    private JButton field_button_modal;
    /** Creates a new instance of JInternalFrame_example */
    public JInternalFrame_example(JDesktopPane desktop) {
        super("example",
              true,
              false,
              true,
              false);
        this.addInternalFrameListener(this);
        this.setBounds(100,50,400,300);
        this.create_and_place_components();
        this.setVisible(true);
        desktop.add(this);
    }
    
    private void create_and_place_components(){
        // создание элементов
        this.field_button_modal=new JButton("show modal");
        // назначение слушателей
        this.field_button_modal.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_show();
            }
        });
        // расположение
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(this.field_button_modal,BorderLayout.SOUTH);
    }
    
    private void on_button_show(){
        System.out.println("button show click");
        String[] option=new String[]{"one","two","three"};
        int return_value=JInternalFrame_modal.get_option_dialog(
                this,
                "hello",
                "title",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                option,
                option[0]);
    }
    
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    public void internalFrameClosing(InternalFrameEvent e) {
    }

    public void internalFrameClosed(InternalFrameEvent e) {
    }

    public void internalFrameIconified(InternalFrameEvent e) {
    }

    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    public void internalFrameActivated(InternalFrameEvent e) {
    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
    }
    
}
