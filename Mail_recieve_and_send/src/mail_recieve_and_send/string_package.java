/*
 * string_package.java
 *
 * Created on 29 лютого 2008, 14:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package mail_recieve_and_send;

/**
 *
 * @author Technik
 */
public class string_package{
    private String value;
    string_package(String s){
        this.value=s;
    }
    String getValue(){
        return this.value;
    }
    void setValue(String value){
        this.value=value;
    }
}
