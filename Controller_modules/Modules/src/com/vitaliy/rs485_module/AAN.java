/*
 * AAN.java
 *
 * Created on 27 Октябрь 2007 г., 10:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * Считать значение сигнала по каналу "N" аналогового входа
 * @author root
 */
public class AAN {
    private String value_AA;
    private String value_N;
    
    /**
     * Проверка на валидность числового значения канала, переданного в виде строки из одного симовола
     */
    private boolean is_valid_N(String _N){
        boolean result=false;
        if(_N.length()==1){
            int value=Integer.parseInt(_N);
            if((value>=0)&&(value<=7)){
                result=true;
            }
        }
        return result;
    }
    /**
     * Проверка на валидность числового значения канала, переданного в виде int
     */
    private boolean is_valid_N(int _N){
        boolean result=false;
        if((_N>=0)&&(_N<=7)){
                result=true;
        }
        return result;
    }

    /**
     * инициализация без параметров
     * AA - адрес модуля, передавшего сообщение
     */
    public AAN(){
        this.value_AA="00";
        this.value_N="0";
    }
    public AAN(String _AA,String _N) throws Exception{
        if(utility.isHEX_byte(_AA)
         &&this.is_valid_N(_N)){
           this.value_AA=_AA;
           this.value_N=_N;
        }
    }
    public AAN(String _AA,int _N) throws Exception{
        if(this.is_valid_N(_N)){
           this.value_AA=_AA;
           this.value_N=Integer.toString(_N);
        }
    }
    
    /**
     * установка в начальные значения всех данных
     */
    public void clear(){
        this.value_AA="00";
    }
    /**
     * Установка адреса опрашиваемого модуля
     */
    public boolean set_AA(String _AA){
        boolean result=false;
        if(utility.isHEX_byte(_AA)){
            this.value_AA=_AA;
            result=true;
        }
        return result;
    }
    /**
     * получение адреса опрашиваемого модуля
     */
    public String get_AA(){
        return this.value_AA;
    }
    /**
     * Установка статуса модуля
     */
    public boolean set_N(String _N){
        boolean result=false;
        if(this.is_valid_N(_N)){
            this.value_N=_N;
            result=true;
        }
        return result;
    }
    /**
     * Установка статуса модуля
     */
    public boolean set_N(int _N){
        boolean result=false;
        if(this.is_valid_N(_N)){
            this.value_N=Integer.toString(_N);
            result=true;
        }
        return result;
    }

    /**
     * получение статуса модуля
     */
    public String get_N(){
        return this.value_N;
    }

    /**
     * команда для отправки в порт
     */
    public String get_command(){
        return "#"+this.get_AA()+this.get_N();
    }
    /**
      * Проверить валидность ответа
      */
    public boolean response_is_valid(String response){
        boolean result=false;
        if(response.length()>=3){
            if(response.charAt(0)=='>'){
                result=true;
            }
        }
        else{
            // нехватка символов в ответе - или модуль не ответил - ошибка передачи
        }
        return result;
    }
    
}
