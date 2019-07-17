/*
 * ComReader.java
 *
 * Created on 27 Октябрь 2007 г., 12:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;
import java.io.InputStream;
/**
 *
 * @author root
 */
public class ComReader implements Runnable{
    private InputStream inputstream;
    private Thread thread;
    private volatile boolean flag_run=false;
    private ComListener comlistener;
    private String string_from_inputstream;
    private String string_cr=new String(new byte[]{13,10});
    /** Creates a new instance of ComReader */
    public ComReader(InputStream _inputstream,ComListener _comlistener) throws Exception{
        if((_inputstream!=null)&&
          (_comlistener!=null)){
            this.inputstream=_inputstream;
            this.comlistener=_comlistener;
            this.thread=new Thread(this);
            this.flag_run=true;
            this.thread.start();
        }
        else{
            // не удалось запустить поток, т.к. нечего читать
            throw new Exception("InputStream=null or ComListener=null");
        }
    }
    /**
     * проверка потока на запущенность
     */
    public boolean isRun(){
        return this.flag_run;
    }
    /**
     * остановка потока
     */
    public void stop(){
        this.flag_run=false;
    }
    public synchronized void process_string_from_inputstream(){
        int position=this.string_from_inputstream.indexOf(this.string_cr);
        if(position>=0){
            this.comlistener.data_from_port(this.string_from_inputstream.substring(0,position));
            this.string_from_inputstream=this.string_from_inputstream.substring(position+2);
        }
    }
    /**
     * чтение данных
     */
    public void run() {
        while(flag_run){
            try{
                if(this.inputstream.available()>0){
                    byte[] temp_array=new byte[this.inputstream.available()];
                    this.inputstream.read(temp_array);
                    this.string_from_inputstream=this.string_from_inputstream+(new String(temp_array));
                    // обработка прочитанной строки
                }
            }
            catch(Exception e){
                // ошибка считывания данных
            }
        }
    }
    
}
