package cherkashyn.vitalii.tool.remotestart.os;

import java.io.IOException;

public class RemoteStart {
	private final static String DELIMITER=" ";
	
	public static void main(String[] args) throws IOException{
		if(args.length<1){
			System.out.println("need to set path to <plink>");
			System.exit(1);
		}
		String server="192.168.0.11";
		String login="root";
		String password="0954671434";
		
		StringBuilder command=new StringBuilder();
		command.append(args[0]);
		command.append(DELIMITER);
		command.append(server);
		command.append(DELIMITER);
		command.append("-l");
		command.append(DELIMITER);
		command.append(login);
		command.append(DELIMITER);
		command.append("-pw");
		command.append(DELIMITER);
		command.append(password);
		command.append(DELIMITER);
		command.append("/soft/tomcat/apache-tomcat-6.0.37/bin/startup.sh");
		
		Runtime.getRuntime().exec(command.toString().split(" "));
	}
}
