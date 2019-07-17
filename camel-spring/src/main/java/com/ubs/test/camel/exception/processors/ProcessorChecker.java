package com.ubs.test.camel.exception.processors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileMessage;
import org.apache.commons.io.IOUtils;

public class ProcessorChecker implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("Exchange: "+exchange);
		File file=getFileFromExchange(exchange);
		checkFile(file);
	}

	private void checkFile(File file) throws IOException {
		InputStream input=new FileInputStream(file);
		try{
			@SuppressWarnings("unchecked")
			List<String> lines=IOUtils.readLines(new FileInputStream(file));
			String fullFileText=joiner(lines);
			conditioner(fullFileText);
		}finally{
			IOUtils.closeQuietly(input);
		}
	}
	
	private void conditioner(String fileContext) throws IOException{
		if(fileContext.contains("exception")){
			throw new IOException("exception text was found ");
		}
	}
	
	private String joiner(List<String> lines){
		StringBuilder returnValue=new StringBuilder(); 
		for(String eachLine:lines){
			returnValue.append(eachLine);
		}
		return returnValue.toString();
	}

	private File getFileFromExchange(Exchange exchange) {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		GenericFileMessage<GenericFile> fileMessage=(GenericFileMessage<GenericFile>)exchange.getIn();
		@SuppressWarnings("rawtypes")
		GenericFile file=(GenericFile) fileMessage.getBody();
		return new File(file.getAbsoluteFilePath());
	}

}
