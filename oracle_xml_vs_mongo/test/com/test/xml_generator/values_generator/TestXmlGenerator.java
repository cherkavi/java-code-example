package com.test.xml_generator.values_generator;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.test.xml_generator.IXmlGenerator;
import com.test.xml_generator.generators.XmlGenerator;
import com.test.xml_generator.values_generator.impl.LetterSequenceGenerator;

public class TestXmlGenerator {
	public static void main(String[] args){
		/** path to root with xml  */
		String pathToFiles="c:\\temp2\\";
		
		/** count of files */
		int filesCount=10;
		
		IXmlGenerator generator=new XmlGenerator(10, 
												"property_",
												new LetterSequenceGenerator(
																			(byte)4, 
																			(char)65, 
																			(char)90 
																			)
												);
		
		for(int fileIndex=0;fileIndex<filesCount;fileIndex++){
			try{
				FileUtils.writeStringToFile(new File(pathToFiles+fileIndex+".xml"), generator.getNextXml());
			}catch(IOException ex){
				System.err.println("Exception during save to File:"+ex.getMessage());
			}
		}
		System.out.println("OK");
	}

}
