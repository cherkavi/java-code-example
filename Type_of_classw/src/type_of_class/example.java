/*
 * example.java
 *
 * Created on 23 липня 2008, 7:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package type_of_class;

import java.util.ArrayList;

/**
 * Outer class
 */
public class example {
    
    
    public example() {
        System.out.println("outer class");
    }
    
    public void example_method_1(){
        /**
         * local class
         */
        class local_class{
            public local_class(){
                System.out.println("local_class");
            }
        }
        new local_class();
    }
    
    public void example_method_2(){
        /**
         * Anonymous class
         */
        example_interface temp_interface=new example_interface(){
            public void write(){
                System.out.println(" realization of interface");
            }
        };
    }
    
    /**
     * Member class
     */
    public class member_class{
        public member_class(){
            System.out.println("member class");
        }
    }
}

/**
 * Static class
 */
class static_example{
    public static_example(){
        System.out.println("static_example");
    }
}
