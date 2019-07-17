package com.cherkashyn.vitalii.barrette.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.cherkashyn.vitalii.barrette.service.exception.TransferServiceException;

public class Ftp2LocalService {

	/** separator between folders */
	private final static String FILE_SEPARATOR=File.separator;

	/**
	 * local directory for store all files from remote ( FTP ) drive 
	 */
	private String localDirectory; 
	
	/**
	 * current file which pointed to file into local folder
	 */
	private String currentFileName=null;
	
	/**
	 * FTP client aware 
	 */
	private final FtpClientAware ftpClientAware;

	/**
	 * service for retrieve files from remote FTP and copy it to local directory 
	 * @param ftpServer
	 * @param ftpPort
	 * @param user
	 * @param password
	 * @param localDirectoryPath
	 * @param clearLocalDirectory
	 */
	public Ftp2LocalService(String ftpServer, int ftpPort, String user, String password, String localDirectoryPath, boolean clearLocalDirectory){
		ftpClientAware=new ReConnectionFtpClientFactory(new FTPConnectionConfiguration(ftpServer, ftpPort, user, password));
		File fileLocalDirectory=new File(localDirectoryPath);
		if(!fileLocalDirectory.isDirectory()){
			throw new IllegalArgumentException("check directory: "+fileLocalDirectory);
		}
		this.localDirectory=fileLocalDirectory.getAbsolutePath();
		if(!this.localDirectory.endsWith(FILE_SEPARATOR)){
			this.localDirectory=this.localDirectory+FILE_SEPARATOR;
		}
		// clear local directory 
		if(clearLocalDirectory){
			clearLocalDirectory();
		}
	}
	
	/**
	 * remove all files from directory 
	 */
	private void clearLocalDirectory() {
		File localDirectory=new File(this.localDirectory);
		File[] files=localDirectory.listFiles();
		if(files==null){
			return;
		}
		for(File eachFile : files){
			if(eachFile.isFile()){
				eachFile.delete();
			}
		}
	}

	/** read previous file, if it exists from temporary folder */
	public File readPrevious() {
		if(this.currentFileName==null){
			return null;
		}
		// read list of files from current directory
		String[] files=getFileList();
		// get position of current file;
		int position=Arrays.binarySearch(files, this.currentFileName);
		// check possibility for move to previous 
		if(position<0){
			return null;
		}
		// move to previous position
		if(position>0){
			this.currentFileName=files[position-1];
		}
		return new File(this.localDirectory+this.currentFileName);
	}

	/** move file from FTP folder to locale temporary folder and return it 
	 * @throws TransferServiceException 
	 */
	public File readNext() throws TransferServiceException {
		copyAllNewFilesFromFTP(true);
		
		// read list of files from current directory
		String[] files=getFileList();
		if(this.currentFileName==null){
			if(files.length==0){
				return null;
			}
			this.currentFileName=files[0];
			return new File(this.localDirectory+this.currentFileName); 
		}
		// get position of current file;
		int position=Arrays.binarySearch(files, this.currentFileName);
		// check possibility for move to previous 
		if(position<0){
			this.currentFileName=files[0];
			return new File(this.localDirectory+this.currentFileName);
		}
		if(position<(files.length-1)){
			// move to next position
			this.currentFileName=files[position+1];
		}
		return new File(this.localDirectory+this.currentFileName);
	}

	/**
	 * remove last read file from temporary folder 
	 * @return
	 */
	public boolean removeCurrent() {
		if(this.currentFileName==null){
			return false;
		}
		
		File file=new File(this.localDirectory+this.currentFileName);
		if(!file.exists()){
			return false;
		}
		
		return file.delete();
	}

