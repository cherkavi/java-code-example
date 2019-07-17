package com.cherkashyn.vitalii.tools.file.processor;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ParentIdentifierProcessor extends FolderProcessor{

	private final static String IDENTIFIER="{0}_{1}_{2}";
	private final static SimpleDateFormat DATE_ID=new SimpleDateFormat("yyyyMMdd_HHmmss");
	
	@Override
	public void process(File directory, Exchange exchange)
			throws ProcessException {
		exchange.setParentIdentifier(createParentIdentifier(directory.getName()));
	}

	private String createParentIdentifier(String name) {
		return MessageFormat.format(IDENTIFIER, DATE_ID.format(new Date()), name, Integer.toString(new Random().nextInt(1000)));
	}

}
