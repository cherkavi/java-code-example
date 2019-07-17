package com.cherkashyn.vitalii.testtask.meteogroup.parser.impl;


class FileParser extends HttpParser{
	public final static String PROTOCOL_FILE="file";
	
	
	FileParser() {
		super(PROTOCOL_FILE);
	}

	protected String checkAndBiteNecessaryPreambula(String value){
		return value;
	}
	
}
