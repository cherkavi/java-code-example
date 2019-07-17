package reader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EnterPoint extends Thread implements IOutputBlockListener{
	public static void main(String[] args){
		System.out.println("begin");
		(new EnterPoint()).start();
		System.out.println("-end");
	}
	
	public EnterPoint(){
		reader=new ReaderAsync("+".getBytes(), "=".getBytes());
		reader.addOutputBlockListener(this);
	}

	private ReaderAsync reader; 
	/** запуск потока на чтение из консоли */
	public void run(){
		this.reader.startReader();
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
		while(true){
			try{
				String line=reader.readLine();
				if(line.equalsIgnoreCase("q")){
					break;
				}
				this.reader.inputData(line.getBytes());
			}catch(Exception ex){
				ex.printStackTrace();
				System.err.println("Exception: "+ex.getMessage());
			}
		}
		this.reader.stopReader();
	}

	@Override
	public void notifyBlock(byte[] array) {
		System.out.println("New Block: "+new String(array));
	}
}
