package com.example.steps;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

public class MySteps {
    @Given("table of text")
    public void partGive(ExamplesTable table){
    	System.out.println("Given");
    	for(int counter=0;counter<table.getRowCount();counter++){
    		System.out.println("- begin");
    		printMap(table.getRow(counter));
    		System.out.println("-  end");
    	}
    }
    
    private void printMap(Map<String, String> map){
    	Iterator<Entry<String, String>> iterator= map.entrySet().iterator();
    	while(iterator.hasNext()){
    		Entry<String, String> entry=iterator.next();
    		System.out.println("Key: "+entry.getKey()+ "Value: "+entry.getValue());    		
    	}
    }
    
    @When("table is recognized")
    public void partWhen(){
    	System.out.println("When");
    }
    
    @Then("print it")
    public void partThen(){
    	System.out.println("Then");
    }
}
