/*
 * table_model.java
 *
 * Created on 30 січня 2008, 8:33
 *
 */

package com.cherkashin.vitaliy.sql;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.*;
/**
 * класс, расширяющий AbstractTableModel для отображения данных из базы данных
 */
public class table_model extends javax.swing.table.AbstractTableModel{
    private ResultSet field_resultset;
    private String[] field_columns;
    /** Набор данных, Заголовки */
    public table_model(ResultSet resultset,
                       String[] columns) {
        this.field_resultset=resultset;
        this.field_columns=columns;
    }
   
    public int getRowCount() {
        try{
            this.field_resultset.last();
            return this.field_resultset.getRow();
        }catch(SQLException e){
            return 0;
        }
    }

    public int getColumnCount() {
        try{
            return this.field_resultset.getMetaData().getColumnCount();
        }catch(SQLException e){
            return 0;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        try{
            this.field_resultset.absolute(rowIndex+1);
            return this.field_resultset.getString(columnIndex+1);
        }catch(Exception e){
            return "";
        }
    }

    public String getColumnName(int column) {
        try{
            return this.field_columns[column];
        }catch(Exception e){
            return Integer.toString(column);
        }
    }
    
    /**   статический метод для установки размеров столбцов в "готовой" таблице*/
    public static void setColumnWidth(JTable table,int[] width){
        for(int counter=0;counter<table.getColumnCount();counter++){
            try{
                table.getColumnModel().getColumn(counter).setPreferredWidth(width[counter]);
                //System.out.println("set "+width[counter]);
            }catch(Exception e){
                System.out.println("setColumnWidth Exception:"+e.getMessage());
            }
        }
    }
}
