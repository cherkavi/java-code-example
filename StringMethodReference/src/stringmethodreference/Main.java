/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stringmethodreference;

/**
 * проверка на возможность изменения переданных в процедуру String значений
 * (проверка возможности передачи в функцию значений по ссылке, и ее изменение )
 */
public class Main {

    private static void tryChange(String original_value){
        System.out.println(">>>:"+original_value);
        original_value="temp value";
        System.out.println("|>|:"+original_value);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String value="this is original string";
        
        System.out.println("before:"+value);
        tryChange(value);
        System.out.println("after:"+value);
        
    }

}
