package com.cherkashyn.vitalii.internet.tools.ftp.collector.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

public class FtpServerClient implements RemoteStorage{
	
	FTPClient client=new FTPClient();
	private String host;
	private String login;
	private String password;
	
	
	public FtpServerClient(String host, String login,String password) {
		super();
		this.host = host;
		this.login = login;
		this.password = password;
	}

	public boolean connect() throws SocketException, UnknownHostException, IOException{
		client.connect(InetAddress.getByName(this.host));
		return client.login(this.login, this.password);
	}
	
	public void disconnect() throws IOException{
		if(this.client!=null && this.client.isConnected()){
			this.client.disconnect();
		}
	}

	public List<String> listFiles(final String directory, final String pattern) throws IOException{
		FTPFileFilter FILTER_TEXT_FILES=new FTPFileFilter() {
			@Override
			public boolean accept(FTPFile file) {
				return file.getName().matches(pattern);
			}
		};

		FTPFile[] files = client.listFiles(directory, FILTER_TEXT_FILES);
		List<String> returnValue=new ArrayList<String>(files.length);
		for(FTPFile eachFile:files){
			returnValue.add(eachFile.getName());
		}
		return returnValue;
	}
	
	public String readFileContent(String directory, String fileName) throws IOException{
    	InputStream input=null;
    	try{
    		input=client.retrieveFileStream(createFilePath(directory, fileName));
    		StringBuilder returnValue=new StringBuilder();
    		@SuppressWarnings("unchecked")
			List<String> lines=(List<String>)IOUtils.readLines(input);
    		for(String eachLine:lines){
    			if(returnValue.length()>0){
    				returnValue.append(StringUtils.CR);
    			}
    			returnValue.append(eachLine);
    		}
    		return returnValue.toString();
    	}catch(IOException ex){
    		return StringUtils.EMPTY;
    	}finally{
    		IOUtils.closeQuietly(input);
    		client.completePendingCommand();
    	}
	}
	
	private final static String DIR_DELIMITER="/";
	
    private static String createFilePath(String directory, String fileName){
    	String realDirectory=StringUtils.trim(directory);
    	if(!StringUtils.endsWith(realDirectory, DIR_DELIMITER)){
    		realDirectory+=DIR_DELIMITER;
    	}
    	return realDirectory+StringUtils.trim(fileName);
    }

	public void removeFile(String directory, String fileName) throws IOException {
		client.deleteFile(createFilePath(directory, fileName));
		// client.completePendingCommand();
	}
	
	
}
