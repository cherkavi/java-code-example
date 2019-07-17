package reader;

import java.util.ArrayList;

/** объект, который принимает входящие последовательности, и отискивает в них блоки данных по преамбуле и постамбуле, оповещая о нахождении */
public class ReaderAsync implements IInputDataListener, Runnable{
	/** преамбула блока */
	private byte[] preambule;
	/** постамбула блока */
	private byte[] postambule;
	/** максимальный размер буфера чтения*/
	private int maxCount=0;
	
	
	/** слушатели найденных блоков  */
	private ArrayList<IOutputBlockListener> list=new ArrayList<IOutputBlockListener>();
	
	/** добавить слушателя входящих найденных блоков */
	public void addOutputBlockListener(IOutputBlockListener listener){
		this.list.add(listener);
	}
	
	/** удалить слушателя входящих найденных блоков */
	public void removeOutputBlockListener(IOutputBlockListener listener){
		this.list.remove(listener);
	}
	
	/** оповестить о новом блоке данных всех зарегестрированных слушателей */
	private void notifyAboutNewBlock(byte[] array){
		for(IOutputBlockListener listener: list){
			listener.notifyBlock(array);
		}
	}
	
	/** сложить массивы */
	private byte[] addArray(byte[] ... values){
		int size=0;
		for(int counter=0;counter<values.length;counter++){
			if(values!=null){
				size+=values[counter].length;
			}
		}
		byte[] result=new byte[size];
		
		int index=0;
		for(int counter=0;counter<values.length;counter++){
			if(values[counter]!=null){
				for(int counterInner=0;counterInner<values[counter].length;counterInner++){
					result[index]=values[counter][counterInner];
					index++;
				}
			}
		}
		return result;
	}
	
	/** 
	 * @param array - массив, из которого нужно извлечь часть 
	 * @param indexBegin - индекс начала 
	 * @param indexEnd - индекс окончания 
	 * @return <b>[</b> indexBegin, indexEnd <b>)</b>
	 */
	private byte[] subArray(byte[] array, int indexBegin, int indexEnd){
		byte[] returnValue=new byte[indexEnd-indexBegin];
		for(int counter=indexBegin;counter<indexEnd;counter++){
			returnValue[counter-indexBegin]=array[counter];
		}
		return returnValue;
	}
	
	/** объект, который принимает входящие последовательности, и отискивает в них блоки данных по преамбуле и постамбуле, оповещая о нахождении 
	 * @param preambule - преамбула блока
	 * @param postambule - постамбула блока
	 * @param maxBufferReadSize - 
	 * обязательно нужно вызвать метод {@link #startReader()}
	 */
	public ReaderAsync(byte[] preambule, byte[] postambule, int maxBufferReadSize){
		this.preambule=preambule;
		this.postambule=postambule;
		this.maxCount=maxBufferReadSize;
	}
	
	private boolean flagRun=false;
	
	/** запустить поток */
	public void startReader(){
		this.flagRun=true;
		(new Thread(this)).start();
	}
	
	/** остановить поток */
	public void stopReader(){
		synchronized(this.signalFlag){
			this.signalFlag.notify();
		}
		this.flagRun=false;
	}

	/** главный массив данных, который содержит все полученные последовательности */
	private byte[] mainData=new byte[]{};
	
	/** объект, который следует оповещать о необходимости анализа входящего потока данных */
	private Object signalFlag=new Object();
	
	
	@Override
	public void inputData(byte[] data) {
		synchronized(this.signalFlag){
			mainData=this.addArray(mainData,data);
			this.signalFlag.notify();
		}
	}

	private int indexOfPreambule;
	@Override
	public void run() {
		while(this.flagRun){
			synchronized(this.signalFlag){
				indexOfPreambule=this.indexArrayInArray(this.mainData, this.preambule);
				if(indexOfPreambule<0){
					try{
						this.mainData=new byte[]{};
						this.signalFlag.wait();
					}catch(Exception ex){};
				}
			}
			// появились данные - отработать
			while(true){
				// int indexOfPreambule=this.indexArrayInArray(this.mainData, this.preambule);
				if(indexOfPreambule>=0){
					int indexOfPostambule=this.indexArrayInArray(this.mainData, this.postambule);
					if(indexOfPostambule>=0){
						byte[] body=null;
						synchronized(this.mainData){
							body=this.subArray(this.mainData, indexOfPreambule, indexOfPostambule+this.postambule.length);
							this.mainData=this.subArray(this.mainData, indexOfPostambule+this.preambule.length, this.mainData.length);
						}
						if((body!=null)&&(body.length>0)){
							this.notifyAboutNewBlock(body);
						}
					}else{
						if(this.mainData.length>this.maxCount){
							this.mainData=new byte[]{};
						}
						break;
					}
				}else{
					break;
				}
			}
		}
	}

	/** получить индекс вхождения одного массива в другой 
	 * @param source - источник, в котором происходит поиск 
	 * @param findPart - последовательность, которая должна быть найдена 
	 * @return -1, если последовательность не найдена
	 */
	private int indexArrayInArray(byte[] source, byte[] findPart){
		int returnValue=(-1);
		if((findPart!=null)&&(source!=null)&&(source.length>=findPart.length)){
			for(int counter=0;counter<source.length-findPart.length+1;counter++){
				if(source[counter]==findPart[0]){
					returnValue=counter;
					for(int innerCounter=0;innerCounter<findPart.length;innerCounter++){
						if(findPart[innerCounter]!=source[counter+innerCounter]){
							returnValue=(-1);
							break;
						}
					}
					if(returnValue==counter){
						return returnValue;
					}
				}
			}
		}
		return returnValue;
	}
}
