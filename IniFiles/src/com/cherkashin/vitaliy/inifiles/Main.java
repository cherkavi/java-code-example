/*
 * Main.java
 *
 * Created on 15 лютого 2008, 15:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cherkashin.vitaliy.inifiles;
import java.util.*;
/**
 *
 * @author Technik
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filename="c://temp.properties";
        StringBuffer stringbuffer=new StringBuffer();
        HashMap hashmap=new HashMap();
        
        System.out.println("создание файла");
        IniFile inifile=new IniFile(filename);
        System.out.println("get value:"+inifile.getValue("section_3","value1"));
        inifile.setValue("section_3","value1","change_hello");
        inifile.setValue("section_2","value2","hello_changed");
        inifile.Update();
        //inifile.read_file_to_StringBuffer(stringbuffer,filename);
        //System.out.println(stringbuffer.toString());
        
        //inifile.from_StringBuffer_to_HashMap(stringbuffer,hashmap);
        //inifile.save_StringBuffer_to_file(stringbuffer,filename);
        System.out.println("end");
    }
    
}
