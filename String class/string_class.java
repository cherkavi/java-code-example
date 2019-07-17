/*
 * Main.java
 *
 * Created on 1 червня 2007, 15:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package javaapplication11;

/**
 *
 * @author Техник
 */
class temp_box{
    private String s;
    temp_box(){
        this.s="temp_box";
    }
    temp_box(String s){
        this.s=s;
    }
    public String toString(){
        return this.s;
    }
    static void clear_array(char[] array_of_chars){
        for(int i=0;i<array_of_chars.length;i++){
            array_of_chars[i]=' ';
        }
    }
    static void print_bytes(byte[] array_of_bytes){
        for(int i=0;i<array_of_bytes.length;i++){
            System.out.print(i+" - "+array_of_bytes[i]+"; ");
        }
        System.out.println();
    }
}

public class Main {

    public Main() {
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("<<<Begin");
        // Конструктор
        char chars[]={'a','b','c','d','e','f','g','h'};
        String s=new String(chars);// toCharArray()
        System.out.println("конструктор:"+s);
        
        String s2=new String("temp string");
        System.out.println("конструктор:"+s2);
        
        String s3=new String(chars,3,chars.length-3);
        System.out.println("конструктор:"+s3);

        // Concat - объединить строку
        s3=s+s2;
        System.out.println(s3);
        // стандартный вызов метода toString()
        temp_box box=new temp_box("method toString of <object> of <class temp_box>");
        System.out.println("toString():"+box);
        // метод charAt()
        char ch="abc".charAt(1);
        System.out.println("charAt():"+ch);
        // очистка массива char[]
        temp_box.clear_array(chars);
        String s4=new String(chars);
        System.out.println("Clear_chars:"+s4);
        
        // метод getChars();
        char[] new_char={'1','2','3','4','5','6','7','8'};
        s2.getChars(1,3,new_char,0);
        String s5=new String(new_char);
        System.out.println("getChars():"+s5);
        char[] new_char_2=s5.toCharArray();
        System.out.println("toCharArray():"+(new String(new_char_2)));
        // метод getBytes();
        byte[] array_of_byte;
        array_of_byte=s5.getBytes();
        System.out.print("getBytes():");temp_box.print_bytes(array_of_byte);
        // метод equals();
        String s10=new String("hello");
        String s11=new String("hello");
        String s12=new String("HELLO");
        if(s10.equals(s11)){
            System.out.println(s10+" equals "+s11);
        }
        else {
            System.out.println(s10+" NOT equals "+s11);
        };
        if(s10.equals(s12)){
            System.out.println(s10+" equals "+s12);
        }
        else {
            System.out.println(s10+" NOT equals "+s12);
        };
        // метод equalsIgnoreCase();
        if(s10.equalsIgnoreCase(s12)){
            System.out.println(s10+" equalsIgnoreCase "+s12);
        }
        else {
            System.out.println(s10+" NOT equalsIgnoreCase "+s12);
        }
        // метода regionMatches - 
        if(s10.regionMatches(true,1,s12,1,2)){// <IgnoreCase>,<begin> find in source string, <source> find string, substr find string <begin> <end>
            System.out.println("Matches");
        }
        else {
            System.out.println(" Not matches ");
        }
        // метод startWith()
        if(s10.startsWith("he")){
            System.out.println(s10+" startsWith "+"he");
        }
        else {
            System.out.println(s10+" NOT startsWith "+"he");
        };
        // метода endWith()
        if(s10.endsWith("llo")){
            System.out.println(s10+" endsWith "+"llo");
        }
        else {
            System.out.println(s10+" NOT endsWith "+"llo");
        }
        // метод compareTo()
        System.out.println(s10+" compareTo() "+s11+"   "+(s10.compareTo(s11)));
        System.out.println(s10+" compareTo() "+s12+"   "+(s10.compareTo(s12)));
        // метод indexOf()
        System.out.println("indexOf():"+s10.indexOf("l"));
        System.out.println("indexOf():"+s10.indexOf("z"));
        // метод lastIndexOf()
        System.out.println("lastIndexOf():"+s10.lastIndexOf("l"));
        System.out.println("lastIndexOf():"+s10.lastIndexOf("z"));
        // метод substring()
        System.out.println("substring():"+s10.substring(2));
        System.out.println("substring():"+s10.substring(1,5));
        System.out.println("<<<End");
        // метод concat()
        System.out.println("concat():"+s10.concat(s12));
        // метод raplace()
        System.out.println("replace():"+s10.replace("l","L"));
        // метод trim()
        System.out.println("trim():"+ (new String("          trim string      ")).trim() );
        // static метод valueOf() - аналог вызова функции toString() для каждого объекта, или простого типа
        System.out.println("valueOf():"+String.valueOf(box));
        System.out.println("valueOf():"+String.valueOf(s10));
        // метод toLowerCase()
        System.out.println("toLowerCase():"+s12+"=>"+s12.toLowerCase());
        // метод toUpperCase()
        System.out.println("toUpperCase():"+s10+"=>"+s10.toUpperCase());
    }
    
}
