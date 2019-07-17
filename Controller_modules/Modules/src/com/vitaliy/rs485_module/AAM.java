/*
 * AAM.java
 *
 * Created on 27 ќкт€брь 2007 г., 10:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * „тение имени подмодул€
 * @author root
 */
public class AAM {
    private String value_AA;
    /**
     * инициализаци€ без параметров
     * AA - адрес модул€, передавшего сообщение
     */
    public AAM(){
        this.value_AA="00";
    }
    public AAM(String _AA) throws Exception{
        if(utility.isHEX_byte(_AA)){
           this.value_AA=_AA;
        }
    }
    /**
     * установка в начальные значени€ всех данных
     */
    public void clear(){
        this.value_AA="00";
    }
    /**
     * ”становка адреса опрашиваемого модул€
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
     * получение адреса опрашиваемого модул€
     */
    public String get_AA(){
        return this.value_AA;
    }
    /**
     * команда дл€ отправки в порт
     */
    public String get_command(){
        return "$"+this.get_AA()+"M";
    }
    /**
      * ѕроверить валидность ответа
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
