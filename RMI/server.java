import java.rmi.*;
import java.rmi.registry.*;

public class server{
	// порт
	public static int PORT=2001;
	// имя сервиса
	public static String SERVER_NAME="Example";
	public static void main(String args[]) throws Exception{
	// объект, который реализует удаленный интерфейс и наследуется от UnicastRemoteObject
		remote_interfaceImpl remote=new remote_interfaceImpl();
	// получить доступ к порту
		Registry registry=LocateRegistry.createRegistry(PORT);
		System.out.println("registry created");
		// обратить особое внимание - по умолчанию в корень - хост не нужен
		//registry.rebind("//localhost:"+Integer.toString(PORT)+"/"+SERVER_NAME,remote);
	//регистрация данного сервиса на порту )
		registry.rebind(SERVER_NAME,remote);
		System.out.println("Server started on "+"//localhost:"+Integer.toString(PORT)+"/"+SERVER_NAME);
	}
}
