/*
 * JFrame_main.java
 *
 * Created on 29 червня 2008, 18:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package swing_shortcut;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Technik
 */
public class JFrame_main extends JFrame{
    JPanel panel_main;
    JButton button_first;
    JButton button_second;
    JTextField field_first;
    JTextField field_second;
    
    /** Creates a new instance of JFrame_main */
    public JFrame_main() {
        super("example shortcut");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,100,200,200);
        
        this.create_and_place_components();
        
        this.set_shortcut();
        
        this.set_shortcut_button();
        this.setVisible(true);
        
        //this.button_first.requestFocus();
    }
    
    /** set shortcut for element's*/
    private void set_shortcut(){
        InputMap input_map=this.getRootPane().getInputMap();
        ActionMap action_map=this.getRootPane().getActionMap();
        
        String action_name="action_1";
        input_map.put(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_MASK),action_name);
        action_map.put(action_name,new AbstractAction(){
           public void actionPerformed(ActionEvent e){
               System.out.println("active shortcut");
           } 
        });
    }
    private void set_shortcut_button(){
        InputMap input_map=this.button_first.getInputMap();
        ActionMap action_map=this.button_first.getActionMap();
        
        String action_name="action_1";
        input_map.put(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_MASK),action_name);
        action_map.put(action_name,new AbstractAction(){
           public void actionPerformed(ActionEvent e){
               System.out.println("active shortcut");
           } 
        });
    }
    
    /** creating and placing component's */
    private void create_and_place_components(){
        // create component's
            /** dimension for JButton*/
        final Dimension button_dimension=new Dimension(100,25);
            /** dimension for JTextField*/
        final Dimension textfield_dimension=new Dimension(150,35);
        panel_main=new JPanel();
        panel_main.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        button_first=new JButton("First"){
            public Dimension getMinimumSize(){
                return button_dimension;
            };
            public Dimension getPreferredSize(){
                return button_dimension;
            };
            public Dimension getMaximumSize(){
                return button_dimension;
            }
        };
        button_second=new JButton("Second"){
            public Dimension getMinimumSize(){
                return button_dimension;
            };
            public Dimension getPreferredSize(){
                return button_dimension;
            };
            public Dimension getMaximumSize(){
                return button_dimension;
            }
        };
        this.field_first=new JTextField(){
            public Dimension getMinimumSize(){
                return textfield_dimension;
            };
            public Dimension getPreferredSize(){
                return textfield_dimension;
            };
            public Dimension getMaximumSize(){
                return textfield_dimension;
            }
        };
        this.field_second=new JTextField(){
            public Dimension getMinimumSize(){
                return textfield_dimension;
            };
            public Dimension getPreferredSize(){
                return textfield_dimension;
            };
            public Dimension getMaximumSize(){
                return textfield_dimension;
            }
        };
        
        // add listener's
        this.button_first.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // request Focus setting
                field_second.requestFocus();
            }
        });
        this.button_second.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // request Focus settin
                field_first.requestFocus();
            }
        });
        // placing
        panel_main.add(this.button_first);
        panel_main.add(this.field_first);
        panel_main.add(this.button_second);
        panel_main.add(this.field_second);
        
        this.getContentPane().add(panel_main);
    }
}
