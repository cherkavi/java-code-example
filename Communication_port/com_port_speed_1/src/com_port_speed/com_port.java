/*
 * com_port.java
 *
 * Created on 2 ќкт€брь 2007 г., 9:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com_port_speed;


import javax.comm.*;
import java.util.*;
import java.io.*;
import javax.swing.JTextArea;
/**
 *
 * @author root
 */

interface transmit_data_to_jTextArea{
    public void set_transmitter(JTextArea destination);
}
/*        
public class com_port implements SerialPortEventListener,transmit_data_to_jTextArea{
    InputStream inputstream;
    OutputStream outputstream;
    SerialPort serialport;
    CommPort commport;
    CommPortIdentifier cpi;
    Thread readThread;
    String port_name;
    LinkedList data_from_port; 
    JTextArea JTextArea_destination=null;
    public static String[] get_aviable_ports(){
        ArrayList temp_list=new ArrayList();
        Enumeration port_list=javax.comm.CommPortIdentifier.getPortIdentifiers();
        while(port_list.hasMoreElements()){
            temp_list.add(((javax.comm.CommPortIdentifier)port_list.nextElement()).getName());
        }
        return utility.copy_object_to_string(temp_list.toArray());
    }

    // Constructor
    public com_port(String com_port_name) {
        this.port_name=com_port_name;
        // port initializing
        try{
            cpi=CommPortIdentifier.getPortIdentifier(com_port_name);
            serialport=(SerialPort)cpi.open("com_port_speed",2000);
        }
        catch(PortInUseException e){
            System.out.println("Port "+com_port_name+" already in use \n"+e.getMessage());
        }
        catch(Exception e){
            System.out.println("Initializing: Unknown Error \n"+e.getMessage());
        }
        // get Input and Output Stream
        try{
            this.inputstream=serialport.getInputStream();
            this.outputstream=serialport.getOutputStream();
        }
        catch(Exception e){
            System.out.println("Open Input and Output Stream: Unknown Error \n"+e.getMessage());
        }
        // set com port listener
        try{
            serialport.addEventListener(this);
        }
        catch(Exception e){
            System.out.println("Set com port Listener: Unknown Error\n"+e.getMessage());
        }
        // set port parameters
        try{
            serialport.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,serialport.PARITY_NONE);
        }
        catch(javax.comm.UnsupportedCommOperationException e){
            System.out.println("Set port parameters: operation not support \n"+e.getMessage());
        }
        catch(Exception e){
            System.out.println("Set port parameters: Unknown Error\n"+e.getMessage());
        }
        this.data_from_port=new LinkedList();
    }

    public boolean write_to_port(byte[] array_of_byte){
        try{
            this.outputstream.write(array_of_byte);
            this.outputstream.flush();
            return true;
        }
        catch(Exception e){
            System.out.println("Error in write to port");
            return false;
        }
    }
    public int data_from_port_aviable(){
        return this.data_from_port.size();
    }
    public byte[] get_data_from_port(){
        byte[] temp_byte=null;
        if(this.data_from_port_aviable()>0){
            temp_byte=new byte[this.data_from_port.size()];
            for(int i=0;i<temp_byte.length;i++){
                temp_byte[i]=(Byte)this.data_from_port.get(i);
            }
        }
        return temp_byte;
    }
    // Listener data from port:
    public void serialEvent(javax.comm.SerialPortEvent event){
        System.out.println("serial event");
        switch(event.getEventType()){
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:break;
            case SerialPortEvent.DATA_AVAILABLE:
                try{
                   byte[] bytes_from_port=new byte[inputstream.available()];
                   inputstream.read(bytes_from_port);
                   for(int i=0;i<bytes_from_port.length;i++){
                       this.data_from_port.push(new Byte(bytes_from_port[i]));
                   }
                   if(this.JTextArea_destination!=null){
                      for(int i=0;i<bytes_from_port.length;i++){
                         this.JTextArea_destination.setText(this.JTextArea_destination.getText()+"\n"+i+":"+bytes_from_port[i]);
                      }
                   }
                }
                catch(Exception e){
                    System.out.println("Error in read data from port into SerialPortEvent");
                }
            break;    
        }
    }

    public void close_port(){
        try{
            if(this.inputstream!=null){
                inputstream.close();
            }
        }
        catch(Exception e){
            System.out.println("Error in close "+e.getMessage());
        }
        try{
            if(this.outputstream!=null){
                outputstream.close();
            }
        }
        catch(Exception e){
            System.out.println("Error in close "+e.getMessage());
        }

        try{
            if(this.serialport!=null){
                this.serialport.close();
            }
        }
        catch(Exception e){
            System.out.println("Error in close "+e.getMessage());
        }
       
    }
    public void finalize(){
        try{
            if(serialport!=null){
                serialport.close();
            }
        }
        catch(Exception e){
            System.out.println("error close port:"+this.port_name);
        }
    }

    public void set_transmitter(JTextArea destination) {
       this.JTextArea_destination=destination;
    }
}
*/
//
public class com_port implements Runnable{
    InputStream inputstream;
    OutputStream outputstream;
    CommPort commport;
    CommPortIdentifier cpi;
    Thread readThread;
    String port_name;
    LinkedList data_from_port; 
    Thread main_thread=null;
    write_to_JTextAreaWriter outer_component;
    public int buffer_size=100;
    public static String[] get_aviable_ports(){
        ArrayList temp_list=new ArrayList();
        Enumeration port_list=javax.comm.CommPortIdentifier.getPortIdentifiers();
        while(port_list.hasMoreElements()){
            temp_list.add(((javax.comm.CommPortIdentifier)port_list.nextElement()).getName());
        }
        return utility.copy_object_to_string(temp_list.toArray());
    }

