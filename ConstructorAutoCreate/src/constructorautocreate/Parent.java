/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package constructorautocreate;

/**
 *
 * @author cherkashinv
 */
public class Parent {
    private String field_string="";
    
    // конструктор, который вызывается без параметров 
    public Parent(){
        System.out.println("Parent Constructor: begin");
        this.field_string="";
        System.out.println("Parent Constructor: end");
    }
    
    public Parent(String value){
        System.out.println("Parent Constructor: begin");
        this.field_string=value;
        System.out.println("Parent Constructor: end");
    }
}
