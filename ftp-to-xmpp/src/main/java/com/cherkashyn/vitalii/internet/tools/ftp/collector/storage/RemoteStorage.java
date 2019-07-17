package com.cherkashyn.vitalii.internet.tools.ftp.collector.storage;

import java.io.IOException;
import java.util.List;

public interface RemoteStorage {
	
	public boolean connect() throws Exception;
	public void disconnect() throws IOException;
	public List<String> listFiles(final String directory, final String pattern) throws IOException;
	public String readFileContent(String directory, String fileName) throws IOException;
	public void removeFile(String directory, String fileName) throws IOException;
	
}
