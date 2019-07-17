/*
 * for_each.java
 *
 * Created on 26 квітня 2008, 0:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package version_1_5;

import java.util.ArrayList;

/**
 * демонстрация перегруженного оператора For
 * который усовершенствован для перебора значений массива, коллекций...
 */
public class for_each {
    
    /** Creates a new instance of for_each */
    public for_each() {
        // ------ одномерный массив
        // создание массива
        int array[]=new int[10];
        for(int counter=0;counter<10;counter++){
            array[counter]=10-counter;
        }
        // перебор значений в массиве
        for(int counter:array){
            System.out.println(counter+" >>> ");
        }

        // ------ многомерный массив
        // создание многомерного массива
        int[][] mas=new int[10][10];
        for(int column_counter=0;column_counter<10;column_counter++){
            for(int row_counter=0;row_counter<10;row_counter++){
                mas[column_counter][row_counter]=(column_counter+1)*(row_counter+1);
            }
        }
        // перебор значений в многомерном масссиве
        for(int[] column:mas){
            for(int value:column){
                System.out.print(value+"  ");
            }
            System.out.println();
        }
        
        // ------- перебор значений в коллекциях
        // создание коллекции с заранее заданным типом
        ArrayList<String> list=new ArrayList();
        for(int counter=0;counter<10;counter++){
            list.add(Integer.toString(counter));
        }
        for(String value:list){
            System.out.println(value);
        }
    }
    
}
