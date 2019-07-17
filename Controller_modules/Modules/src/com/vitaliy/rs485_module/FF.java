/*
 * FF.java
 *
 * Created on 26 Октябрь 2007 г., 17:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * Настройка формата данных
 */
public class FF {
    private static int value=0;
    /**
     * Значение по умолчанию
     */
    public static String getDefaultKod(){
        return "00";
    }
    /**
     * очистка переменной
     */
    public static void clear(){
        FF.value=0;
    }
    /**
     * получить значение переменной
     */
    public static String getHexValue(){
        String result="";
        if(FF.value<16){
            result="0"+Integer.toHexString(FF.value);
        }
        else {
            result=Integer.toHexString(FF.value);
        }
        return result;
    }
    /**
     * получить значение переменной 
     */
    public static int getIntValue(){
        return FF.value;
    }
    /**
     * установить значение переменной
     */
     public static void setIntValue(String hex_string) throws Exception{
         if(utility.isHEX_byte(hex_string)){
             int value=Integer.parseInt(hex_string,16);
             FF.setIntValue(value);
         }
         else {
             throw new Exception("Error convert String to integer");
         }
     }
    /**
     * установить значение переменной
     */
    public static void setIntValue(int value) throws Exception{
        if((value>=0)&&(value<=255)){
            FF.value=value;
        }
        else{
            throw new Exception("Error convert String to integer");
        }
    }
    
    /**
     * получить значение подавления частоты 60Гц
     */
    public static boolean getSuppression60Hz(){
        boolean result=false;
        if((FF.value & (int)utility.pow(2,7))==0){
            result=true;
        }
        return result;
    }
    /**
     * получить значение подавления частоты 50Гц
     */
    public static boolean getSuppression50Hz(){
        boolean result=false;
        if((FF.value & (int)utility.pow(2,7))!=0){
            result=true;
        }
        return result;
    }
    /**
     * получить значение бита контроля суммы (true=разрешен, false=запрещен)
     */
    public static boolean getBitControlAmount(){
        boolean result=false;
        if((FF.value & (int)utility.pow(2,6))!=0){
            result=true;
        }
        return result;
    }
    /**
     * получить значение бита контроля суммы
     */
    public static int getBitFormatData(){
        int result=0;
        if((FF.value & (int)utility.pow(2,0))==1){
            result=result+1;
        }
        if((FF.value & (int)utility.pow(2,1))==1){
            result=result+2;
        }
        return result;
    }
    /**
     * установить значение подавления частоты 60Гц
     */
    public static void setSuppression60Hz(boolean value){
        if(value==true){
            // установка в "0" 7 бита
            if((FF.value&(int)utility.pow(2,7))==1){
                FF.value=FF.value-(int)utility.pow(2,7);
            }
        }
        else {
            // установка в "1" 7 бита
            FF.value=FF.value|(int)utility.pow(2,7);
        }
    }
    /**
     * установить значение подавления частоты 50Гц
     */
    public static void setSuppression50Hz(boolean value){
        if(value==true){
            FF.value=FF.value|(int)utility.pow(2,7);
        }
        else {
            // проверка на установленность
            if((FF.value&(int)utility.pow(2,7))!=0){
                FF.value=FF.value-(int)utility.pow(2,7);
            }
        }
        
    }
    /**
     * установить значение бита контроля суммы (true=разрешен, false=запрещен)
     */
    public static void setBitControlAmount(boolean value){
        if(value==true){
            FF.value=FF.value|(int)utility.pow(6,2);
        }
        else {
            // проверка на установленность
            if((FF.value&(int)utility.pow(6,2))!=0){
                FF.value=FF.value-(int)utility.pow(6,2);
            }
        }
    }
    /**
     * установить значение бита контроля суммы
     */
    public static void setBitFormatData(int value){
        FF.value=FF.value|value;
    }
    /**
     * Проверка на принадлежность к коду
     */
    public static boolean isFFkod(int value){
        boolean result=false;
        if((value>=0)&&(value<=255)){
            result=true;
        }
        return result;
    }
    /**
     * Проверка на принадлежность к коду
     */
     public static boolean isFFkod(String hex_string){
         boolean result=false;
         if(utility.isHEX_byte(hex_string)){
             result=FF.isFFkod(Integer.parseInt(hex_string,16));
         }
         else{
             // не удалось преобразовать к типу int
         }
         return result;
     }
}
