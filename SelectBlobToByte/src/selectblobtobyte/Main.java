/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package selectblobtobyte;

/**
 *
 * @author cherkashinv
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        bcAppletActionDBParameters parameter=new bcAppletActionDBParameters("CAE5584596644FC7FB70ABC02F3AD4D1");
        System.out.println("parameter count:"+parameter.getParameterCount());
        for(int counter=0;counter<parameter.getParameterCount();counter++){
            System.out.println("Key:"+parameter.getParameterKeyByIndex(counter)+"    Value:"+parameter.getParameterValueStringByIndex(counter));
        }
        
    }

}
