package java_application_example;
/*
 * enter_point.java
 *
 * Created on 17 березня 2008, 22:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Technik
 */
public class combobox_example extends JFrame{
    JTextField field_JTextField;
    javax.swing.JComboBox field_JСomboBox;
    String[] main_array=new String[]{"one","two","three","four","five","six","seven"};
    /** Creates a new instance of enter_point */
    public combobox_example() {
        super("test application");
        // создание компонентов
        field_JTextField=new JTextField();
        field_JСomboBox=create_JComboBox();
        
        // добавить слушателя для изменений в событиях нажатия на кнопку
        field_JTextField.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                System.out.println("editor changed");
                //change_combobox_editor();

            }
        });
        this.field_JСomboBox.setRenderer(new ListCellRenderer(){
            
            public Component getListCellRendererComponent(JList list, 
                                                          Object value, 
                                                          int index, 
                                                          boolean isSelected, 
                                                          boolean cellHasFocus) {
                JLabel current_label=new JLabel();                                                          
                if(isSelected){
                    current_label.setText((String)value);
                    current_label.setForeground(Color.YELLOW);
                }else{
                    current_label.setText((String)value);
                    current_label.setForeground(Color.RED);
                    
                }
                return current_label;
            }
        });
        
        JPanel content=new JPanel();
        content.setLayout(new BorderLayout());
        content.add(this.field_JСomboBox,BorderLayout.SOUTH);
        // назначить необходимые атрибуты для JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(content);
        this.setBounds(100,100,200,100);
        this.setVisible(true);
    }
    /** метод, который реагирует на изменения в компоненте для редактирования*/
    private void change_combobox_editor(){
        this.field_JСomboBox.setModel(new javax.swing.DefaultComboBoxModel(this.get_array_by_patter(this.field_JTextField.getText())));
        this.field_JСomboBox.showPopup();
    }
    /**  метод, который создает JCombobox*/
    private JComboBox create_JComboBox(){
        // создать на основе массива данных
        JComboBox return_value=new JComboBox(this.get_array_by_patter(""));
        // установить элемент для редактирования
        return_value.setEditor(this.create_ComboBoxEditor());
        // разрешить редактирование
        return_value.setEditable(true);
        return return_value;
    }
    /**  назначить Editor для JComboBox*/
    private ComboBoxEditor create_ComboBoxEditor(){
        ComboBoxEditor return_value=new ComboBoxEditor(){
            public Component getEditorComponent() {
                return field_JTextField;
            }

            public void setItem(Object anObject) {
                System.out.println((String)anObject);
            }

            public Object getItem() {
                return null;
            }

            public void selectAll() {
            }

            public void addActionListener(ActionListener l) {
            }

            public void removeActionListener(ActionListener l) {
            }
            
        };
        return return_value;
    }
    
    private String[] get_array_by_patter(String value){
        if(value.equals("")){
            return this.main_array;
        }else{
            return new String[]{"one","four"};
        }
    }
}
