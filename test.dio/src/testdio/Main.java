/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testdio;

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
        Logger log=Logger.getRootLogger();
        log.setLevel(Level.WARN);
        BasicConfigurator.configure();
        log.debug("begin");
        new JFrameMain();
        log.debug("end");
    }

}
