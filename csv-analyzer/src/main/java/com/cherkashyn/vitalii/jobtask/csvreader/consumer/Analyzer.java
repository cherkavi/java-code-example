package com.cherkashyn.vitalii.jobtask.csvreader.consumer;

import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Row;

/**
 * core data analyzer 
 */
public abstract class Analyzer {
	public abstract void read(Row row);
	public abstract String getResult();

	public static void walkThrough(Analyzer[] analyzers, Row row) {
		if(row==null){
			return;
		}
		for(Analyzer eachAnalyzer:analyzers){
			eachAnalyzer.read(row);
		}
	}
	
}
