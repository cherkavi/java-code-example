/*
 * COM_port.java
 *
 * Created on 27 Октябрь 2007 г., 11:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;
import java.io.InputStream;
import java.io.OutputStream;
import com.siemens.icm.io.comm.RS232Port;
import javax.microedition.io.CommConnection;
import javax.microedition.io.Connector;
/*import javax.microedition.midlet.*;
import javax.microedition.io.*;
import com.siemens.icm.io.*;*/

/**
 *
 * @author root
 */
public class COM_port {
    private InputStream inputstream;
    private OutputStream outputstream;
    private CommConnection commConn;
    private boolean flag_open_port=false;
    private ComListener comlistener;
    private ComReader comreader;
    /** 
     * Адрес порта, скорость передачи, слушатель пришедших данных
     */
    public COM_port(String com_port_name,int baudrate,ComListener this_comlistener) throws Exception{
      String strCOM = "comm:com0;blocking=on;baudrate=9600";
      commConn = (CommConnection)Connector.open(strCOM);
      this.inputstream  = commConn.openInputStream();
      this.outputstream = commConn.openOutputStream();
      this.comlistener=this_comlistener;
      comreader=new ComReader(this.inputstream,this.comlistener);
      System.out.println("InputStream and OutputStream opened");
      this.flag_open_port=true;
    }
    public COM_port(ComListener this_comlistener) throws Exception{
        this("com0",9600,this_comlistener);
    }
    // Проверка на открытие порта
    public boolean com_is_open(){
        return this.flag_open_port;
    }
    /**
     * Возвращает поток ввода
     */
    public InputStream get_inputstream(){
        return this.inputstream;
    }
    /**
     * Возвращает поток вывода
     */
    public OutputStream get_outputstream(){
        return this.outputstream;
    }
    /**
     * запись данных в порт 
     */
    public int write_to_port(String write_string){
        int result=0;
        try{
            this.outputstream.write(write_string.getBytes());
            this.outputstream.write(13);
            this.outputstream.write(10);
            result=write_string.length();
        }
        catch(Exception e){
            result=0;
        }
        return result;
    }
    /**
     * Закрытие открытого порта
     */
    
    public void close(){
        if(this.flag_open_port){
            try{
                this.comreader.stop();
            }
            catch(Exception e){};
            try{
                this.inputstream.close();
            }
            catch(Exception e){};
            try{
                this.outputstream.close();
            }
            catch(Exception e){};
            try{
                this.commConn.close();
            }
            catch(Exception e){};
        }
    }
}
