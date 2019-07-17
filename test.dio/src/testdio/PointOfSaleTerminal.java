/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testdio;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * point of sale emulator
 */
public class PointOfSaleTerminal {
    /** logger */
    private Logger field_logger=Logger.getLogger("PointOfSaleTerminal");
    /** price list*/
    HashMap<String,ItemPrice> field_price_list;
    /** string for search price item*/
    private StringBuffer field_scan_string;
    
    public PointOfSaleTerminal(){
        field_logger.debug("create price list ");
        this.field_price_list=new HashMap();
        this.field_scan_string=new StringBuffer();
        
        // adding price list info
        this.setPrice("A", 1.25f);
        this.addRule("A", 3, 3f);
        
        this.setPrice("B",4.25f);

        this.setPrice("C", 1.00f);
        this.addRule("C", 6, 5f);

        this.setPrice("D", 0.75f);
    }
    /**
     * create new price item or update price item
     * @param name
     * @param price
     */
    public void setPrice(String name, float price){
        if(this.field_price_list.containsKey(name)){
            field_logger.debug("change price");
            this.field_price_list.get(name).setPrice(price);
        }else{
            field_logger.debug("create new item");
            this.field_price_list.put(name, new ItemPrice(name,price));
        }
    }
    /**
     * add rule for price item
     * @param name
     * @param quantity
     * @param price
     * @return
     */
    public boolean addRule(String name,int quantity, float price){
        boolean return_value=false;
        if(this.field_price_list.containsKey(name)){
            this.field_price_list.get(name).addRule(new ItemPriceRule(quantity,price));
            return_value=true;
        }else{
            field_logger.warn("PriceItem not exist");
            return_value=false;
        }
        return return_value;
    }
    
    /** 
     * put scan string
     * @param source string for scan
     */
    public void scan(String source){
        this.field_scan_string.append(source);
    }
    
    public void scanReset(){
        this.field_scan_string=new StringBuffer();
    }
    
    public double calculateTotal(){
        return this.calculateTotal(this.field_scan_string.toString());
    }
    public float calculateTotal(String scan_string){
        float return_value=0;
        Set key_set=this.field_price_list.keySet();
        Iterator key_iterator=key_set.iterator();
        String current_key;
        while(key_iterator.hasNext()){
            try{
                current_key=(String)key_iterator.next();
                return_value+=ItemPriceRule.getAmountFromRules(scan_string, 
                                                               this.field_price_list.get(current_key).getName(),
                                                               this.field_price_list.get(current_key).getPrice(),
                                                               this.field_price_list.get(current_key).getAllRules());
            }catch(Exception ex){
                field_logger.error("key_iterator.next - error :");
            }
        }
        return return_value;
    }
}
