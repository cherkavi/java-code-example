/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package launcher.settings;

import java.util.List;
import launcher.settings.exceptions.ESettingsException;

/**
 *
 * @author vcherkashin
 */
public interface ISettingsService {
    
    /** get all Values from storage */
    public List<SettingsItem> getAllValues() throws ESettingsException;

    /** save all values to Storage  */
    public boolean saveAllValues(List<SettingsItem> list) throws ESettingsException;
    
}
