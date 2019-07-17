/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testdio;

import java.util.ArrayList;

/**
 * price of elements 
 */
public class ItemPrice{
    private String field_name;
    private float field_price;
    /** set of rule for item*/
    private ArrayList<ItemPriceRule> field_rule;
    /**
     * @param name item name
     * @param price item price
     */
    public ItemPrice(String name,float price){
        this.field_rule=new ArrayList();
        this.field_name=name;
        this.field_price=price;
    }
    
    public ItemPrice(String name, float price, ItemPriceRule rule){
        this.field_name=name;
        this.field_price=price;
    }
    /**
     * set price 
     * @param price
     */
    public void setPrice(float price){
        this.field_price=price;
    }
    /**
     * 
     * @return price
     */
    public float getPrice(){
        return this.field_price;
    }
    
public String getName(){
        return this.field_name;
    }
    /**
     * clear all rule for item
     */
    public void clear_rule(){
        this.field_rule.clear();
    }
    /**
     * replacing all rule's
     * @param rule
     */
    public void setRule( ItemPriceRule ... rule){
        this.clear_rule();
        for(int counter=0;counter<rule.length;counter++){
            this.field_rule.add(rule[counter]);
        }
    }
    public ArrayList<ItemPriceRule> getAllRules(){
        return this.field_rule;
    }
    /**
     * add rule to list
     * @param rule
     */
    public void addRule(ItemPriceRule rule){
        boolean return_value=true;
        int adding_quantity=rule.getQuantity();
        for(int counter=0;counter<this.field_rule.size();counter++){
            if(this.field_rule.get(counter).getQuantity()==adding_quantity){
                return_value=false;
                this.field_rule.get(counter).setQuantity(adding_quantity);
                break;
            }
        }
        if(return_value==true){
            this.field_rule.add(rule);
        }
    }

}
