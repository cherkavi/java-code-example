package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {
	public static void main(String[] args) throws Exception {
		SerialPortProxyOutputStream outputStream=new SerialPortProxyOutputStream(10000);
		String line="";
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
		while(!line.equals("q")){
			line=reader.readLine();
			//outputStream.write(line.getBytes());
			outputStream.write(257);
			outputStream.flush();
		}
		checkArray();
		System.out.println("end");
	}
	
	
	public static void checkArray(int ... values){
		System.out.println(values);
		for(int counter=0;counter<values.length;counter++){
			System.out.println(values[counter]);
		}
	}
}
