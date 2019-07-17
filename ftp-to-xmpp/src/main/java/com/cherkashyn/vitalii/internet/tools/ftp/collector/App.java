package com.cherkashyn.vitalii.internet.tools.ftp.collector;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cherkashyn.vitalii.internet.tools.ftp.collector.display.Display;
import com.cherkashyn.vitalii.internet.tools.ftp.collector.domain.DisplayElement;
import com.cherkashyn.vitalii.internet.tools.ftp.collector.storage.FtpDirectories;
import com.cherkashyn.vitalii.internet.tools.ftp.collector.storage.RemoteStorage;

/**
 * <ul>
 * 	<li>scan FTP remote folders,</li>
 * 	<li>retrieve all TXT files ( remove them after processing ),</li>
 * 	<li>send text as message to XMPP protocol</li>
 * </ul>
 */
public class App 
{
    private final static String APPLICATION_CONTEXT_FILE="context.xml";
	
    public static void main( String[] args ) throws Exception
    {
    	ApplicationContext context=getApplicationContext(args);
    	
    	RemoteStorage ftpServer=context.getBean(RemoteStorage.class);
    	Map<String, Display> displays=context.getBeansOfType(Display.class);
    	FtpDirectories folders=context.getBean(FtpDirectories.class);
    	
    	if(ftpServer.connect()){
    		for(String eachDirectory: folders.getFolders()){
    			List<String> orderFiles=ftpServer.listFiles(eachDirectory, PhpUtils.ORDER_FILE_PATTERN);
    			for(String eachFile:orderFiles){
    				String fileContent=ftpServer.readFileContent(eachDirectory, eachFile);
    				DisplayElement element=PhpUtils.convert(eachDirectory, eachFile, fileContent);
    				System.out.println("element: "+element.toString());
    				for(Entry<String, Display> eachDisplayEntry: displays.entrySet()){
    					eachDisplayEntry.getValue().show(element);
    				}
    				ftpServer.removeFile(eachDirectory, eachFile);
    			}
    		}
    	}else{
    		System.err.println("login/password Error ");
    	}
    	ftpServer.disconnect();
    }

    
	private static ApplicationContext getApplicationContext(String[] args) {
		String path=null;
		if(args.length==0){
			path=APPLICATION_CONTEXT_FILE;
		}else{
			path=args[0];
		}
		return new FileSystemXmlApplicationContext(path);
	}
	
    
}
