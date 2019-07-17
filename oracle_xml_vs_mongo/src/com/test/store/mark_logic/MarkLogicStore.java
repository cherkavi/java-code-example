package com.test.store.mark_logic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultItem;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.test.store.IXmlSearcher;
import com.test.store.XmlStore;

public class MarkLogicStore implements XmlStore, IXmlSearcher{
	/** content of MarkLogic server */
	private ContentSource contentSource=null;
	
	/**
	 * MarkLogic Store:
	 * @param host - "127.0.0.1"
	 * @param port - 8000
	 * @param login - "root"
	 * @param password - "root"
	 * @throws URISyntaxException
	 * @throws RequestException 
	 */
	public MarkLogicStore(String host, 
						  int port, 
						  String login, 
						  String password) {
		// Sometimes need to define the Database Name: String database="test";

	    // Example of connection String: URI uri = new URI("xcc://user:password@host:port/contentbase");
		// String connectionString="xcc://"+login+":"+password+"@"+host+":"+port+"/"+database;
		// ContentSource contentSource = ContentSourceFactory.newContentSource(new URI(connectionString));
		this.contentSource = ContentSourceFactory.newContentSource(host, port, login, password);
	}
	
	@Override
	public void clearData() throws SQLException {
		// TODO need to implement
		throw new UnsupportedOperationException();
	}

	@Override
	public void printList(List<String> list) {
		if(list==null){
			System.out.println("list is Empty");
		}else{
			System.out.println("===== existing list ("+list.size()+")======");
			for(String value:list){
				System.out.println(value);
				System.out.println("---------------");
			}
			System.out.println("==================");
		}
	}

	
	@Override
	public List<String> readXml(Connection connection,
								String xpath, 
								String condition) throws SQLException {
	    Session session = contentSource.newSession();
	    // request = session.newAdhocQuery ("\"Hello World\"");
        // request = session.newAdhocQuery("for $a in doc()/root/property_1 return $a");
	    
	    // Request request =request=session.newAdhocQuery("/root/(property_1|property_2)");
	    // request=session.newAdhocQuery("/root/(property_1|property_2)/..");
	    // request=session.newAdhocQuery("/root");
	    // request=session.newAdhocQuery("doc()/root/*/text() [starts-with(lower-case(.), \"value_\")]");
	    // request=session.newAdhocQuery("doc()/root/*/text() [ends-with(lower-case(.), \"_3\")]");
	    // request=session.newAdhocQuery("doc()/root/property_1/text() [contains(lower-case(.), \"value\")]");
	    // request=session.newAdhocQuery("distinct-values(doc()/root/*/text() [contains(lower-case(.), \"value\")])");
	    // request=session.newAdhocQuery("doc()/root/*/text() [contains(lower-case(.), \"value\")]");
	    // request=session.newAdhocQuery("doc()/root[contains(lower-case(.), 'property')]");
	    // request=session.newAdhocQuery("/root/*[starts-with((./..),'property_1')]");
	    
	    Request request=session.newAdhocQuery("doc()"+xpath+"/text() [contains(lower-case(.), \""+condition.toLowerCase()+"\")]");
	    // Request request=session.newAdhocQuery("doc()"+xpath+"/text() [contains(., \""+condition+"\")]");
	    ResultSequence resultSequence = null;
	    try{
	    	resultSequence=session.submitRequest(request);
	    }catch(RequestException re){
	    	System.err.println("Exception during trying to get data RequestException: "+re.getMessage());
	    	throw new SQLException("RequestException:"+re.getMessage());
	    }
	    List<String> returnValue=new LinkedList<String>();
	    Iterator<ResultItem> resultItem=resultSequence.iterator();
	    while(resultItem.hasNext()){
	    	ResultItem nextItem=resultItem.next();
	    	returnValue.add(nextItem.asString());
	    }
	    // All done
	    session.close();
		return returnValue;
	}

	@Override
	public long writeXml(String xmlBody) throws SQLException, IOException {
		// TODO need to implement
		throw new UnsupportedOperationException();
	}


}
