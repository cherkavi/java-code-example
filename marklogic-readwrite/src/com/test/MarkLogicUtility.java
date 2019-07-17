package com.test;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.marklogic.xcc.Content;
import com.marklogic.xcc.ContentCreateOptions;
import com.marklogic.xcc.ContentFactory;
import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.Session.TransactionMode;
import com.marklogic.xcc.exceptions.RequestException;

public class MarkLogicUtility {

	
	public static ContentSource getContentSource(String host, 
											     int port, 
											     String login, 
											     String password) {
		// Sometimes need to define the Database Name: String database="test";
		
		// Example of connection String: URI uri = new URI("xcc://user:password@host:port/contentbase");
		// String connectionString="xcc://"+login+":"+password+"@"+host+":"+port+"/"+database;
		// ContentSource contentSource = ContentSourceFactory.newContentSource(new URI(connectionString));
		return ContentSourceFactory.newContentSource(host, port, login, password);
	}
	
	
	public static boolean saveXml(ContentSource contentSource, String pathToXmlFile){
		Session session=contentSource.newSession();
		try{
			Content content=ContentFactory.newContent(getUriFromPath(pathToXmlFile), new File(pathToXmlFile), ContentCreateOptions.newTextInstance());
			session.setTransactionMode(TransactionMode.UPDATE);
			session.insertContent(content);
			session.commit();
			ResultSequence result=MarkLogicUtility.readValues(contentSource, "doc()/root2");
			System.out.println("Result:"+result);
			return true;
		}catch (RequestException e) {
			System.err.println("RequestException:"+e.getMessage());
			return false;
		}finally{
			try{
				session.close();
			}catch(Exception ex){};
		}
	}
	
	static String getUriFromPath(String pathToXmlFile) {
		if(!pathToXmlFile.startsWith("/")){
			pathToXmlFile="/"+pathToXmlFile;
		}
		pathToXmlFile=pathToXmlFile.replace('\\', '/');
		pathToXmlFile=pathToXmlFile.replaceAll("[:]", "");
		return pathToXmlFile;
	}

	/**
	 * 
	 * @param contentSource
	 * @param xquery  (Example "/root/*[starts-with((./..),'property_1')]")
	 * @return
	 * <ul>
	 * 	<li><b>null</b> - exception was happen </li>
	 * 	<li><b>{@link ResultSequence}</b> </li>
	 * </ul>
	 */
	public static ResultSequence readValues(ContentSource contentSource, 
											String xquery){
		Session session=contentSource.newSession();
		ResultSequence resultSequence = null;
	    try{
	    	resultSequence=session.submitRequest(session.newAdhocQuery(xquery));
	    }catch(RequestException re){
	    	System.err.println("Exception was happen during execute the query: "+re.getMessage());
	    	return null;
	    }finally{
	    	try{
	    		session.close();
	    	}catch(Exception ex){};
	    }
	    return resultSequence;
	}
	
}
