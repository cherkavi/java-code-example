/*
 * enter_point.java
 *
 * Created on 17 березня 2008, 22:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package java_application_example;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Technik
 */
public class popup_example extends JFrame{
    JPopupMenu field_JPopupMenu;
    JLabel field_JLabel;
    javax.swing.JComboBox field_JСomboBox;
    /** Creates a new instance of enter_point */
    public popup_example() {
        super("test application");
        // создание компонентов
        field_JPopupMenu=create_JPopupMenu();
        field_JLabel=create_JLabel();
        field_JСomboBox=create_JComboBox();
        // добавить слушателей
        field_JLabel.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                field_JPopupMenu.show(field_JLabel,e.getX(),e.getY());
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
            
        });
        // расположение панелей
        JPanel temp_label=new JPanel(new BorderLayout());
        temp_label.add(field_JLabel,BorderLayout.NORTH);
        temp_label.setBorder(javax.swing.BorderFactory.createTitledBorder("wrap"));
        
        JPanel content=new JPanel();
        content.setLayout(new BorderLayout());
        content.add(temp_label,   BorderLayout.NORTH);
        content.add(this.field_JСomboBox,BorderLayout.CENTER);
        // назначить необходимые атрибуты для JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(content);
        this.setBounds(100,100,200,100);
        this.setVisible(true);
    }
    
    private JPopupMenu create_JPopupMenu(){
        JPopupMenu return_value=new JPopupMenu();
       
        JMenuItem m1=new JMenuItem("p1");
        JMenuItem m2=new JMenuItem("p2");
        JMenuItem m3=new JMenuItem("p3");
        
        JMenu sub_menu=new JMenu("sub_menu");
        JMenuItem m4_1=new JMenuItem("p4_1");
        JMenuItem m4_2=new JMenuItem("p4_2");
        JMenuItem m4_3=new JMenuItem("p4_3");
        sub_menu.add(m4_1);
        sub_menu.add(m4_2);
        sub_menu.add(m4_3);
        
        ActionListener actionlistener=new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getSource() instanceof JMenuItem){
                    System.out.println(((JMenuItem)e.getSource()).getActionCommand());
                }
            }
        };
        // добавить ActionCommand, которая не зависит от интернационализации
        m1.setActionCommand("p1");
        m2.setActionCommand("p2");        
        m3.setActionCommand("p3");
        m4_1.setActionCommand("p1");
        m4_2.setActionCommand("p2");        
        m4_3.setActionCommand("p3");
        
        m1.addActionListener(actionlistener);
        m2.addActionListener(actionlistener);
        m3.addActionListener(actionlistener);
        m4_1.addActionListener(actionlistener);
        m4_2.addActionListener(actionlistener);
        m4_3.addActionListener(actionlistener);
        
        return_value.add(m1);
        return_value.add(m2);
        return_value.add(m3);
        return_value.add(sub_menu);
        return return_value;
    }
    private JLabel create_JLabel(){
        JLabel return_value=new JLabel("description",JLabel.CENTER);
        return return_value;
    }
    private JComboBox create_JComboBox(){
        JComboBox return_value=new JComboBox(new String[]{"one","two","three"});
        return return_value;
    }
    
}
