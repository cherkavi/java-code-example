package database.serializer.unpack;

import java.sql.Connection;
import java.util.ArrayList;

import database.serializer.common.Answer;
import database.serializer.common.RecordWrap;
import database.serializer.unpack.interceptors.Interceptor;

/** класс, который служит для сохранения полученных данных во внешнем хранилище, в частности в базе данных */
public class UnpackRecordFactory {
	/** список перехватчиков */
	private ArrayList<Interceptor> listOfInterceptors=new ArrayList<Interceptor>();
	
	public UnpackRecordFactory(Interceptor ... interceptors ){
		for(int counter=0;counter<interceptors.length;counter++){
			listOfInterceptors.add(interceptors[counter]);
		}
	}

	/** добавить еще один перехватчик в список перехватчиков */
	public void addInterceptor(Interceptor interceptor){
		this.listOfInterceptors.add(interceptor);
	}
	
	/** удалить перехватчик из списка перехватчиков */
	public void removeInterceptor(Interceptor interceptor){
		this.listOfInterceptors.remove(interceptor);
	}
	
	/** удалить все перехватчики */
	public void removeAllInterceptors(){
		this.listOfInterceptors.clear();
	}
	
	/** получить кол-во всех интерцепторов в объекте */
	public int getInterceptorCount(){
		return this.listOfInterceptors.size();
	}
	
	/** обработать полученный пакет данных и получить ответы по каждому из них
	 * <b>!!! данне НЕ подтверждаются Connection.commit()</b>
	 * @param records - записи, которые нужно обработать
	 * @return - массив из значений ({@link Answer#OK}, {@link Answer#ERROR}, {@link Answer#CANCEL}), которые говорят об обработанных данных 
	 */
	public int[] processPackage(RecordWrap[] records, Connection connection){
		if(records!=null){
			int[] returnValue=new int[records.length];
			// перебрать все полученные пакеты с записями 
			for(int recordCounter=0;recordCounter<records.length;recordCounter++){
				try{
					boolean processOk=false;
					// перебрать все зарегестрированные перехватчики для каждого конкретного пакета 
					for(int counter=0;counter<this.listOfInterceptors.size();counter++){
						// подходит ли данный пакет для перехватчика ?
						if(this.listOfInterceptors.get(counter).isValidRecord(records[recordCounter])){
							// попытка обработки пакета
							processOk=this.listOfInterceptors.get(counter)
							                                 .processRecord(records[recordCounter],connection);
							break;
						}
					}
					// проверка, был ли обработан данный пакет 
					if(processOk==false){
						// нет, пакет не был обработан 
						returnValue[recordCounter]=Answer.CANCEL;
					}else{
						// да, пакет был обработан 
						returnValue[recordCounter]=Answer.OK;
					}
				}catch(Exception ex){
					System.err.println("UnpackRecordFactory#processPackage Exception: "+ex.getMessage());
					returnValue[recordCounter]=Answer.ERROR;
				}
			};
			return returnValue;
		}else{
			return null;
		}
	}
}
