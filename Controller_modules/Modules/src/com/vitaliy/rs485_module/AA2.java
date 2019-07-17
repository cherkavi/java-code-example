/*
 * AA2.java
 *
 * Created on 26 Октябрь 2007 г., 19:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * Чтение параметров конфигурации
 */
public class AA2 {
    private String value_AA;
    private String value_TT;
    private String value_CC;
    private String value_FF;
    /**
     * инициализация без параметров
     * AA - адрес модуля, передавшего сообщение
     * TT - код типа аналогового входа модуля
     * CC - код скорости передачи модуля
     * FF - формат данных модуля
     */
    public AA2(){
        this.value_AA="00";
        this.value_TT="00";
        this.value_CC="00";
        this.value_FF="00";
    }
    public AA2(String _AA, String _TT, String _CC, String _FF) throws Exception{
        if(utility.isHEX_byte(_AA)
         &&TT.isTTkod(_TT)
         &&CC.isCCkod(_CC)
         &&FF.isFFkod(_FF)){
           this.value_AA=_AA;
           this.value_TT=_TT;
           this.value_CC=_CC;
           this.value_FF=_FF;
        }
    }
    /**
     * установка в начальные значения всех данных
     */
    public void clear(){
        this.value_AA="00";
        this.value_TT="00";
        this.value_CC="00";
        this.value_FF="00";
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
     * установка типа аналогового входа
     */
    public boolean set_TT(String _TT){
        boolean result=false;
        if(TT.isTTkod(_TT)){
            this.value_TT=_TT;
            result=true;
        }
        return result;
    }
    /**
     * получение типа аналогового входа
     */
    public String get_TT(){
        return this.value_TT;
    }
    /**
     * установка скорости передачи модуля
     */
    public boolean set_CC(String _CC){
        boolean result=false;
        if(CC.isCCkod(_CC)){
            this.value_CC=_CC;
            result=true;
        }
        return result;
    }
    /**
     * получение скорости передачи модуля
     */
    public String get_CC(){
        return this.value_CC;
    }
    /**
     * установка формата данных модуля
     */
    public boolean set_FF(String _FF){
        boolean result=false;
        if(FF.isFFkod(_FF)){
            this.value_FF=_FF;
        }
        return result;
    }
    /**
     * получение формата данных модуля
     */
    public String get_FF(){
        return this.value_FF;
    }
    /**
     * команда для отправки в порт
     */
    public String get_command(){
        return "$"+this.get_AA()+"2";
    }
    /**
      * Проверить валидность ответа
      */
    public boolean response_is_valid(String response){
        boolean result=false;
        if(response.length()>=9){
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
