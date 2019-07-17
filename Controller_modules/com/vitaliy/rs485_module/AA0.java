/*
 * AA0.java
 *
 * Created on 27 ќкт€брь 2007 г., 10:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * —читать статус модул€
 * @author root
 */
public class AA0 extends Command{
    private String value_AA;
    private String value_SS;
    
    /**
     * инициализаци€ без параметров
     * AA - адрес модул€, передавшего сообщение
     */
    public AA0(){
        this.value_AA="00";
    }
    public AA0(String _AA) throws Exception{
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
     * ”становка статуса модул€
     */
    public boolean set_SS(String _SS){
        boolean result=false;
        if(utility.isHEX_byte(_SS)){
            this.value_SS=_SS;
            result=true;
        }
        return result;
    }
    /**
     * получение статуса модул€
     */
    public String get_SS(){
        return this.value_SS;
    }

    /**
     * команда дл€ отправки в порт
     */
    public String get_command(){
        return "~"+this.get_AA()+"0";
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
	public String get_name() {
		// TODO Auto-generated method stub
		return "AA0";
	}
    
}
