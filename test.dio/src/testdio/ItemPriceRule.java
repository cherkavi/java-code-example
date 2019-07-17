/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testdio;

import java.util.ArrayList;
import java.util.Collections;
import org.apache.log4j.Logger;
/**
 * Rule for price of item
 */
public class ItemPriceRule implements Comparable{
    private int field_quantity;
    private float field_price;
    public ItemPriceRule(int quantity, float price){
        this.field_quantity=quantity;
        this.field_price=price;
    }
    
    public int getQuantity(){
        return field_quantity;
    }
   
    public float getPrice(){
        return field_price;
    }

    public void setQuantity(int value){
        this.field_quantity=value;
    }
    public void setPrice(float value){
        this.field_price=value;
    }
    
    public int compareTo(Object destination) {
        if(destination instanceof ItemPriceRule){
            int destination_value=((ItemPriceRule)destination).field_quantity;
            if(destination_value>this.field_quantity){
                return -1;
            }else{
                if(destination_value==this.field_quantity){
                    return 0;
                }else{
                    return (1);
                }
            }
        }else{
            return 0;
        }
    }
    
    
    /**
     * @param source string from scan
     * @param name name of element
     * @param price price of element
     * @param rule for element "name"
     * @return amount for string "source"
     */
    public static float getAmountFromRules(String source, 
                                           String name, 
                                           float price, 
                                           ArrayList<ItemPriceRule> rule){
        Logger field_logger=Logger.getLogger("ItemPriceRule.getAmountFromRules");
        float return_value=0;
        field_logger.debug("===begin===name="+name+"   source="+source);
        if((name==null)||(!name.trim().equals(""))){
            if(source.indexOf(name)>=0){
                int quantity=getElementsCountFromString(source, name);
                field_logger.debug("source:"+source+"    find_name:"+name+"   quantity:"+quantity);
                return_value=get_amount(rule,price,quantity);
            }else{
                field_logger.debug("name <"+name+"> not found into "+source);
            }
        }else{
            field_logger.warn("Name is null");
        }
        field_logger.debug("=== end===");
        return return_value;
    }
    
    /**
     * @param rule rules, if exists
     * @param price price element 
     * @param quantity quantity of element into string
     * @return amount 
     */
    public static float get_amount(ArrayList<ItemPriceRule> rule,
                                   float price, 
                                   int quantity){
        Logger logger=Logger.getLogger("get_amount");
        float return_value=0;
        if((rule==null)||(rule.size()==0)||(price==0)){
            logger.debug("without rule:");
            return_value=quantity*price;
        }else{
            logger.debug("rule count:"+rule.size());
            logger.debug("sorted");
            Collections.sort(rule);
            int rule_counter=rule.size()-1;
            int rule_quantity=0;
            int rule_mult=0;
            while(rule_counter>=0){
                rule_quantity=rule.get(rule_counter).field_quantity;
                if(rule_quantity<=quantity){
                    logger.debug("rule counter:"+rule_counter);
                    rule_mult=(int) quantity/rule_quantity;
                    logger.debug("rule_mult:"+rule_mult+" quantity before:"+quantity+" rule_quantity"+rule_quantity);
                    return_value+=rule_mult*rule.get(rule_counter).field_price;
                    quantity-=rule_mult*rule.get(rule_counter).field_quantity;
                    logger.debug(" quantity after:"+quantity);
                }else{
                    logger.debug("find quantity:"+rule_quantity+"   exists quantity:"+quantity);
                }
                rule_counter--;
            }
            return_value+=quantity*price;
        }
        return return_value;
    }
    /**
     * this is element
     * @param source string wich included element
     * @param element for find into string
     * @return count of element into source
     */
    private static int getElementsCountFromString(String source, 
                                                      String element){
        int return_value=0;
        int counter=0;
        int position=0;
        while(counter<source.length()){
            position=source.indexOf(element,counter);
            if(position>=0){
                return_value++;
                counter=position+element.length();
            }else{
                break;
            }
        }
        return return_value;
    }
    

}
