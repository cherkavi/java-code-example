/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package launcher.settings.exceptions;

/**
 * exception for load settings 
 * @author vcherkashin
 */
public class ESettingsException extends Exception{
    
    public ESettingsException(String cause){
        super(cause);
    }

}
