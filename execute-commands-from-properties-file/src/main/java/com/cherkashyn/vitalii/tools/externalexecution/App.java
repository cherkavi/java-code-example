package com.cherkashyn.vitalii.tools.externalexecution;

import java.io.IOException;

public class App {
	
    public static void main( String[] args ) {
    	String path="commands-descriptor.properties";
    	ExternalScriptExecutor commandFactory=new ExternalScriptExecutor(path);
    	// Runtime.getRuntime().exec("cmd.exe", "/c", "./com/projct/util/server.bat");
    	
    	try {
			commandFactory.executeCommand("clear");
		} catch (IOException e) {
			System.err.println("can't execute command: "+e.getMessage());
		} catch (InterruptedException e) {
			System.err.println("can't wait until finish execution: "+e.getMessage());
		}
    	
    	System.out.println("-end-");
    }
    
}