	private String[] getFileList() {
		String[] files=new File(this.localDirectory).list();
		if(files==null){
			return new String[0];
		}
		Arrays.sort(files, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		});
		return files;
	}

	
	private void copyAllNewFilesFromFTP(boolean deleteAfterCopy) throws TransferServiceException{
		// read files 
		FTPClient client=ftpClientAware.getFtpClient();
		try{
			FTPFile[] files=null;
			try {
				files=client.listFiles();
			} catch (IOException e) {
				throw new TransferServiceException(e); 
			}
			
			// copy one-by-one
			for(FTPFile eachFile:files){
				if(!eachFile.isFile()){
					continue;
				}
				copy(client, eachFile, generateNewFileName(eachFile));
				if(deleteAfterCopy){
					try {
						client.deleteFile(eachFile.getName());
					} catch (IOException e) {
						throw new TransferServiceException("can't remove remote file: "+eachFile.getName(), e);
					}
				}
			}
		}finally{
			// disconnect
			if(client!=null && client.isConnected()){
				try {
					client.logout();
				} catch (IOException e) {
				}
				try {
					client.disconnect();
				} catch (IOException e) {
				}
			}
		}
		
	}

	private final String FILE_EXT_SEPARATOR=".";
	
	/**
	 * copy from remote file to local file 
	 * @param client
	 * @param eachFile
	 * @param generatedFileName
	 */
	private void copy(FTPClient client, FTPFile eachFile, String generatedFileName) {
		FileOutputStream localFile=null;
		try{
			client.setFileType(FTP.BINARY_FILE_TYPE);
			localFile=new FileOutputStream(new File(this.localDirectory+generatedFileName));
			try{
				IOUtils.copy(client.retrieveFileStream(eachFile.getName()), localFile);
			}catch(NullPointerException npe){
				throw new IOException("try again");
			}
			// client.retrieveFile(eachFile.getName(), localFile);
			localFile.flush();
		}catch(IOException ex){
			if(localFile!=null){
				try {
					localFile.close();
				} catch (IOException e) {
				}
			}
		}
	}


	private final static SimpleDateFormat FILE_PATTERN=new SimpleDateFormat("YYYY_MM_dd_HH_mm_ss_");
	/**
	 * generate new filename
	 * @param file
	 * @return
	 */
	private String generateNewFileName(FTPFile file){
		return FILE_PATTERN.format(new Date())+randomStamp()+extension(file);
	}

	/**
	 * retrieve extensions from file 
	 * @param file
	 * @return
	 */
	private String extension(FTPFile file) {
		return FILE_EXT_SEPARATOR+StringUtils.substringAfterLast(file.getName(), FILE_EXT_SEPARATOR); 
	}

	private final static Random RANDOM=new Random();
	private final static int MAX_INT=999;
	
	private String randomStamp() {
		return Integer.toString(RANDOM.nextInt(MAX_INT));
	}
	
}


/**
 * FTP configuration holder 
 */
class FTPConnectionConfiguration{
	String ftpServer; int ftpPort; String user; String password;

	public FTPConnectionConfiguration(String ftpServer, int ftpPort, String user, String password) {
		super();
		this.ftpServer = ftpServer;
		this.ftpPort = ftpPort;
		this.user = user;
		this.password = password;
	}
	
}


/**
 * strategy template FTP connection 
 */
interface FtpClientAware{
	FTPClient getFtpClient() throws TransferServiceException;
}


/**
 * common realization for FTP connections
 */
abstract class FtpClientFactory implements FtpClientAware{
	private final FTPConnectionConfiguration configuration;
	private FTPClient client;
	
	public FtpClientFactory(FTPConnectionConfiguration ftpConfiguration) {
		this.configuration=ftpConfiguration;
	}
	
	@Override
	public abstract FTPClient getFtpClient() throws TransferServiceException;

	protected FTPClient connect() throws TransferServiceException{
		client=new FTPClient();
		try {
			client.connect(configuration.ftpServer, configuration.ftpPort);
			client.login(configuration.user, configuration.password);
		} catch (SocketException e) {
			throw new TransferServiceException("socket exception: " + e.getMessage());
		} catch (IOException e) {
			throw new TransferServiceException("remote server exception: " + e.getMessage());
		}
		return client;
	}
}

/**
 * one connection holder ( need to create sibling for FTPClient with reloaded method disconnect - nothing to do ) 
 */
class SingleConnectionFtpClientFactory extends FtpClientFactory{

	private FTPClient client;
	
	public SingleConnectionFtpClientFactory(
			FTPConnectionConfiguration ftpConfiguration) {
		super(ftpConfiguration);
	}

	@Override
	public FTPClient getFtpClient() throws TransferServiceException {
		if(this.client==null || !this.client.isConnected()){
			this.client=connect();
		}
		// method "disconnect" should be replaced  - nothing to do 
		return this.client;
	}
}


/**
 * every request will create new FTPClient 
 */
class ReConnectionFtpClientFactory extends FtpClientFactory{

	public ReConnectionFtpClientFactory(
			FTPConnectionConfiguration ftpConfiguration) {
		super(ftpConfiguration);
	}

	@Override
	public FTPClient getFtpClient() throws TransferServiceException {
		return connect();
	}
	
}


