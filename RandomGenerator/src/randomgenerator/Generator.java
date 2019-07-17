/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package randomgenerator;

import java.util.Random;

/**
 * Класс предназначен для статического генерирования уникальных последовательностей в подобном к HEX формату
 */
public class Generator {
    	public final static String hexChars[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

        /**          
         * @param count - кол-во уникальных символов в строке
         * @return строку из случайных символов (HEX-символов) 
         */
        public static String getUniqueChar(int count){
            StringBuffer return_value=new StringBuffer();
            Random random=new java.util.Random();
            int temp_value;
            for(int counter=0;counter<count;counter++){
                temp_value=random.nextInt(hexChars.length);
                return_value.append(hexChars[temp_value]);
            }
            return return_value.toString();
        }
}
