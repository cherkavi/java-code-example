/*
 * JFrame_main.java
 *
 * Created on 28 травня 2008, 13:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jtable_title;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
/**
 *
 * @author Technik
 */
public class JFrame_main extends JFrame{
    private JButton field_button_print;
    private JButton field_button_fire;
    /** Creates a new instance of JFrame_main */
    public JFrame_main() {
        super("Заголовок таблиц, пример");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.create_and_place_components();
        this.setBounds(100,100,500,500);
        this.setVisible(true);
    }

    /** creating and placing components*/
    private void create_and_place_components(){
        JPanel panel_main=new JPanel(new BorderLayout());

        this.field_button_print=new JButton("print");
        this.field_button_fire=new JButton("fire");

        this.field_button_print.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_print_click();
            }
        });
        this.field_button_fire.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_fire_click();
            }
        });
        panel_main.add(this.field_button_fire,BorderLayout.NORTH);
        
        // -----------------------
        // самый простой вариант реализации - заголовок в одну строку, полное подобие JTableHeader
        //String[] column_title=new String[]{"one","two","three","four"};
        //int[] column_width=new int[]{100,150,100,150};
        //TableModel table_model=new DefaultTableModel(new Object[][]{new String[]{"0","1","2","3"},new String[]{"4","5","6","7"}},column_title);
        
        // ------------------------
        // сложный вариант реализации - многоэтажное меню
        int[] column_width=new int[]{100,150,100,150};
        String[] column_title=new String[]{"one","two","three","four"};
        TableModel table_model=new DefaultTableModel(new Object[][]{new String[]{"0","1","2","3"},new String[]{"4","5","6","7"}},column_title);
        JTable table=new JTable(table_model);
        table=new JTable(table_model);
        table.setTableHeader(null);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        table_panel table_with_title=new table_panel(table,new sub_title_panel(table.getColumnModel(),column_width));
        panel_main.add(new JScrollPane(table_with_title),BorderLayout.CENTER);
        
        panel_main.add(this.field_button_print,BorderLayout.SOUTH);
        this.getContentPane().add(panel_main);
    }
    /** fire click*/
    private void on_fire_click(){
        //this.field_table.getColumnModel().getColumn(0).setPreferredWidth(this.field_table.getColumnModel().getColumn(0).getPreferredWidth()+10);
    }
    /** print table */
    private void on_print_click(){
/*        try{
            if(this.field_table.print()==true){
                System.out.println("Print OK");
            }else{
                System.out.println("Print false");
            }
        }catch(java.awt.print.PrinterException ex){
            System.out.println("printer Exception:"+ex.getMessage());
        }*/
    }
}
// ---------------    EXTENDS title_panel
    class sub_title_panel extends title_panel{
        public sub_title_panel(TableColumnModel column_model,int[] column_width){
            super(column_model,column_width);
            this.fill_title_panel();
            this.fill_column_sizes_new();
            this.reconstruct_column();
        }
        /** получить новую панель с заданным Label*/
        private JPanel get_panel_with_label(String label){
            JPanel return_value=new JPanel();
            return_value.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            return_value.add(new JLabel(label));
            return return_value;
        }
        /** (наполнение объекта field_labels(JPanel)) */
        protected void fill_title_panel(){
            this.field_labels=new JPanel[7];
            this.field_labels[0]=this.get_panel_with_label("первый");
            this.field_labels[1]=this.get_panel_with_label("второй");
            this.field_labels[2]=this.get_panel_with_label("третий");
            this.field_labels[3]=this.get_panel_with_label("четвертый");
            
            this.field_labels[4]=this.get_panel_with_label("первый+второй");
            this.field_labels[5]=this.get_panel_with_label("третий+четвертый");

            this.field_labels[6]=this.get_panel_with_label("главный");
        }
        /** наполнение объекта field_column_sizes(Object,int[]{,,,}) - какие столбцы включает объект */
        protected void fill_column_sizes_new(){
            this.field_column_sizes=new HashMap();
            this.field_column_sizes.put(this.field_labels[0],new int[]{0});
            this.field_column_sizes.put(this.field_labels[1],new int[]{1});
            this.field_column_sizes.put(this.field_labels[2],new int[]{2});
            this.field_column_sizes.put(this.field_labels[3],new int[]{3});

            this.field_column_sizes.put(this.field_labels[4],new int[]{0,1});
            this.field_column_sizes.put(this.field_labels[5],new int[]{2,3});

            this.field_column_sizes.put(this.field_labels[6],new int[]{0,1,2,3});
        }
        /** расставить компоненты field_labels(JPanel) и назначить им ширину get_width_for_object(this.field_labels[counter]) */
        public void reconstruct_column(){
            // расставить элементы
            GroupLayout group_layout=new GroupLayout(this);
            this.setLayout(group_layout);
            GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
            GroupLayout.SequentialGroup group_layout_vertical=group_layout.createSequentialGroup();
            
            group_layout_vertical.addGroup(group_layout.createParallelGroup()
                                           .addComponent(this.field_labels[6])
                                           );
            group_layout_vertical.addGroup(group_layout.createParallelGroup()
                                           .addComponent(this.field_labels[4])
                                           .addComponent(this.field_labels[5])
                                           );
            group_layout_vertical.addGroup(group_layout.createParallelGroup()
                                           .addComponent(this.field_labels[0])
                                           .addComponent(this.field_labels[1])
                                           .addComponent(this.field_labels[2])
                                           .addComponent(this.field_labels[3])
                                           );
            
            group_layout_horizontal.addGroup(group_layout.createParallelGroup()
                                             .addComponent(this.field_labels[6],
                                                           this.get_width_for_object(this.field_labels[6]),
                                                           this.get_width_for_object(this.field_labels[6]),
                                                           this.get_width_for_object(this.field_labels[6])
                                                           )
                                             .addGroup(group_layout.createSequentialGroup()
                                                       .addComponent(this.field_labels[4],
                                                                     this.get_width_for_object(this.field_labels[4]),
                                                                     this.get_width_for_object(this.field_labels[4]),
                                                                     this.get_width_for_object(this.field_labels[4])
                                                                     )
                                                       .addComponent(this.field_labels[5],
                                                                     this.get_width_for_object(this.field_labels[5]),
                                                                     this.get_width_for_object(this.field_labels[5]),
                                                                     this.get_width_for_object(this.field_labels[5])
                                                                     )
                                                       )
                                             .addGroup(group_layout.createSequentialGroup()
                                                       .addComponent(this.field_labels[0],
                                                                     this.get_width_for_object(this.field_labels[0]),
                                                                     this.get_width_for_object(this.field_labels[0]),
                                                                     this.get_width_for_object(this.field_labels[0])
                                                                     )
                                                       .addComponent(this.field_labels[1],
                                                                     this.get_width_for_object(this.field_labels[1]),
                                                                     this.get_width_for_object(this.field_labels[1]),
                                                                     this.get_width_for_object(this.field_labels[1])
                                                                     )
                                                       .addComponent(this.field_labels[2],
                                                                     this.get_width_for_object(this.field_labels[2]),
                                                                     this.get_width_for_object(this.field_labels[2]),
                                                                     this.get_width_for_object(this.field_labels[2])
                                                                     )
                                                       .addComponent(this.field_labels[3],
                                                                     this.get_width_for_object(this.field_labels[3]),
                                                                     this.get_width_for_object(this.field_labels[3]),
                                                                     this.get_width_for_object(this.field_labels[3])
                                                                     )
                                                       )
                                            );
            
            group_layout.setHorizontalGroup(group_layout_horizontal);
            group_layout.setVerticalGroup(group_layout_vertical);
            // установка размерности для колонок
            for(int counter=0;counter<4;counter++){
                this.field_column_model.getColumn(counter).setMaxWidth(this.get_width_for_object(this.field_labels[counter]));
                this.field_column_model.getColumn(counter).setPreferredWidth(this.get_width_for_object(this.field_labels[counter]));
                this.field_column_model.getColumn(counter).setMinWidth(this.get_width_for_object(this.field_labels[counter]));
                
                if(this.field_column_model.getColumn(counter).getMaxWidth()!=this.field_column_model.getColumn(counter).getMinWidth()){
                    this.field_column_model.getColumn(counter).setMinWidth(this.get_width_for_object(this.field_labels[counter]));
                    this.field_column_model.getColumn(counter).setPreferredWidth(this.get_width_for_object(this.field_labels[counter]));
                    this.field_column_model.getColumn(counter).setMaxWidth(this.get_width_for_object(this.field_labels[counter]));                
                }
            }
            
        }
        
    }