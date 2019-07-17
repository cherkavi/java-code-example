package com.cherkashyn.vitaliy.devices.control.ping;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class ConsolePing {
	private final static String ip="193.201.80.186";
	private static String charsetName="IBM866";
	public static void main(String[] args) throws IOException{
//		Map<String, Charset> map=Charset.availableCharsets();
//		for(String key:map.keySet()){
//			System.out.println("Key:"+key);
//		}
		
		Process process=Runtime.getRuntime().exec("ping "+ip);
		BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName(charsetName)));
		StringBuilder buffer=new StringBuilder();
		String line=null;
		while( (line=reader.readLine())!=null){
			if(!line.trim().equals("")){
				buffer.append(line);
			}
		}
		System.out.println("Result:"+map.get(charsetName).analise(buffer.toString()));
	}
	
	private interface Strategy{
		public boolean analise(String result);
	}
	
	private static Map<String, Strategy> map=new HashMap<String, Strategy>();
	static{
		map.put("IBM866", new Strategy(){
			private final String marker="получено =";
			@Override
			public boolean analise(String result) {
				System.out.println("Raw:"+result);
				int positionBegin=result.indexOf(marker);
				int positionEnd=result.indexOf(",",positionBegin);
				try{
					Integer pingCount=Integer.parseInt(result.substring(positionBegin+marker.length(), positionEnd).trim());
					return (pingCount!=null && pingCount>0);
				}catch(Exception ex){
					return false;
				}
			}
		});
	}
}
