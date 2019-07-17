/*
 * table.java
 *
 * Created on 15 лютого 2008, 14:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cherkashin.vitaliy.sql;
import javax.swing.*;
import com.cherkashin.vitaliy.inifiles.IniFile;
import javax.swing.table.TableColumn;
/**
 * класс для работы с таблицей JTable
 */
public class table {
    /**
     * метод который удаляет из таблицы столбцы
     *(удаление происходит только в отображении, в модели столбцы остаются)
     * @param table исходная таблица, в которой нужно произвести удаления
     * @param delete_column_name массив строк, который содержит имена всех таблиц, которые нужно удалить
     */
    public static void delete_from_table(JTable table,String[] delete_column_name){
        for(int i=0;i<delete_column_name.length;i++){
            try{
                table.removeColumn(table.getColumn(delete_column_name[i]));
            }catch(Exception e){
                System.out.println("error in delete column by name or id:"+delete_column_name[i]);
            }
        }
    }
    /**
     * метод, который устанавливает на столбцы по их именам заданную ширину
     * @param table таблица, в которой будут изменены размеры
     * @param column_name массив имен столбцов
     * @param column_width массив значений ширины для столбцов
     */
    public static void set_width_on_name(JTable table,String[] column_name,int[] column_width){
        for(int i=0;i<column_name.length;i++){
            if(i<column_width.length){
                try{
                    table.getColumn(column_name[i]).setPreferredWidth(column_width[i]);
                }catch(Exception e){
                    System.out.println(" не удалось установить ширину для столбцов "+column_name[i]);
                }
            }else{
                System.out.println("неправильно заданы кол-ва параметров");
            }
        }
    }
    /**
     * метод, который устанавливает имена для столбцов по их именам в модели
     * @param table таблица, в которой будут происходить изменения
     * @param column_name имена столбцов в модели
     * @param column_title имена, которые нужно установить в качесте заголовков
     */
    public static void set_column_title_on_name(JTable table,
                                                String[] column_name,
                                                String[] column_title){
        for(int i=0;i<column_name.length;i++){
            if(i<column_title.length){
                try{
                    table.getColumn(column_name[i]).setHeaderValue(column_title[i]);
                }catch(Exception e){
                    System.out.println("ошибка при установке значения для столбца"+column_name[i]);
                }
            }else{
                System.out.println("нарушение размерности массивов");
            }
        }
    }
    /** статический метод для установки заголовков столбцов в "готовой" таблице 
     * @param table - таблица, в которой будут происходить изменения
     * @param titles - заголовки для таблицы
     */
    public static void set_column_titles(JTable table,
                                         String[] titles){
        for(int counter=0;counter<table.getColumnCount();counter++){
            try{
                table.getColumnModel().getColumn(counter).setHeaderValue(titles[counter]);
            }catch(Exception e){
                System.out.println("setColumnTitles Exception:"+e.getMessage());
            }
        }
    }
    
    /**
     * метод, который сохраняет в указанный файл (INI file) данные обо всех размерах таблицы
     * @param table таблица, с которой будут считаны данные
     * @param path_to_file путь к файлу
     * @param prefix_name преамбула, которая будет писать данные в файл prefix_name+"."+field_name
     */
    public static void save_column_width_and_position_to_ini(JTable table,
                                                             String path_to_file,
                                                             String section_name){
        com.cherkashin.vitaliy.inifiles.IniFile inifile=new com.cherkashin.vitaliy.inifiles.IniFile(path_to_file);
        for(int counter=0;counter<table.getColumnCount();counter++){
            try{
                String key_name=table.getColumnName(counter);
                String value_name=Integer.toString(table.getColumn(counter).getMinWidth());
                inifile.setValue(section_name,
                                 key_name,
                                 value_name);
            }catch(Exception e){
                System.out.println("INI file error in save "+e.getMessage());
            };
        }
        inifile.Update();
    }
    /**
     * метод, который читает данные из файла (INI) и задает размерность у столбцов таблицы
     * @param table таблица в которой будут установлены столбцы
     * @param path_to_file путь к файлу в котором находятся данные
     * @param section_name имя секции, в которой записаны данные
     */
    public static void get_column_width_and_position_from_ini(JTable table,
                                                              String path_to_file,
                                                              String section_name){
        com.cherkashin.vitaliy.inifiles.IniFile inifile=new com.cherkashin.vitaliy.inifiles.IniFile(path_to_file);
        for(int counter=0;counter<table.getColumnCount();counter++){
            try{
                table.getColumn(counter).setMinWidth(Integer.parseInt(inifile.getValue(section_name,
                                                                                       table.getColumnName(counter),
                                                                                       "50")
                                                                      )
                                                     );
            }catch(Exception e){
                System.out.println("Ini file error in read");
            }
        }
        
    }
}
















