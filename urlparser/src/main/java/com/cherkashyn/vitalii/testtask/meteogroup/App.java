package com.cherkashyn.vitalii.testtask.meteogroup;

import java.util.List;

import com.cherkashyn.vitalii.testtask.meteogroup.domain.ResultElement;
import com.cherkashyn.vitalii.testtask.meteogroup.parser.impl.UrlParser;

/**
 * URLs parser, documentation: 
 * <ul>
 * 	<li>https://en.wikipedia.org/wiki/Uniform_Resource_Locator<li>
 * 	<li>https://danielmiessler.com/study/url-uri/<li>
 * 	<li>http://www.cuug.ab.ca/~branderr/csce/url.html<li>
 * </ul>
 * 
 *  <small>http://www.iana.org/assignments/uri-schemes/uri-schemes.xhtml </small>
 */
public class App {
	
    public static void main(String[] args){
    	InputArgument inputArgument=InputArgument.parse(args);
    	if(inputArgument==null){
    		System.err.println("input parameter should be specified");
    		System.exit(1);
    	}
    	
    	List<ResultElement<?>> elements=UrlParser.parse(inputArgument.getUrl());
    	for(ResultElement<?> eachElement:elements){
    		System.out.println(eachElement.getElement()+" : "+eachElement.getValue());
    	}
    	
    }
    
}

/**
 * input parameter 
 */
class InputArgument{
	String url;

	public InputArgument(String argument) {
		this.url=argument;
	}
	
	public static InputArgument parse(String[] args) {
		if(args.length<1){
			return null;
		}
		return new InputArgument(args[0]);
	}

	public String getUrl() {
		return url;
	}
	
}
