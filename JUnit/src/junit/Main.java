/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package junit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;



/**
 *
 * @author First
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Logger out=Logger.getRootLogger();
        // если закомментировать - сразу же будет обращение к файлу log4j.properties
        BasicConfigurator.configure();
        out.setLevel(Level.INFO);
        new JFrame_main();
    }
}
