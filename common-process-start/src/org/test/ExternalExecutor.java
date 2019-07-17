package org.test;

import java.io.IOException;

public class ExternalExecutor {
	
	public static void main(String[] args) throws IOException{
		executeForWindows();
		System.out.println("started");
	}
	
	private static void executeForWindows() throws IOException{
		Runtime.getRuntime().exec(new String[]{"java", "-jar", "c:\\temp\\file-writer.jar","&"});
	}
	
	private static void executeForLinux() throws IOException{
		Runtime.getRuntime().exec(new String[]{"java", "-jar", "/home/technik/temp/file-writer.jar","&"});
	}
}
