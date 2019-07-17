import java.io.Console;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class ConsoleClass {
	/** вывод отладочной информации */
	private static void debug(Object information){
		StackTraceElement element=(new Throwable()).getStackTrace()[1];
		System.out.print(element.getClassName()+"#"+element.getMethodName());
		System.out.print("  DEBUG ");
		System.out.println(information);
	}
	
	private static void printByteArray(PrintStream writer,byte[] byteArray){
		for(int counter=0;counter<byteArray.length;counter++){
			writer.print(counter+":"+(byte)byteArray[counter]);
		}
		writer.println();
	}
	
	/** new Class  {@link java.io.Console} может не работать в режиме отладки, необходим запуск *.class файла из консоли операционной системы  */
	private static void consoleExample(){
		try{
			debug("This is console example: ");
			debug("Get console:");
			Console console=System.console();
			debug("Get writer");
			PrintWriter writer=console.writer();
			writer.println("InputString");
			String inputString=console.readLine();
			System.out.println("InputString:"+inputString);
			System.out.println("Hello, input your password:");
			char[] value=console.readPassword();
			System.out.println("your passowrd:"+new String(value));
		}catch(Exception ex){
			System.out.println("Exception:"+ex.getMessage());
		}
	}
	
	/** получение всех свойств среды, дл€ определени€ некоторых особенностей системы */
	private static void getProperties(String pathToFile){
		Properties properties=System.getProperties();
		try{
			properties.store(new FileOutputStream(pathToFile),"System.properties");
		}catch(Exception ex){
			System.out.println("Properties: "+ex.getMessage());
		}
	}
	
	/** ѕолучение расширенной информации об указанном узле */
	private static void getInformation(){
		try{
			NetworkInterface networkInterface=NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
			
			String displayName=networkInterface.getDisplayName();
			System.out.println("DisplayName:"+displayName);
			
			byte[] hardwareAddress=networkInterface.getHardwareAddress();
			System.out.println("Hardware address:");printByteArray(System.out,hardwareAddress);
			
			Enumeration<InetAddress> address=networkInterface.getInetAddresses();
			System.out.println("Inet Address:");
			InetAddress element;
			while((element=address.nextElement())!=null){
				System.out.println("--- new InetAddress: ---");
				System.out.println("Address:");printByteArray(System.out,element.getAddress());
				System.out.println("CanonicalHostName: "+element.getCanonicalHostName());
				System.out.println("HostAddress:"+element.getHostAddress());
				System.out.println("HostName:"+element.getHostName());
				System.out.println("--------------------");
			}
			
			List<InterfaceAddress> list=networkInterface.getInterfaceAddresses();
			for(InterfaceAddress addressElement:list){
				System.out.println("--- AddressElement ---");
				System.out.println("Network Prefix length:"+addressElement.getNetworkPrefixLength());
				System.out.println("--- BroadCast: begin");
				System.out.println("Address:");printByteArray(System.out,addressElement.getBroadcast().getAddress());
				System.out.println("CanonicalHostName: "+addressElement.getBroadcast().getCanonicalHostName());
				System.out.println("HostAddress:"+addressElement.getBroadcast().getHostAddress());
				System.out.println("HostName:"+addressElement.getBroadcast().getHostName());
				System.out.println("--- BroadCast: end");
				System.out.println("Address begin");
				System.out.println("Address:"+addressElement.getAddress().getAddress());
				System.out.println("CanonicalHostName: "+addressElement.getAddress().getCanonicalHostName());
				System.out.println("HostAddress:"+addressElement.getAddress().getHostAddress());
				System.out.println("HostName:"+addressElement.getAddress().getHostName());
				System.out.println("Address end");
				System.out.println("");
				System.out.println("----------------------");
			}
			
			int mtu=networkInterface.getMTU();
			System.out.println("MTU:"+mtu);
			
			String name=networkInterface.getName();
			System.out.println("NetworkInterface Name:"+name);
			
		}catch(Exception ex){
			
		}
	}
	
	public static void main(String[] args){
		//getProperties("c:\\out.properties"); // java.runtime.version=1.6 не ниже 
		
		//debug("Console Example:");
		//consoleExample();
		
		getInformation();

	}
}
