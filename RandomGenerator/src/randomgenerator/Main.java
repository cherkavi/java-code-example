/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package randomgenerator;

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
        for(int counter=0;counter<1000;counter++){
            System.out.println(Generator.getUniqueChar(5));
        }
    }

}
