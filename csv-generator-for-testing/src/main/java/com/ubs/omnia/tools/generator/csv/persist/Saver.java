package com.ubs.omnia.tools.generator.csv.persist;

import java.io.IOException;

import com.ubs.omnia.tools.generator.csv.domain.Line;

public interface Saver {
	/** 
	 * before using - must be executed 
	 */
	public void init() throws Exception;
	
	/**
	 * add one line to persist 
	 * @param line
	 */
	public void add(Line line) throws IOException;
	
	/**
	 * after add all lines - must be executed
	 */
	public void destroy();
	
}
