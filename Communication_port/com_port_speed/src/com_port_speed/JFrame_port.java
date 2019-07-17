/*
 * JFrame_port.java
 *
 * Created on 2 ������� 2007 �., 11:34
 */

package com_port_speed;

import java.awt.*;
import java.util.Calendar;
import javax.swing.UIManager.*;
import javax.swing.UIManager;
/**
 *
 * @author  root
 */

/**
 * 
 * JFrame ��� ����������� ����������� � COM ����� � ��������� ������ �� ����
 * @see javax.swing.JFrame ���������� ��� ������� ������
 * 
 */
public class JFrame_port extends javax.swing.JFrame implements write_to_JTextAreaWriter{
    /**
     * ����, � ������� ����� ������� ������ ��� ������� COM �����
     */
    public com_port port=null;
    /** Creates new form JFrame_port */
    public JFrame_port() {
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize=this.getSize();
        this.setLocation(new Point( 
                                   (int)(screensize.getWidth()-framesize.getWidth())/2, 
                                   (int)(screensize.getHeight()-framesize.getHeight())/2) 
                         );
        initComponents();
        try{
            //UIManager.setLookAndFeel("javax.swing.plaf.basic.BasicLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception e){
            System.out.println("Error set UIManager");
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem4 = new javax.swing.JCheckBoxMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenu7 = new javax.swing.JMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jCheckBoxMenuItem5 = new javax.swing.JCheckBoxMenuItem();
        jButton_open_port = new javax.swing.JButton();
        jTextField_port_name = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_data_from_port = new javax.swing.JTextArea();
        jButton_close_port = new javax.swing.JButton();
        jTextField_code = new javax.swing.JTextField();
        jButton_send = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenu5 = new javax.swing.JMenu();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem4 = new javax.swing.JRadioButtonMenuItem();

        jMenuItem5.setText("Item");
        jPopupMenu1.add(jMenuItem5);

        jMenu6.setText("Menu");
        jCheckBoxMenuItem3.setText("CheckBox");
        jMenu6.add(jCheckBoxMenuItem3);

        jCheckBoxMenuItem4.setText("CheckBox");
        jMenu6.add(jCheckBoxMenuItem4);

        jPopupMenu1.add(jMenu6);

        jPopupMenu1.add(jSeparator2);

        jMenu7.setText("Menu");
        jPopupMenu1.add(jMenu7);

        jMenu8.setText("Menu1");
        jMenu8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu8ActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenu8);

        jMenuItem6.setText("Item");
        jPopupMenu2.add(jMenuItem6);

        jCheckBoxMenuItem5.setText("CheckBox");
        jPopupMenu2.add(jCheckBoxMenuItem5);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jButton_open_port.setBackground(new java.awt.Color(255, 102, 255));
        jButton_open_port.setComponentPopupMenu(jPopupMenu2);
        jButton_open_port.setForeground(new java.awt.Color(255, 255, 51));
        jButton_open_port.setToolTipText("this is tool tip textl");
        jButton_open_port.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton_open_port.setLabel("Open port");
        jButton_open_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_port_open(evt);
            }
        });

        jTextField_port_name.setText("COM1");
        jTextField_port_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_port_nameActionPerformed(evt);
            }
        });

        jTextArea_data_from_port.setColumns(20);
        jTextArea_data_from_port.setComponentPopupMenu(jPopupMenu1);
        jTextArea_data_from_port.setRows(5);
        jScrollPane1.setViewportView(jTextArea_data_from_port);

        jButton_close_port.setText("Close port");
        jButton_close_port.setEnabled(false);
        jButton_close_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close_port(evt);
            }
        });

        jTextField_code.setText("55");

        jButton_send.setText("Send data");
        jButton_send.setEnabled(false);
        jButton_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_data_to_port(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jMenu1.setText("Menu");
        jMenuItem1.setText("Item");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Item");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Menu");
        jCheckBoxMenuItem1.setText("CheckBox");
        jMenu2.add(jCheckBoxMenuItem1);

        jCheckBoxMenuItem2.setText("CheckBox");
        jCheckBoxMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem2ActionPerformed(evt);
            }
        });

        jMenu2.add(jCheckBoxMenuItem2);

        jMenu3.setText("Menu");
        jMenuItem3.setText("Item");
        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("Item");
        jMenu3.add(jMenuItem4);

        jMenu4.setText("Menu");
        buttonGroup1.add(jRadioButtonMenuItem1);
        jRadioButtonMenuItem1.setText("RadioButton");
        jMenu4.add(jRadioButtonMenuItem1);

        buttonGroup1.add(jRadioButtonMenuItem2);
        jRadioButtonMenuItem2.setText("RadioButton");
        jMenu4.add(jRadioButtonMenuItem2);

        jMenu3.add(jMenu4);

        jMenu3.add(jSeparator1);

        jMenu5.setText("Menu");
        buttonGroup1.add(jRadioButtonMenuItem3);
        jRadioButtonMenuItem3.setText("RadioButton");
        jMenu5.add(jRadioButtonMenuItem3);

        buttonGroup1.add(jRadioButtonMenuItem4);
        jRadioButtonMenuItem4.setText("RadioButton");
        jMenu5.add(jRadioButtonMenuItem4);

        jMenu3.add(jMenu5);

        jMenu2.add(jMenu3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jButton_send))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField_port_name)
                                    .addComponent(jButton_open_port, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton_close_port, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField_code, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(25, 25, 25)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField_port_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_open_port)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_close_port)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_send)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu8ActionPerformed

// TODO add your handling code here:
    }//GEN-LAST:event_jMenu8ActionPerformed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
// TODO add your handling code here:
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBoxMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem2ActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxMenuItem2ActionPerformed

    private void send_data_to_port(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_data_to_port
// TODO add your handling code here:
        try{
           this.port.write_to_port(new byte[]{Byte.decode(this.jTextField_code.getText())});    
        }
        catch(Exception e){
           this.jTextArea_data_from_port.setText(this.jTextArea_data_from_port.getText()+"\n - - error in conver value");
        }
    }//GEN-LAST:event_send_data_to_port

    private void close_port(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close_port
// TODO add your handling code here:
        if(this.port!=null){
            this.port.close_port();
            this.jButton_open_port.setEnabled(true);
            this.jButton_close_port.setEnabled(false);
            this.jButton_send.setEnabled(false);
        }
    }//GEN-LAST:event_close_port
    private void com_port_open(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_port_open
// TODO add your handling code here:
        try{
            this.port=new com_port(this.jTextField_port_name.getText(),500,this);
            this.jButton_open_port.setEnabled(false);
            this.jButton_close_port.setEnabled(true);
            this.jButton_send.setEnabled(true);
        }
        catch(Exception e){
            this.jTextArea_data_from_port.setText(this.jTextArea_data_from_port.getText()+"\n Error in open port"+e.getMessage());
        }

    }//GEN-LAST:event_com_port_open

    private void jTextField_port_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_port_nameActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jTextField_port_nameActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_port().setVisible(true);
            }
        });
    }

    public void write_to_JTextArea(int index,byte b) {
       Calendar c=(java.util.Calendar.getInstance());
       System.currentTimeMillis();
       this.jTextArea_data_from_port.setText(this.jTextArea_data_from_port.getText()+"\n  "+(index)+":"+b+"    "+c.getTime());
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_close_port;
    private javax.swing.JButton jButton_open_port;
    private javax.swing.JButton jButton_send;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem4;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextArea_data_from_port;
    private javax.swing.JTextField jTextField_code;
    private javax.swing.JTextField jTextField_port_name;
    // End of variables declaration//GEN-END:variables
    
}



