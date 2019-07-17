/*
 * Tcom_port.java
 *
 * Created on 24 Октябрь 2007 г., 19:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com_port_in_out;

import javax.comm.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author root
 */
public class Tcom_port {
    private Icom_listener com_listener; // объект, которому будем передавать данные с порта
    private String port_name;// имя порта
    private boolean flag_open=false;// признак открытия файла
    private InputStream inputstream;// входной поток
    private OutputStream outputstream;// выходной поток
    private SerialPort serialport;// порт
    private CommPort commport;
    private CommPortIdentifier cpi;
    private Tcom_port_listener com_port_listener;
    
    /** Конструктор класса, который следит за данными из порта и отправляет данные в порт */
    public Tcom_port(String this_port_name,Icom_listener this_com_listener) {
        this.com_listener=this_com_listener;
        this.port_name=this_port_name;
    }
    /**
     * возвращаем признак открытия порта и его инициализации
     */
    public boolean isOpen(){
        return this.flag_open;
    }
    /**
     * возвращаем наименование порта
     */
    public String getPortName(){
        return this.port_name;
    }
    /**
     * Открытие и инициализация порта, возвращает строку сообщения об ошибке при инициализации, 
     * или же возвращает null - в случае успешного открытия и инициализации
     */
    public String open(){
        String result="";
        try{
            System.out.println("try open port, is name="+this.port_name);
            cpi=javax.comm.CommPortIdentifier.getPortIdentifier(this.port_name);
            commport=cpi.open("com_port",100);
            //serialport=(SerialPort) cpi.open("Test",100);
            outputstream=commport.getOutputStream();
            inputstream=commport.getInputStream();
            com_port_listener=new Tcom_port_listener(this.inputstream,this.com_listener);
            this.flag_open=true;
        }
        catch(Exception e){
            System.out.println("Error in open/initialize COM port:"+e.getMessage());
            result=e.getMessage();
            this.flag_open=false;
        }
        return result;
    }
    /**
     * Закрытие порта и потоков данных
     */
    public void close(){
        if(this.flag_open==true){
            // порт открыт, нужно закрыть все данные
            try{
                this.inputstream.close();
            }
            catch(Exception e){}
            try{
                this.outputstream.close();
            }
            catch(Exception e){}
            try{
                this.commport.close();
            }
            catch(Exception e){}
            try{
                if(this.com_port_listener.isRun()){
                    this.com_port_listener.stop();
                }
            }
            catch(Exception e){}
        }
        else {
            // порт не открыт
        }
    }
    /**
     * запись данных в порт, возврат ошибки в виде строки
     */
    public String write(String s){
        String result=null;
        if(this.isOpen()){
            try{
                System.out.println(" put String to COM1:"+s);
                byte[] temp_array=s.getBytes();
                for(int i=0;i<temp_array.length;i++){
                    System.out.println(i+":"+temp_array[i]);
                }
                this.outputstream.write(temp_array);
            }
            catch(Exception e){
                result=e.getMessage();
            }
        }
        else{
            result="Error write to port: port not open";
        }
        return result;
    }
    /**
     * запись данных в порт, возврат ошибки в виде строки
     */
    public String write(byte[] array_of_byte){
        String result=null;
        if(this.isOpen()){
            try{
                this.outputstream.write(array_of_byte);
            }
            catch(Exception e){
                result=e.getMessage();
            }
        }
        else{
            result="Error write to port: port not open";
        }
        return result;
    }
    /**
     * запись в порт данных с признаком перевода строки, возврат ошибки в виде строки
     */
    public String writeln(String s){
        return this.write(s+(char)13+(char)10);
    }
    /**
     * запись в порт данных с признаком перевода строки, возврат ошибки в виде строки
     */
    public String writeln(byte[] array_of_byte){
        byte[] temp_array=new byte[array_of_byte.length+2];
        temp_array[temp_array.length-2]=13;
        temp_array[temp_array.length-1]=10;
        return this.write(temp_array);
    }
}
