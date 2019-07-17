package engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Test {
	public static void main(String[] args){
		System.out.println("begin");
		try{
			/** анализатор для анализа входящих значений, и хранения выборочных данных */
			Analizator analisator=new Analizator();
			/** труба, в которую нужно записывать прочтенные данные */
			PipedOutputStream pipeOutput=new PipedOutputStream();
			/** труба, которая принимает данные  на другой стороне - в другом потоке */
			PipedInputStream pipeInput=new PipedInputStream(pipeOutput);
			/** слушатель, который слушает входящий поток и передает данные на анализатор  - одновременный запуск отдельного потока */
			InputListener listener=new InputListener(pipeInput,analisator);
			FileInputStream fis=new FileInputStream(new File("c:\\com_data.txt"));
			byte[] buffer=new byte[10];
			int readedBytes=0;
			while(fis.available()>0){
				readedBytes=fis.read(buffer);
				if(readedBytes>0){
					pipeOutput.write(buffer,0,readedBytes);
					pipeOutput.flush();
				}
			}
			fis.close();
			//listener.join();
			//pipeOutput.write(bytes);
			Thread.sleep(2000);
			listener.stopThread();
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
		 
		System.out.println("end");
	}
	
	private static byte[] getBytes(byte start, int size){
		byte[] returnValue=new byte[size];
		for(int counter=start;counter<start+size;counter++){
			returnValue[counter-start]=(byte)counter;
		}
		return returnValue;
	}
}
