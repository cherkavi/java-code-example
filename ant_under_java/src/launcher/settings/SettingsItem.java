/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package launcher.settings;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vcherkashin
 */
public class SettingsItem {

    public static void printList(List<SettingsItem> list) {
        for(int counter=0;counter<list.size();counter++){
            System.out.println(counter+"   "+list.get(counter));
        }
    }

    public static SettingsItem decodeFromPropertyString(String nextLine, int index) {
        if(nextLine==null)return null;
        int position=nextLine.indexOf("=");
        if(position>0){
            return new SettingsItem(position,
                                    nextLine.substring(0,position),
                                    nextLine.substring(position+1, nextLine.length())
                                    );
        }else{
            return null;
        }
    }

    private String name;
    private String value;
    int position=0;

    /**
     * This is one Settings Item
     * @param position - position of item
     * @param name - name of item
     * @param value - value of item
     */
    public SettingsItem(int position, String name, String value){
        this.position=position;
        this.name=name;
        this.value=value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }

    private void setPosition(int counter) {
        this.position=counter;
    }


    /**
     * @param list - list for find
     * @param name - name for find
     * @return -
     * <ul>
     *  <li>founded object by name</li>
     *  <li>null, if object not founded </li>
     * <.ul>
     */
    public static SettingsItem getSettingsItemByName(List<SettingsItem> list, String name){
        for(int counter=0;counter<list.size();counter++){
            if(list.get(counter).getName().equals(name)){
                return list.get(counter);
            }
        }
        return null;
    }

    public static List<SettingsItem> setPositionNumberInArrayList(ArrayList<SettingsItem> list) {
        if( (list==null)|| (list.isEmpty()) )return list;
        for(int counter=0;counter<list.size();counter++){
            list.get(counter).setPosition(counter);
        }
        return list;
    }


    @Override
    public String toString(){
        return String.format("Position: %d   Name: %s   Value: %s", this.position, this.name, this.value);
    }
}