    // Constructor
    public com_port(String com_port_name, int max_buffer_size,write_to_JTextAreaWriter outer_component_writer) throws Exception {
        this.port_name=com_port_name;
        this.outer_component=outer_component_writer;
        this.buffer_size=max_buffer_size;
        // port initializing
        try{
            cpi=javax.comm.CommPortIdentifier.getPortIdentifier(com_port_name);
            commport=cpi.open("Test",100);
        }
        catch(PortInUseException e){
            System.out.println("Port "+com_port_name+" already in use \n"+e.getMessage());
            throw new Exception(e.getMessage());
        }
        catch(Exception e){
            System.out.println("Initializing: Unknown Error \n"+e.getMessage());
            throw new Exception(e.getMessage());
        }
        // get Input and Output Stream
        try{
            this.inputstream=commport.getInputStream();
            this.outputstream=commport.getOutputStream();
        }
        catch(Exception e){
            System.out.println("Open Input and Output Stream: Unknown Error \n"+e.getMessage());
            throw new Exception(e.getMessage());
        }
        this.data_from_port=new LinkedList();
        this.main_thread=new Thread(this);
        this.main_thread.start();
    }

    public boolean write_to_port(byte[] array_of_byte){
        try{
            this.outputstream.write(array_of_byte);
            this.outputstream.flush();
            return true;
        }
        catch(Exception e){
            System.out.println("Error in write to port");
            return false;
        }
    }
    
    public int data_from_port_aviable(){
        return this.data_from_port.size();
    }
    public byte[] get_data_from_port(){
        byte[] temp_byte=null;
        if(this.data_from_port_aviable()>0){
            temp_byte=new byte[this.data_from_port.size()];
            for(int i=0;i<temp_byte.length;i++){
                temp_byte[i]=(Byte)this.data_from_port.get(i);
            }
            this.data_from_port.clear();
        }
        return temp_byte;
    }
    private void read_data_from_port(){
        try{
            byte[] byte_from_port=new byte[this.inputstream.available()];
            inputstream.read(byte_from_port);
            for(int i=0;i<byte_from_port.length;i++){
                // push to LinkedList
                this.data_from_port.push(new Byte(byte_from_port[i]));
                // clear LinkedList if need
                if(this.buffer_size<this.data_from_port.size()){
                    this.data_from_port.clear();
                }
                // setText in JTextArea
                this.outer_component.write_to_JTextArea(i,byte_from_port[i]);
            }
        }
        catch(Exception e){
            System.out.println("error in read data from port\n"+e.getMessage());
        }
    }
    public void close_port(){
        try{
            if(this.inputstream!=null){
                inputstream.close();
            }
        }
        catch(Exception e){
            System.out.println("Error in close "+e.getMessage());
        }
        try{
            if(this.outputstream!=null){
                outputstream.close();
            }
        }
        catch(Exception e){
            System.out.println("Error in close "+e.getMessage());
        }

        try{
            if(this.commport!=null){
                this.commport.close();
            }
        }
        catch(Exception e){
            System.out.println("Error in close "+e.getMessage());
        }
       
    }
    public void finalize(){
        try{
            if(commport!=null){
                commport.close();
            }
        }
        catch(Exception e){
            System.out.println("error close port:"+this.port_name);
        }
    }
    public void run(){
        while(true){
            try{
                if(inputstream.available()>0){
                    this.read_data_from_port();
                }
            }
            catch(Exception e){
                System.out.println(" Error in get Available data from port");
            }
        }
    }
}
