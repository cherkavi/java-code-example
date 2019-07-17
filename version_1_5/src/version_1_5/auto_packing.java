/*
 * auto_packing.java
 *
 * Created on 26 квітня 2008, 0:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package version_1_5;

/**
 * Автоупаковка - автораспаковка
 */
public class auto_packing {
    
    /** Creates a new instance of auto_packing */
    public auto_packing() {
        // автоупаковка boolean в Boolean
        Boolean b=true;
        // автоупаковка int в Integer
        Integer i=10;
        // автоупаковка double в Double
        Double x=1.5;
        // вывод на печать - автораспаковка Integer в int и Double в double
        System.out.println("Integer i="+i+" Double x="+x+" Boolean b="+b);
    }
    
}
