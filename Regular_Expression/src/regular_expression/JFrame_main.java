/*
 * JFrame_main.java
 *
 */

package regular_expression;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * for displaying data
 */
public class JFrame_main extends JFrame{
    JTextField field_textfield_mask;
    JButton field_button;
    JTextArea field_area_source;
    JTextArea field_area_destination;
    /** Creates a new instance of JFrame_main */
    public JFrame_main() {
        super("Regular Expression");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,100,200,300);
        create_and_placing_components();
        
        this.setVisible(true);
    }
    /**
     * creating visual element's
     */
    private void create_and_placing_components(){
        // create components
        this.field_textfield_mask=new JTextField();
        this.field_button=new JButton("apply expression");
        this.field_area_source=new JTextArea();
        this.field_area_destination=new JTextArea();
        // set listeners
        this.field_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fill_source();
            }
        });
        // placing components
        JPanel panel_main=new JPanel(new GridLayout(2,1));
        JPanel panel_source=new JPanel(new BorderLayout());
        JPanel panel_destination=new JPanel(new BorderLayout());
        
        panel_source.add(this.field_area_source,BorderLayout.CENTER);
        panel_source.add(this.field_textfield_mask,BorderLayout.SOUTH);
        panel_destination.add(this.field_button,BorderLayout.NORTH);
        panel_destination.add(this.field_area_destination,BorderLayout.CENTER);
        
        panel_main.add(panel_source);
        panel_main.add(panel_destination);
        this.getContentPane().add(panel_main);
    }
    private void fill_source(){
        hsqldb data=new hsqldb();
        try{
            data.connecting();
            if(data.get_connection()!=null){
                this.field_area_source.setText("");
                ArrayList<String> values=data.get_data_from_database();
                for(int counter=0;counter<values.size();counter++){
                    this.field_area_source.append(values.get(counter));
                    this.field_area_source.append("\n");
                }
                data.disconnect();
            }
        }catch(Exception ex){
            try{
                data.disconnect();
            }catch(Exception e){};
            System.out.println("Error in filling data:"+ex.getMessage());
        }
    }
}
