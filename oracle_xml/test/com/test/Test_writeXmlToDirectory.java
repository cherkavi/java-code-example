package com.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.test.xml_generator.generators.XmlGenerator;
import com.test.xml_generator.values_generator.impl.LetterSequenceGenerator;

import junit.framework.TestCase;


/**
 * test for write XML files to certain directory
 */
public class Test_writeXmlToDirectory extends TestCase{

	/**
	 * test for generate files into certain directory
	 */
	public void testMain(){
		System.out.println("begin");
		final String pathToOutputDirectory="c:\\temp2\\";
		int maxFileCount=20000;
		XmlGenerator generator=new XmlGenerator(10,  // <property_0..9
										"property_", // <property_
										new LetterSequenceGenerator(
																	(byte)3, // xxxx 
																	(char)65,// A
																	(char)75 // Z - 90
																	)
										);
		this.writeFilesToDirectory(generator, pathToOutputDirectory, maxFileCount);
		System.out.println("done");
	}
	
	/** 
	 * generate files and write it to output Directory 
	 * @param generator - values generator 
	 * @param path - path to directory for save files 
	 * @param count - count of files for save 
	 */
	private void writeFilesToDirectory(XmlGenerator generator, 
									   String path, 
									   int count){
		for(int index=0;index<count; index++){
			try{
				FileUtils.writeStringToFile(new File(path+getNameForXmlFile(index)), 
											generator.getNextXml());
			}catch(IOException ex){
				System.err.println("Write file to directory Exception:"+ex.getMessage());
			}
		}
	}
	
	private String getNameForXmlFile(int counter){
		return StringUtils.leftPad(Integer.toString(counter), 5, '0')+".xml";
	}
}
