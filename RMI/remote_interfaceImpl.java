import java.rmi.*;
import java.rmi.server.*;

/**
 * объект, который реализует удаленные функции, именно из него нужно будет получить заглушку для клиента
 * %java_home%/bin/rmic remote_interfaceImpl	
 */
public class remote_interfaceImpl extends java.rmi.server.UnicastRemoteObject
                                  implements remote_interface{
	public remote_interfaceImpl() throws java.rmi.RemoteException{
		System.out.println("remote object created");
	}
	
	public String getString(String prefix){
		return "remote answer:"+prefix+" and "+prefix;
	}
}
