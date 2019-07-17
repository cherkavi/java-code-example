/*
 * title_panel.java
 *
 * Created on 29 травня 2008, 9:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jtable_title;

import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Technik
 */
public class title_panel extends JPanel{
    private String[] field_column_caption;
    /** панели, которые образуют первый ряд в виде заголовков для таблиц*/
    protected JPanel[] field_labels;
    /** ширина колонок и заголовков */
    private int[] field_column_width;
    /** кол-во колонок*/
    private int field_column_count;
    /** таблица с данными */
    protected TableColumnModel field_column_model;
    /** Map of sizes*/
    protected HashMap<Object,int[]> field_column_sizes;
    /** создание простой таблицы с одной строкой заголовка 
     * @param column_model модель таблицы данных
     * @param column_caption заголовки
     * @param column_width ширина заголовков
     */
    public title_panel(TableColumnModel column_model,String[] column_caption,int[] column_width) {
        super();
        this.field_column_caption=column_caption;
        this.field_column_count=column_caption.length;
        this.field_column_model=column_model;
        this.field_column_width=column_width;
        this.fill_title_panel();
        this.fill_column_sizes(this.field_labels,column_width);
        this.reconstruct_column();
    }
    /** создание таблицы с мульти-строкой заголовка 
     * @param column_model модель таблицы
     * @param column_width ширина столбцов таблицы <br>
     * необходимо переопределение/замена и вызов: 
     *                  
     *                  <b>fill_title_panel(); </b>(наполнение объекта field_labels(JPanel))<br>
     *                  <b>fill_column_sizes(); не переопределять</b>(наполнение объекта field_column_sizes(Object,int[]{,,,}) - какие столбцы включает объект) <br>                
     *                  <b>reconstruct_column(); </b>(расставить компоненты field_labels(JPanel) и назначить им ширину get_width_for_object(this.field_labels[counter]); 
     *                                                так же назначить ширину для ColumnModel)
     * 
     */
    public title_panel(TableColumnModel column_model,int[] column_width) {
        super();
        //this.field_column_caption=column_caption;
        //this.field_column_count=column_caption.length;
        this.field_column_model=column_model;
        this.field_column_width=column_width;
        //this.fill_title_panel();
        //this.fill_column_sizes(this.field_labels,column_width);
        //this.reconstruct_column();
    }
    
    /** get index from array*/
    public int get_index_from_array(Object object, Object[] array){
        int return_value=(-1);
        for(int counter=0;counter<array.length;counter++){
            if(array[counter]==object){
                return_value=counter;
                break;
            }
        }
        return return_value;
    }
    
    /** получить ширину для указанного объекта*/
    public int get_width_for_object(Object source){
        int[] array_of_int=this.field_column_sizes.get(source);
        if(array_of_int!=null){
            int value=0;
            for(int counter=0;counter<array_of_int.length;counter++){
                value+=this.field_column_width[array_of_int[counter]];
            }
            return value;
        }else{
            return 0;
        }
    }
    /** получить последний столбец из всего заданного диапазона */
    public int get_width_for_object_last(Object source){
        int[] array_of_int=this.field_column_sizes.get(source);
        if(array_of_int!=null){
            return this.field_column_width[array_of_int[array_of_int.length-1]];
        }else{
            return 0;
        }
    }
    /** получить последний индекс столбеца из всего заданного диапазона */
    public int get_column_for_object_last(Object source){
        int[] array_of_int=this.field_column_sizes.get(source);
        if(array_of_int!=null){
            return array_of_int[array_of_int.length-1];
        }else{
            return 0;
        }
        
    }
    
    
    /** получить объект, на котором была нажата левая клавиша мыши
     * @return null, в случае, когда не нужно производить изменения размера колонки <br>
     * @return Object над которым будет производиться манипуляция
     */
    public Object is_dragged_object(Object source,int mouse_position){
        // получить ширину объекта, который задан
        int width=this.get_width_for_object(source);
        if(width>0){
            if((mouse_position<width)&&(mouse_position>(width-10))){
                return source;
            }else{
                // клавиша нажата не в правой части объекта
                return null;
            }
        }else{
            return null;
        }
    }
        
