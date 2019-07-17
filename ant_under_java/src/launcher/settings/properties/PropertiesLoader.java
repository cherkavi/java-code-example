/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package launcher.settings.properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import launcher.settings.ISettingsService;
import launcher.settings.SettingsItem;
import launcher.settings.exceptions.ESettingsException;

/**
 *
 * @author vcherkashin
 */
public class PropertiesLoader implements ISettingsService{
    private String[] priority=null;
    private String pathToFile=null;

    /**
     * load settings from properties file
     * @param pathToFile - path to file
     * @param priority - list of priority (list of keys, wich must be ordered by )
     * @throws when file is not found
     */
    public PropertiesLoader(String pathToFile, String[] priority) throws ESettingsException {
        if(!isFileExists(pathToFile)){
            throw new ESettingsException ("file is not found: "+pathToFile);
        }
        this.pathToFile=pathToFile;
        this.priority=priority;
    }

    /**
     * load settings from properties file
     * @param pathToFile - path to file
     * @param priority - list of priority (list of keys, wich must be ordered by )
     * @throws when file is not found
     */
    public PropertiesLoader(String pathToFile) throws ESettingsException {
        this(pathToFile,null);
    }

    public List<SettingsItem> getAllValues() {
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(this.pathToFile)));
            ArrayList<SettingsItem> list=new ArrayList<SettingsItem>();
            String nextLine=null;
            int index=0;
            while( ((nextLine=reader.readLine())!=null) ){
                nextLine=nextLine.trim();
                if(nextLine.startsWith("#"))continue;
                SettingsItem value=SettingsItem.decodeFromPropertyString(nextLine, index);
                if(value!=null){
                    // System.out.println(value);
                    list.add(value);
                    index++;
                }
            }
            return sortList(list,this.priority);
        }catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
                Logger.getLogger(PropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
    }

    public boolean saveAllValues(List<SettingsItem> list) throws ESettingsException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** check file for Exists  */
    private boolean isFileExists(String pathToFile) {
        return (new File(pathToFile)).exists();
    }

    private List<SettingsItem> sortList(ArrayList<SettingsItem> list, String[] priority) {
        if( (priority==null) || (priority.length==0) ){
            return list;
        }else{
            ArrayList<SettingsItem> returnValue=new ArrayList<SettingsItem>();
            for(int counter=0;counter<priority.length;counter++){
                SettingsItem currentItem=SettingsItem.getSettingsItemByName(list, priority[counter]);
                if(currentItem!=null){
                    returnValue.add(currentItem);
                    list.remove(currentItem);
                }
            }
            for(int counter=0;counter<list.size();counter++){
                returnValue.add(list.get(counter));
            }
            return SettingsItem.setPositionNumberInArrayList(returnValue);
        }
    }


}
