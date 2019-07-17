package com.cherkashyn.vitalii.parsers.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class UrlSaver {

	public static int saveContentToDisk(String url, String pathToFile) throws IOException {
		URL remoteUrl=null;
		InputStream is=null;
		FileOutputStream out=null;
		
		try{
			remoteUrl=new URL(url);
			is=remoteUrl.openStream();
			out=new FileOutputStream(pathToFile);
			int returnValue=IOUtils.copy(is, out);
			out.flush();
			return returnValue;
		}finally{
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(out);
		}
	}
}
