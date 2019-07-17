package engine;

import java.io.InputStream;

/** слушатель, который слушает входящие данных */
public class InputListener extends Thread{
	/** объект, из которого нужно слушать входящие сообщения */
	private InputStream stream;
	/** флаг для продолжения прослушивания */
	private boolean flagRun=true;
	/** данные для парсинга */
	private StringBuffer symbolBuffer=new StringBuffer();
	/** анализатор данных, который может брать необходимые данные и отсекать оставшуюся часть */
	private Analizator analizator;
	
	private void debug(Object information){
		System.out.print("InputListener ");
		System.out.print(" DEBUG ");
		System.out.println(information);
	}

	private void error(Object information){
		System.out.print("InputListener ");
		System.out.print(" ERROR ");
		System.out.println(information);
	}
	
	public InputListener(InputStream stream,Analizator analisator){
		this.stream=stream;
		this.analizator=analisator;
		this.flagRun=true;
		
		Thread thread=new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		int aviableByteCount=0;
		int readedBytes=0;
		byte[] buffer=new byte[100]; 
		while(flagRun){
			// проверка на наличие данных 
			if(this.stream!=null){
				aviableByteCount=getAviableByteCount(this.stream);
			}else{
				aviableByteCount=0;
			}
			// чтение всех данных из потока
			while(aviableByteCount>0){
				try{
					readedBytes=this.stream.read(buffer);
					if(readedBytes>0){
						this.addBytesToStringBuffer(buffer, readedBytes, symbolBuffer);
						symbolBuffer=analizator.analize(symbolBuffer);
					}
					aviableByteCount=readedBytes;
				}catch(Exception ex){
					error("run (read all data from stream):"+ex.getMessage());
					break;
				}
			}
			// заснуть на определенное время
			try{
				Thread.sleep(100);
			}catch(Exception ex){
				error("run.sleep "+ex.getMessage());
			};
		}
		debug("InputListener Stop");
	}
	
	/** добавить данные к буферу */
	private void addBytesToStringBuffer(byte[] bytes, int bytesCount, StringBuffer buffer){
		if(bytesCount>0){
			buffer.append(new String(bytes,0,bytesCount));
			//debug(">>> "+buffer.toString());
		}else{
			// нет данных для добавления 
		}
	}
	
	/** получить кол-во доступных для чтения данных */
	private int getAviableByteCount(InputStream is){
		try{
			return is.available();
		}catch(Exception ex){
			error("InputListener#getAviableByteCount Exception:"+ex.getMessage());
			return 0;
		}
	}
	
	public void stopThread(){
		this.flagRun=false;
	}
}