    /** установить новую ширину для объекта*/
    public boolean set_dragged_object_width(Object source,int new_width){
        boolean return_value=false;
        // получить ширину объекта, который задан
        //int index=this.get_index_from_array(source,this.field_labels);
        int index=this.get_column_for_object_last(source);
        if(index>=0){
            try{
                //int width=((JPanel)source).getWidth();
                int width=this.get_width_for_object(source);
                if(new_width>width){
                    // новая ширина больше
                    this.field_column_width[index]=this.field_column_width[index]+new_width-width;
                    reconstruct_column();
                }else{
                    // новая ширина меньше
                    if(new_width>0){
                        this.field_column_width[index]=this.field_column_width[index]-(width-new_width);
                        reconstruct_column();
                    }else{
                        // новая ширина меньше нуля
                    }
                }
            }catch(Exception ex){
                // ошибка приведения типов
                return_value=false;
            }
        }else{
            // объект не найден среди тех, которым нужно было бы передать управление
            return_value=false;
        }
        return return_value;
    }
    /** добавить ко всем элементам, которые позволяют изменять свою форму слушателей событий мыши*/
    public void add_mouse_listener(MouseListener mouse_listener){
        for(int counter=0;counter<this.field_labels.length;counter++){
            this.field_labels[counter].addMouseListener(mouse_listener);
        }
    }

    /** fill Map column_sizes*/
    protected void fill_column_sizes(Object[] panels,int[] sizes){
        this.field_column_sizes=new HashMap();
        for(int counter=0;counter<panels.length;counter++){
            this.field_column_sizes.put(panels[counter],new int[]{counter});
        }
    }
    /** fill panel data */
    protected void fill_title_panel(){
        // создание панелей для отображения заголовков
        this.field_labels=new JPanel[this.field_column_count];
        for(int counter=0;counter<this.field_labels.length;counter++){
            this.field_labels[counter]=new JPanel();
            this.field_labels[counter].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            this.field_labels[counter].setLayout(new FlowLayout(FlowLayout.CENTER));
            this.field_labels[counter].add(new JLabel(this.field_column_caption[counter]));
        };
        // перестроить колонки
        //this.reconstruct_column();
    }
    /** перестроить колонки и заголовки, установить для каждого свою ширину, получив ее из this.get_width_for_object*/
    public void reconstruct_column(){
        // расстановка панелей для отображения заголовков
        GroupLayout group_layout=new GroupLayout(this);
        this.setLayout(group_layout);
        GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
        GroupLayout.SequentialGroup group_layout_vertical=group_layout.createSequentialGroup();

        GroupLayout.ParallelGroup temp_vertical=group_layout.createParallelGroup();
        GroupLayout.ParallelGroup[] temp_horizontal=new GroupLayout.ParallelGroup[this.field_labels.length];
        int label_width;
        for(int counter=0;counter<this.field_labels.length;counter++){
            temp_vertical.addComponent(this.field_labels[counter]);

            temp_horizontal[counter]=group_layout.createParallelGroup();
            label_width=this.get_width_for_object(this.field_labels[counter]);
            // установка ширины ячеек
            this.field_column_model.getColumn(counter).setMaxWidth(label_width);
            this.field_column_model.getColumn(counter).setPreferredWidth(label_width);
            this.field_column_model.getColumn(counter).setMinWidth(label_width);

            if(this.field_column_model.getColumn(counter).getMaxWidth()!=this.field_column_model.getColumn(counter).getMinWidth()){
                this.field_column_model.getColumn(counter).setMinWidth(label_width);
                this.field_column_model.getColumn(counter).setPreferredWidth(label_width);
                this.field_column_model.getColumn(counter).setMaxWidth(label_width);                
            }
            
            temp_horizontal[counter].addComponent(this.field_labels[counter],label_width,label_width,label_width);
            group_layout_horizontal.addGroup(temp_horizontal[counter]);
        }
        group_layout_vertical.addGroup(temp_vertical);
        group_layout.setVerticalGroup(group_layout_vertical);
        group_layout.setHorizontalGroup(group_layout_horizontal);
        //this.field_table.updateUI();
    }
    
    
}
