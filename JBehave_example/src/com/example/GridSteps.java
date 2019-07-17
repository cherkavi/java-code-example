package com.example;

import org.apache.commons.lang.StringUtils;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;

import com.example.core.Game;

public class GridSteps extends Steps { 
    
    private Game game;
 
    @Given("a $width by $height game")
    @Aliases(values={"a new game: $width by $height"})
    public void theGameIsRunning(int width, int height) {
        System.out.println(">>> Given");
    	game = new Game(width, height);
    }
     
    @When("I toggle the cell at ($column, $row)")
    @Alias("i toggle the cell at ($column, $row)")
    public void iToggleTheCellAt(int column, int row) {
    	System.out.println(">>> When");
        game.toggleCellAt(column, row);
    }
/*
    @Pending
    @When("I toggle the cell at ($column, $row)")
    @Alias("i toggle the cell at ($column, $row)")
    public void pending(int column, int row){
    	System.out.println(">>> Pending method");
    }
*/    
    
    @Then("the grid should look like $grid")
    @Aliases(values={"the grid should be $grid"})
    public void theGridShouldLookLike(String grid) {
    	System.out.println(">>> Then");
    	// System.out.println("Try to compare original:"+StringUtils.trim(game.asString()));
    	// System.out.println("                   With:"+StringUtils.trim(grid));
    	// System.out.println("Compare result:"+game.asString().trim().equals(StringUtils.trim(grid)));
    	System.out.println("CompareResult: "+ compareByBreakLine(game.asString(), grid) );
    	Assert.assertTrue(compareByBreakLine(game.asString(), grid) );
    }
    
    
    private boolean compareByBreakLine(String first, String second){
    	first=StringUtils.trim(first);
    	String[] firstArray=trimArray(StringUtils.split(first, "\n"));
    	second=StringUtils.trim(second);
    	String[] secondArray=trimArray(StringUtils.split(second, "\n"));
    	if(firstArray.length!=secondArray.length){
    		return false;
    	}
    	for(int index=0;index<firstArray.length;index++){
    		if(!firstArray[index].equals(secondArray[index])){
    			return false;
    		}
    	}
    	return true;
    }

	private String[] trimArray(String[] split) {
		if((split!=null)&&(split.length>0)){
			for(int index=0;index<split.length;index++){
				split[index]=StringUtils.trim(split[index]);
			}
		}
		return split;
	}
 
}