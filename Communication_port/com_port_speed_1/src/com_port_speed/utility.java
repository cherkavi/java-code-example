/*
 * utility.java
 *
 * Created on 2 ќкт€брь 2007 г., 9:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com_port_speed;

/**
 *
 * @author root
 */
interface write_to_JTextAreaWriter {
    public void write_to_JTextArea(int index,byte b);
}

public class utility {
    // copy from Object Array to String Array
    static String[] copy_object_to_string(Object[] source){
        String[] temp_string=new String[source.length];
        for(int i=0;i<source.length;i++){
            try{
               temp_string[i]=(String)source[i];
            }
            catch(Exception e){
                // 
            }
        }
        return temp_string;
    }
    // print String Array
    static void print_string(String[] s){
        for(int i=0;i<s.length;i++){
            System.out.println(i+":"+s[i]);
        }
    }
    // check current string present in Array 
    static boolean string_in_array(String s,String[] array_string){
        boolean return_value=false;
        for(int i=0;i<array_string.length;i++){
           if(array_string[i].equals(s)){
               return_value=true;
               break;
           }
        }
        return return_value;
    }
    static void print_array_byte(byte[] data){
        for(int i=0;i<data.length;i++){
            System.out.println(i+":"+data[i]);
        }
    }
    
}
