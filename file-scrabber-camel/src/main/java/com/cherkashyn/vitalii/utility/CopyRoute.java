package com.cherkashyn.vitalii.utility;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileMessage;
import org.apache.camel.impl.DefaultExchange;
import org.apache.log4j.Logger;

public class CopyRoute extends RouteBuilder {
	private static Logger LOGGER=Logger.getLogger(CopyRoute.class);
	
	private final String sourceFolder;
	private final String destinationFolder;

	public CopyRoute(String sourceFolder, String destinationFolder) {
		super();
		this.sourceFolder = sourceFolder;
		this.destinationFolder = destinationFolder;
	}
	
	@Override
	public void configure() throws Exception {
		from(makeReference(this.sourceFolder)).process(new Processor(){
			@Override
			public void process(Exchange value) throws Exception {
				if(!(value instanceof DefaultExchange)){
					return;
				}
				DefaultExchange exchange=(DefaultExchange)value;
				if(!(exchange.getIn() instanceof GenericFileMessage<?>)){
					return;
				}
				GenericFileMessage<?> fileExchange=(GenericFileMessage<?>)exchange.getIn();
				LOGGER.info(fileExchange.getGenericFile().getFileName()+" : "+fileExchange.getGenericFile().getFileLength());
			}
		}). to(makeReference(this.destinationFolder));
	}
	
	
	private static String makeReference(String rawFolder){
		return "file:"+rawFolder.trim()+"?delete=true";
	}

}
