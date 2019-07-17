/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ascii;

/**
 *
 * @author cherkashinv
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //showAscII();
        byte[] array=new byte[]{10,20,30,40,50,60};
    }

    public static void showAscII(){
        int limit_low=10;
        int limit_high=70;
        for(int counter=limit_low;counter<=limit_high;counter++){
            System.out.println(counter+"("+Integer.toHexString(counter)+")"+": "+(char)counter);
        }
        if('?'==0x3f){
            System.out.println("equals");
        }
        
    }
}
