/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testdio;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import org.apache.log4j.Logger;

/**
 *
 * @author First
 */
public class JFrameMain extends JFrame{
    private Logger field_logger=Logger.getLogger(this.getClass());
    private JTextArea field_history;
    private JTextField field_scan_line;
    private JTextField field_amount;
    private PointOfSaleTerminal terminal;
    public JFrameMain(){
        super("Dio test");
        init_components();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
        
        terminal=new PointOfSaleTerminal();
    }
    
    private void init_components(){
        this.field_logger.debug("create visual components");
        JButton field_button_clear=new JButton(this.getLabel_button_clear());
        JLabel field_label_amount=new JLabel(this.getLabel_amount());
        field_amount=new JTextField(10);
        field_amount.setEditable(false);
        field_amount.setHorizontalAlignment(JTextField.RIGHT);
        
        field_scan_line=new JTextField();
        field_scan_line.setHorizontalAlignment(JTextField.CENTER);
        JLabel field_label_scan_line=new JLabel(this.getLabel_scan_line());
        JButton field_button_scan=new JButton(this.getLabel_button_scan());
        
        field_history=new JTextArea();
        
        this.field_logger.debug("add listeners");
        field_button_clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_clear_click();
            }
        });
        field_button_scan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_button_scan_click();
            }
        });
        this.field_scan_line.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
                if(!e.isActionKey()){
                    field_amount.setText("");
                }
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
            
        });
        this.field_logger.debug("place visual components");
        JPanel panel_manager=new JPanel();
        panel_manager.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
        GroupLayout group_layout=new GroupLayout(panel_manager);
        panel_manager.setLayout(group_layout);
        GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
        GroupLayout.SequentialGroup group_layout_vertical=group_layout.createSequentialGroup();

        group_layout_vertical.addGroup(group_layout.createParallelGroup()
                                        .addGroup(group_layout.createSequentialGroup()
                                                    .addComponent(field_label_amount)
                                                    .addComponent(field_amount,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                                                 )
                                        .addGroup(group_layout.createSequentialGroup()
                                                    .addGap(7)
                                                    .addComponent(field_button_clear))
                                         );
        group_layout_horizontal.addGap(20);
        group_layout_horizontal.addGroup(group_layout.createParallelGroup()
                                                 .addComponent(field_label_amount)
                                                 .addComponent(field_amount,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                                                 );
        group_layout_horizontal.addGap(20);
        group_layout_horizontal.addComponent(field_button_clear,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE);

        group_layout.setVerticalGroup(group_layout_vertical);
        group_layout.setHorizontalGroup(group_layout_horizontal);

        JPanel panel_input=new JPanel();
        JPanel panel_input_label=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel_input_label.add(field_label_scan_line);
        panel_input.setLayout(new GridLayout(3,1));
        panel_input.add(panel_input_label);
        panel_input.add(field_scan_line);
        panel_input.add(field_button_scan);
        
        JPanel panel_main=new JPanel();
        panel_main.setLayout(new BorderLayout());
        panel_main.add(panel_input,BorderLayout.NORTH);
        panel_main.add(field_history,BorderLayout.CENTER);
        panel_main.add(panel_manager,BorderLayout.SOUTH);
        this.getContentPane().add(panel_main);
    }
    /**
     * @return caption for "label_scan_line"
     */
    private String getLabel_scan_line(){
        return "Line from scanner";
    }
    /**
     * @return caption for "button_clear"
     */
    private String getLabel_button_scan(){
        return "Calculate";
    }
    /**
     * @return caption for "button_clear"
     */
    private String getLabel_button_clear(){
        return "Clear";
    }
    /**
     * @return caption for "label_amount"
     */
    private String getLabel_amount(){
        return "Amount:";
    }
    /**
     * striking on button clear
     * 
     */
    private void on_button_clear_click(){
        field_logger.debug("button clear click:");
        this.field_amount.setText("");
        this.field_scan_line.setText("");
        this.field_history.setText("");
    }
    
    /**
     * striking on button scan
     */
    private void on_button_scan_click(){
        this.field_logger.debug("button_scan click:");
        //JOptionPane.showMessageDialog(this.getRootPane(),"Amount:" + terminal.calculateTotal(this.field_scan_line.getText().toUpperCase().trim()));
        float value=terminal.calculateTotal(this.field_scan_line.getText().toUpperCase().trim());
        this.field_amount.setText(Float.toString(value));
        field_history.append("Scan line:"+this.field_scan_line.getText()+"    Result=$"+value+"\n");
    }

}
