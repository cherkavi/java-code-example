import java.rmi.*;
/**
 * 
 */
public class client{
	// порт на который обращается клиент
	public static int PORT=2001;
	// имя сервиса, куда стучится клиент
	public static String SERVER_NAME="Example";
	public static void main(String args[]) throws Exception{
		// строка запроса к серверу "//HOST:PORT/SERVICE_NAME"
		String lookup_string="//localhost:"+Integer.toString(PORT)+"/"+SERVER_NAME;
		System.out.println("URL:"+lookup_string);
		// получить ссылку на интерфейс, используя удаленный вызов через URL
		// на самом деле ищем на локальном компьютере заглушку и через нее начинаем обращение
		// на клиенте должна быть и заглушка и описание интерфейса
		remote_interface remote=(remote_interface) Naming.lookup(lookup_string);
		System.out.println("remote_interface getting");
		System.out.println("RESULT:"+remote.getString("temp_value"));		
	}
}