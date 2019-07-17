/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serializable_arrayofbyte;

import java.io.Serializable;

/**
 *
 * @author cherkashinv
 */
public class ObjectSerializable implements Serializable{
    private String field_string_value=null;
    public ObjectSerializable(String value){
        this.field_string_value=value;
    }
    
    @Override
    public String toString(){
        return field_string_value;
    }
}
