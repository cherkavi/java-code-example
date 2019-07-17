/*
 * AAF.java
 *
 * Created on 27 Октябрь 2007 г., 10:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * Чтение номера версии исполнения модуля
 */
public class AAF {
    private String value_AA;
    /**
     * инициализация без параметров
     * AA - адрес модуля, передавшего сообщение
     */
    public AAF(){
        this.value_AA="00";
    }
    public AAF(String _AA) throws Exception{
        if(utility.isHEX_byte(_AA)){
           this.value_AA=_AA;
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
     * команда для отправки в порт
     */
    public String get_command(){
        return "$"+this.get_AA()+"F";
    }
    /**
      * Проверить валидность ответа
      */
    public boolean response_is_valid(String response){
        boolean result=false;
        if(response.length()>=3){
            if(response.charAt(0)=='!'){
                result=true;
            }
        }
        else{
            // нехватка символов в ответе - или модуль не ответил - ошибка передачи
        }
        return result;
    }
    
}
