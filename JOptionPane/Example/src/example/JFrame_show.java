/*
 * JFrame_show.java
 *
 * Created on 4 травня 2008, 12:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
/**
 *
 * @author Technik
 */
public class JFrame_show extends JFrame implements WindowListener{
    /**  parent JFrame */
    modal_close field_parent_frame;
    /** button for show JOptionPane*/
    JButton field_button_show;
    /** show MessageDialog */
    JRadioButton field_button_messagedialog;
    JRadioButton field_button_messagedialog_middle;
    JRadioButton field_button_confirmdialog;
    JRadioButton field_button_confirmdialog_middle;
    JRadioButton field_button_confirmdialog_full;
    JRadioButton field_button_optiondialog;
    JRadioButton field_button_optiondialog_middle;
    JRadioButton field_button_optiondialog_full;
    JRadioButton field_button_inputdialog;
    JRadioButton field_button_inputdialog_middle;
    JRadioButton field_button_inputdialog_full;
    /** Creates a new instance of JFrame_show */
    public JFrame_show(modal_close parent_frame) {
        super("Show on JFrame");
        this.field_parent_frame=parent_frame;
        this.create_and_place();
        this.addWindowListener(this);
        this.setBounds(100,100,250,400);
        this.setVisible(true);
    }

    private void create_and_place(){
        // create
        this.field_button_show=new JButton("show");
        
        this.field_button_messagedialog=new JRadioButton("MessageDialog");
        this.field_button_messagedialog_middle=new JRadioButton("MessageDialog_middle");
        this.field_button_confirmdialog=new JRadioButton("ConfirmDialog");
        this.field_button_confirmdialog_middle=new JRadioButton("ConfirmDialog_middle");
        this.field_button_confirmdialog_full=new JRadioButton("ConfirmDialog_full");
        this.field_button_optiondialog=new JRadioButton("OptionDialog");
        this.field_button_inputdialog=new JRadioButton("inputDialog");
        this.field_button_inputdialog_middle=new JRadioButton("inputDialog_middle");
        this.field_button_inputdialog_full=new JRadioButton("inputDialog_full");
        
            // create ButtonGroup
        javax.swing.ButtonGroup button_group=new javax.swing.ButtonGroup();
        button_group.add(this.field_button_messagedialog);
        button_group.add(this.field_button_messagedialog_middle);
        button_group.add(this.field_button_confirmdialog);
        button_group.add(this.field_button_confirmdialog_middle);
        button_group.add(this.field_button_confirmdialog_full);
        button_group.add(this.field_button_optiondialog);
        button_group.add(this.field_button_inputdialog);
        button_group.add(this.field_button_inputdialog_middle);
        button_group.add(this.field_button_inputdialog_full);
        // add listener's
        ActionListener action_listener=new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_show(e);
            }
        };
        this.field_button_show.addActionListener(action_listener);
        // placing
        JPanel panel_radiobutton=new JPanel(new GridLayout(11,1));
        panel_radiobutton.add(this.field_button_messagedialog);
        panel_radiobutton.add(this.field_button_messagedialog_middle);
        panel_radiobutton.add(this.field_button_confirmdialog);
        panel_radiobutton.add(this.field_button_confirmdialog_middle);
        panel_radiobutton.add(this.field_button_confirmdialog_full);
        panel_radiobutton.add(this.field_button_optiondialog);
        panel_radiobutton.add(this.field_button_inputdialog);
        panel_radiobutton.add(this.field_button_inputdialog_middle);
        panel_radiobutton.add(this.field_button_inputdialog_full);
        
        JPanel panel_main=new JPanel(new BorderLayout());
        panel_main.add(this.field_button_show,BorderLayout.SOUTH);
        panel_main.add(panel_radiobutton,BorderLayout.CENTER);
        this.getContentPane().add(panel_main);
    }

    private void on_button_show(ActionEvent e){
        // MessageDialog
        if(this.field_button_messagedialog.isSelected()){
            JOptionPane.showMessageDialog(this,
                                          "message");
        };
        // MessageDialog
        if(this.field_button_messagedialog_middle.isSelected()){
            JOptionPane.showMessageDialog(this,
                                          "Hello",
                                          "Title",
                                          JOptionPane.QUESTION_MESSAGE);
        };
        // ConfirmDialog
        if(this.field_button_confirmdialog.isSelected()){
            int return_value=JOptionPane.showConfirmDialog(this,
                                                           "hello message");
            if(return_value==JOptionPane.YES_OPTION){
                System.out.println("YES");
            }else{
                if(return_value==JOptionPane.NO_OPTION){
                    System.out.println("NO");
                }else{
                    if(return_value==JOptionPane.CANCEL_OPTION){
                        System.out.println("CANCEL");
                    }
                }
            }
        };
        // ConfirmDialog
        if(this.field_button_confirmdialog_middle.isSelected()){
            System.out.println("JOptionPane:"+JOptionPane.showConfirmDialog(this,
                                                                            "hello_message",
                                                                            "Title",
                                                                            JOptionPane.OK_CANCEL_OPTION));
        };
        // ConfirmDialog
        if(this.field_button_confirmdialog_full.isSelected()){
            System.out.println("JOptionPane:"+JOptionPane.showConfirmDialog(this,
                                                                            "hello_message",
                                                                            "Title",
                                                                            JOptionPane.OK_CANCEL_OPTION,
                                                                            JOptionPane.INFORMATION_MESSAGE));
        };
        // OptionDialog
        if(this.field_button_optiondialog.isSelected()){
            String[] option=new String[]{"Первый","Второй","Третий","Четвертый","Пятый"};
            int result=
            JOptionPane.showOptionDialog(this,
                                         "Choose of:",
                                         "Title",
                                         JOptionPane.DEFAULT_OPTION,
                                         JOptionPane.INFORMATION_MESSAGE,
                                         null,
                                         option,
                                         option[2]);
            System.out.println("result:"+result+"    value:"+option[result]);
        };
        // InputDialog
        if(this.field_button_inputdialog.isSelected()){
            System.out.println("Input:"+JOptionPane.showInputDialog(this,
                                                                    "message"));
        }
        // InputDialog
        if(this.field_button_inputdialog_middle.isSelected()){
            System.out.println("Input:"+JOptionPane.showInputDialog(this,
                                                                    "message",
                                                                    "value"));
        }
        // InputDialog
        if(this.field_button_inputdialog_full.isSelected()){
            String[] option=new String[]{"Первый","Второй","Третий","Четвертый","Пятый"};
            System.out.println("Input:"+JOptionPane.showInputDialog(this,
                                                                    "message",
                                                                    "title",
                                                                    JOptionPane.ERROR_MESSAGE,
                                                                    null,
                                                                    option,
                                                                    option[1]));
        }
        
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
