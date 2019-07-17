/*
 * TT.java
 *
 * Created on 26 Октябрь 2007 г., 17:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * Настройка типа аналогового входа
 */
public class TT {
    /**
     * значение по умолчанию
     */
    public static String getDefaultKod(){
        return "00";
    }
    /**
     * Проверка кода на валидность
     */
    public static boolean isTTkod(int value){
        boolean result=false;
        if((value>=0)&&(value<=0x19)){
            result=true;
        }
        return result;
    }
    /**
     * Проверка кода на валидность
     */
    public static boolean isTTkod(String hex_string){
        boolean result=false;
        if(utility.isHEX_byte(hex_string)){
            int value=Integer.parseInt(hex_string,16);
            switch(value){
                case 0x00:result=true;break;
                case 0x01:result=true;break;
                case 0x02:result=true;break;
                case 0x03:result=true;break;
                case 0x04:result=true;break;
                case 0x05:result=true;break;
                case 0x06:result=true;break;
                case 0x07:result=true;break;
                case 0x08:result=true;break;
                case 0x09:result=true;break;
                case 0x0a:result=true;break;
                case 0x0b:result=true;break;
                case 0x0c:result=true;break;
                case 0x0d:result=true;break;
                case 0x0e:result=true;break;
                case 0x0f:result=true;break;
                case 0x10:result=true;break;
                case 0x11:result=true;break;
                case 0x12:result=true;break;
                case 0x13:result=true;break;
                case 0x14:result=true;break;
                case 0x15:result=true;break;
                case 0x16:result=true;break;
                case 0x17:result=true;break;
                case 0x18:result=true;break;
                case 0x19:result=true;break;
                default:result=false;break;
            }
        }
        else{
            // не удалось преобразовать строку в число
        }
        return result;
    }
}
