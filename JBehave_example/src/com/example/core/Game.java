package com.example.core;

import org.apache.commons.lang.StringUtils;


public class Game {
	private String[][] field=null;
	
	public Game(int width, int height){
		field=new String[height][width];
		for(int x=0;x<height;x++){
			for(int y=0;y<width;y++){
				field[x][y]=".";
			}
		}
	}
	
	public void toggleCellAt(int column, int row) {
		try{
			this.field[row][column]="X";
		}catch(Exception ex){
		}
	}
	
	public String asString(){
		StringBuilder returnValue=new StringBuilder();
		for(int y=0;y<this.field.length;y++){
			returnValue.append(StringUtils.join(this.field[y]));
			returnValue.append("\n");
		}
		return returnValue.toString();
	}
}
