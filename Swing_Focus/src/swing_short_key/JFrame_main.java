/*
 * JFrame_main.java
 *
 * Created on 29 червня 2008, 18:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package swing_short_key;
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
        
        this.set_move_focus();
        
        this.set_focus_policy();        
        
        this.setVisible(true);
        
        this.button_first.requestFocus();
    }
    /** set policy for moving focus between components*/
    private void set_focus_policy(){
        FocusOrder focus_order=new FocusOrder();
        focus_order.add_component(this.button_second);
        focus_order.add_component(this.button_first);
        focus_order.add_component(this.field_second);
        focus_order.add_component(this.field_first);
        this.getContentPane().setFocusTraversalPolicy(focus_order);
        this.getContentPane().setFocusCycleRoot(true);
    }
    
    /** set key for moving focus*/
    private void set_move_focus(){
        HashSet forward=new HashSet();
        forward.add(AWTKeyStroke.getAWTKeyStroke('F',KeyEvent.CTRL_MASK));
        this.getContentPane().setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,forward);
        HashSet back=new HashSet();
        back.add(AWTKeyStroke.getAWTKeyStroke('F',KeyEvent.SHIFT_MASK));
        this.getContentPane().setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,back);
    }
    
    /** creating and placing component's */
    private void create_and_place_components(){
        // create component's
            /** dimension for JButton*/
        final Dimension button_dimension=new Dimension(100,25);
            /** dimension for JTextField*/
        final Dimension textfield_dimension=new Dimension(150,35);
        JPanel panel_main=new JPanel();
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
// -----------------------------------------------------------------------------
/** class for Ordering Focus*/
class FocusOrder extends ContainerOrderFocusTraversalPolicy{
    ArrayList<Component> component_list=new ArrayList();
    public Component getLastComponent(Container container_with_element){
        if(component_list.size()>0){
            return component_list.get(component_list.size()-1);
        }else{
            return null;
        }
    }
    
    public Component getFirstComponent(Container container_with_element){
        if(component_list.size()>0){
            return component_list.get(0);
        }else{
            return null;
        }
    }
    
    public Component getDefaultComponent(Container container_with_element){
        
        if(component_list.size()>0){
            return component_list.get(0);
        }else{
            return null;
        }
    }
    
    public Component getComponentAfter(Container container_with_element,Component current_component){
        int position=this.component_list.indexOf(current_component);
        if(position>=0){
            if(position<(this.component_list.size()-1)){
                // return next element
                return this.component_list.get(position+1);
            }else{
                // return first element
                return this.getFirstComponent(container_with_element);
            }
        }else{
            return this.getDefaultComponent(container_with_element);
        }
    }
    public Component getComponentBefore(Container container_with_element,Component current_component){
        int position=this.component_list.indexOf(current_component);
        if(position>=0){
            if(position>0){
                // return next element
                return this.component_list.get(position-1);
            }else{
                // return last element
                return this.getLastComponent(container_with_element);
            }
        }else{
            return this.getDefaultComponent(container_with_element);
        }
    }
    protected boolean accept(Component component){
        return (this.component_list.indexOf(component)>=0);
    }
    // ------------------
    /** for adding component into focus circuit */
    public void add_component(Component component){
        this.component_list.add(component);
    }
    /** for removing component from focus circuit */
    public boolean remove_component(Component component){
        return this.component_list.remove(component);
    }
    /** for delete all component from focus circuit */
    public void clear(){
        this.component_list.clear();
    }
    
}

