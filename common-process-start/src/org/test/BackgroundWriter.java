package org.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class BackgroundWriter {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		System.out.println(" - begin - ");
		// String fileName="/home/technik/temp/heartbeat.txt";
		String fileName="c:\\temp\\heartbeat.txt";
		long count=0;
		while(true){
			OutputStream out=new FileOutputStream(fileName, false);
			out.write(Long.toString(++count).getBytes());
			out.flush();
			out.close();
			System.out.println(count);
			TimeUnit.SECONDS.sleep(1L);
		}
	}
	
}
