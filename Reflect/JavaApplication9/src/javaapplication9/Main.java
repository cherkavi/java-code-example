/*
 * Main.java
 *
 * Created on 7 березня 2008, 14:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package javaapplication9;

import java.lang.reflect.Method;
import javax.swing.JLabel;

/**
 *
 * @author Technik
 */
class example{
    private String field_temp_string;
    private String field_temp_string2;
    
    public example(String temp_string,String temp_string2){
        this.field_temp_string=temp_string;
        this.field_temp_string2=temp_string2;
    }
    public String get_temp_string(Integer index){
        String return_value=null;
        if(index.intValue()==1){
            return_value=this.field_temp_string;
        };
        if(index.intValue()==2){
            return_value=this.field_temp_string2;
        }
        return return_value;
    }
    /*public String get_temp_string(){
        return this.field_temp_string;
    }*/
}

public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    public static Object call_method_from_object(Object object, String method_name,Object[] arg){
        try{
            // создать Class объекта
            Class class_of_object=object.getClass();
            // создать Method объекта
            Method method=class_of_object.getMethod(method_name,new Class[]{});
            // вызвать метод объекта с необходимыми параметрами
            System.out.println("Длинна массива:"+arg.length);
            return method.invoke(object,arg);
        }catch(Exception e){
            System.out.println("error INVOKE:"+e.getMessage());
            return null;
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JLabel label=new JLabel("hello");
        System.out.println(
            call_method_from_object(
                label,
                "getText",
                new Object[]{}
            )
        );
        example temp_example=new example("hello_1","hello_2");
        Integer[] arg=new Integer[]{new Integer(1)};
        System.out.println(
            call_method_from_object(
                temp_example,
                "get_temp_string",
                arg
            )
        );
    }
    
}
