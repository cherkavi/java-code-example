import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * интерфейс, который есть и на клиенте и на сервере, описание удаленных функций
 */
public interface remote_interface extends java.rmi.Remote{
	public String getString(String prefix) throws java.rmi.RemoteException;
}