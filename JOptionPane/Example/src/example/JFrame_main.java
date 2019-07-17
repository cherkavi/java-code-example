/*
 * JFrame_main.java
 *
 * Created on 4 травня 2008, 12:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 *
 * @author Technik
 */
public class JFrame_main extends JFrame
                         implements modal_close{
    JButton button_show;
    JButton button_show_internal;
    /** Creates a new instance of JFrame_main */
    public JFrame_main() {
        super("Test OptionPane");
        create_and_place();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,100,150,100);
        this.setVisible(true);
    }
    /** create and place visual components*/
    private void create_and_place(){
        // create componenents
        this.button_show=new JButton("JFrame show");
        this.button_show_internal=new JButton("JInternalFrame show");
        // set listeners
        this.button_show.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_show_jframe();
            }
        });
        this.button_show_internal.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_show_jinternalframe();
            }
        });
        // placing components
        JPanel panel_main=new JPanel();
        panel_main.setLayout(new java.awt.GridLayout(2,1));
        panel_main.add(button_show);
        panel_main.add(button_show_internal);
        this.getContentPane().add(panel_main);
    }
    private void on_show_jframe(){
        System.out.println("JFrame");
        this.setVisible(false);
        new JFrame_show(this);
    }
    private void on_show_jinternalframe(){
        System.out.println("JInternal_frame");
        this.setVisible(false);
        new JInternalFrame_show(this);
    }

    public void modal_result(int result_value) {
        System.out.println("modal window has return result:"+result_value);
        this.setVisible(true);
    }
}
