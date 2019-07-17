package com_port.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Test implements ISocketPortListener{
	public static void main(String[] args){
		Logger.getRootLogger().setLevel(Level.DEBUG);
		Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout()));
		if(args.length<4){
			System.out.println("<port listener> <port heart beat> <delay for heart beat> <program and parameters>");
			System.exit(1);
		}
		new Test(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
	}

	public Test(int portListener, int portHeartBeat, int delayHeartBeat){
		try{
			(new SocketPortListener(portListener)).addDataListener(this);
			new SocketHeartBeatProgramRunner(portHeartBeat, delayHeartBeat, "");
		}catch(Exception ex){
			System.err.println("Test: ");
			System.exit(1);
		}
	}
	
	
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
	
	@Override
	public void notifyDataFromPort(byte[] data) {
		System.out.println("Data:"+sdf.format(new Date()));
		for(int counter=0;counter<data.length;counter++){
			System.out.print(data[counter]+"  ");
		}
		System.out.println();
	}
	
	
}
