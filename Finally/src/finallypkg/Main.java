/*
 * Main.java
 *
 * Created on 17 липня 2008, 13:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package finallypkg;

/**
 *
 * @author Technik
 */
public class Main {
    public static int get_value(int value){
        try{
            System.out.println("try begin");
            if(value>0){
                throw new Exception("");
            }
            System.out.println("try end");
        }catch(Exception ex){
            System.out.println("   catch begin");
            return value;
        }finally{
            System.out.println("      finally begin");
            return value+1;
        }
    }
    public static int get_value_2(int value) throws Exception{
        try{
            System.out.println("try begin");
            if(value>0){
                System.out.println("throw Exception");
                throw new Exception("get_value_2 exception");
            }
            System.out.println("try end");
        }catch(Exception ex){
            System.out.println("   catch begin");
// данное исключение не ловится
            throw new Exception("   exception into finally");
            //return value;
        }finally{
            try{
                System.out.println("      finally try: begin");
                return value+1;
            }catch(Exception ex_finally){
                System.out.println("      finally catch:");
                return value+2;
            }
        }
        //return 0;
    }
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int value=5;
        System.out.println("main:value begin="+value);
        System.out.println("main:value end="+get_value(value));
        System.out.println("main:---");
        System.out.println("main:value begin="+value);
        try{
            System.out.println("main:value end="+get_value_2(value));
        }catch(Exception ex){
            System.out.println("main: Exception:"+ex.getMessage());
        }
        
    }
    
}
