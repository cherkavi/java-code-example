/*
 * Main.java
 *
 * Created on 22 Сентябрь 2007 г., 22:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com_port;
import javax.comm.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author root
 */
class Utility{
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
    static void print_string(String[] s){
        for(int i=0;i<s.length;i++){
            System.out.println(i+":"+s[i]);
        }
    }
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
}

class Terminal implements Runnable{//,SerialPortEventListener
    InputStream inputstream;
    OutputStream outputstream;
    SerialPort serialport;
    CommPort commport;
    CommPortIdentifier cpi;
    Thread readThread;
    Terminal(){
        try{
            System.out.println("proba");
            cpi=javax.comm.CommPortIdentifier.getPortIdentifier("COM1");
            commport=cpi.open("Test",100);
            //serialport=(SerialPort) cpi.open("Test",100);

            outputstream=commport.getOutputStream();
            inputstream=commport.getInputStream();
            outputstream.write(55);
            outputstream.write(13);
            Thread.sleep(1000);
            if(inputstream.available()>0){
                byte[] b=new byte[inputstream.available()];
                inputstream.read(b);
                for (int i=0;i<b.length;i++){
                    System.out.println(i+":"+b[i]);
                }
            }
            System.out.println("end proba");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("----");
            System.out.println(e.getStackTrace());
        }
    }
    public void run() {
    }
    
}
class Port{
    public String[] get_aviable_ports(){
        ArrayList temp_list=new ArrayList();
        Enumeration port_list=javax.comm.CommPortIdentifier.getPortIdentifiers();
        while(port_list.hasMoreElements()){
            temp_list.add(((javax.comm.CommPortIdentifier)port_list.nextElement()).getName());
        }
        return Utility.copy_object_to_string(temp_list.toArray());
    }
    public SerialPort get_serial_port(String port_name,int baund,int timeout){
        SerialPort return_port=null;
        return return_port;
    }
    Port(){
        System.out.println("Constructor for SerialPort");
    }
}

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String com_port_name="COM1";
        /*Port port=new Port();
        Utility.print_string(port.get_aviable_ports());
        if(Utility.string_in_array(com_port_name,port.get_aviable_ports())){
            System.out.println(com_port_name+" aviable");
        }*/
        new Terminal();
        //(new JFrame_main()).setVisible(true);
    }
    
}
