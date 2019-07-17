package com.cherkashyn.vitalii.tools.database.diff.gui;

public class ButtonModel {
	private String command;
	private String title;
	public String getCommand() {
		return command;
	}
	public String getTitle() {
		return title;
	}
	public ButtonModel(String command, String title) {
		super();
		this.command = command;
		this.title = title;
	}
	public ButtonModel(Object key, Object value) {
		this.command=(value==null)?"":value.toString();
		this.title=(key==null)?"":key.toString();
	}
	
}
