package com.test.store.mark_logic;

import java.net.URI;
import java.net.URISyntaxException;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.exceptions.XccConfigException;


public class _01_Connection {
	
	public static void main(String[] args) throws RequestException, URISyntaxException, XccConfigException{
		String login="root";
		String password="root";
		String host="127.0.0.1";
		int port=8010;
		String database="test";
	    // Create a URI object from the supplied argument
	    // URI uri = new URI("xcc://user:password@host:port/contentbase");
		String connectionString="xcc://"+login+":"+password+"@"+host+":"+port+"/"+database;
		System.out.println("ConnectionString:   "+connectionString);
		URI uri = new URI(connectionString);

	    // Obtain a ContentSource object for the server at the URI.
	    // ContentSources can create many Sessions.  ContentSources are
	    // tightly bound to a host/port, but user, password and contentbase,
	    // if provided, are defaults and may be overridden for each Session.
	    ContentSource contentSource = ContentSourceFactory.newContentSource(uri);
	    // ContentSource contentSource = ContentSourceFactory.newContentSource(host, port, login, password);

	    // Create a Session, which encapsulates host, port, user and
	    // password, and an optional contentbase id.  If Contentbase is
	    // not specified, the default configured on the server for the
	    // host/port will be used.
	    // Sessions represent a dialog with a contentbase and may hold
	    // state related to that dialog.  A Session is also the factory
	    // for Request objects.  Sessions are lightweight and relatively
	    // cheap to create -- don't bother pooling them, they do not
	    // represent connections.
	    Session session = contentSource.newSession();
	    // Create an ad-hoc Request, which contains XQuery code to be
	    // evaluated.  Requests are mutable and may be re-used repeatedly
	    // and in any order.
	    Request request =null; 
	    
	    // request = session.newAdhocQuery ("\"Hello World\"");
        // request = session.newAdhocQuery("for $a in doc()/root/property_1 return $a");
	    request=session.newAdhocQuery("/root/(property_1|property_2)");
	    // request=session.newAdhocQuery("/root/(property_1|property_2)/..");
	    // request=session.newAdhocQuery("/root");
	    // request=session.newAdhocQuery("doc()/root/*/text() [starts-with(lower-case(.), \"value_\")]");
	    // request=session.newAdhocQuery("doc()/root/*/text() [ends-with(lower-case(.), \"_3\")]");
	    // request=session.newAdhocQuery("doc()/root/*/text() [contains(lower-case(.), \"value\")]");
	    // request=session.newAdhocQuery("distinct-values(doc()/root/*/text() [contains(lower-case(.), \"value\")])");
	    // request=session.newAdhocQuery("doc()/root/*/text() [contains(lower-case(.), \"value\")]");
	    // request=session.newAdhocQuery("doc()/root[contains(lower-case(.), 'property')]");
	    // request=session.newAdhocQuery("/root/*[starts-with((./..),'property_1')]");
	    
	    /* ContentCreateOptions createOptions = new ContentCreateOptions();
	    createOptions.setEncoding("UTF-8");
	    Content content =ContentFactory.newContent(connectionString, "".getBytes(), createOptions);
	    System.out.println("try to insert");
	    session.insertContent(content);*/
	    
	    
	    
	    // Submit the Request and return a new ResultSequence object.
	    // By default, the result will be cached and need not be closed.
	    System.out.println("submit request");
	    
	    ResultSequence rs = session.submitRequest(request);

	    // Print the String representation of the ResultSequence.
	    // In this case, there is only one item in the sequence.
	    // Not that "asString()" is different than "toString()".
	    // The asString() method returns the value of the object
	    // after converting it to String form.  But toString()
	    // returns a descriptive String that summarizes the state
	    // of an object.
	    System.out.println(rs.asString());

	    // All done
	    session.close();
	    System.out.println("done");
	}
	

}
