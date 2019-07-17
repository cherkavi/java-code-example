/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package constructorautocreate;

/**
 *
 * @author cherkashinv
 */
public class Child extends Parent{
    private String field_string="";
    public Child(){
        // автоматически вызывается конструктор без параметров 
        System.out.println("Child constructor:begin");
        System.out.println("Child constructor:end");
    }

    public Child(String value){
        // автоматически вызывается конструктор без параметров 
        System.out.println("Child constructor:begin");
        this.field_string=value;
        System.out.println("Child constructor:end");
    }
}
