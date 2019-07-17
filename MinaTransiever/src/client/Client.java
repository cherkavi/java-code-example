package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import common_object.Transport;


public class Client {
	private final static int PORT=2009;
	
    public static void main(String[] args) {
        System.out.println("Client begin");
        try{
        	Transport sendTransport=new Transport("hello from client");
        	Transport answer=sendTransportGetAnswer(sendTransport);
        	System.out.println("Server Get Answer:"+answer);
        }catch(Exception ex){
        	System.err.println("Connect exception "+ex.getMessage());
        }
        System.out.println("Client out");
    }
    
    private static Transport sendTransportGetAnswer(Transport transport) throws Exception{
    	Transport returnValue=null;
    	Socket connection=new Socket("127.0.0.1",PORT);
/*    	URL url=new URL("http://127.0.0.1:"+PORT);
    	url.openStream();
    	URLConnection connection=url.openConnection();
    	connection.setDoInput(true);
    	connection.setDoOutput(true);
    	System.out.println("Client try connect:");
    	connection.connect();
*/
    	
    	OutputStream os=connection.getOutputStream();
    	ObjectOutputStream output=new ObjectOutputStream(os);
    	output.writeObject(transport);
    	//output.close(); - нельзя закрывать данное соединение, иначе будет ошибка 
    	ObjectInputStream input=new ObjectInputStream(connection.getInputStream());
    	Object answerObject=input.readObject();
    	if(answerObject!=null){
    		if(answerObject instanceof Transport){
    			returnValue=(Transport)answerObject;
    		}else{
    			System.err.println("Answer from server is not Transport");
    		}
    	}else{
    		System.err.println("Answer is null");
    	}
    	return returnValue;
    }
}
