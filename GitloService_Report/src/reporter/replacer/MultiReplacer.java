package reporter.replacer;

import java.text.MessageFormat;
import java.util.ArrayList;

/** заполнение всех добавленных объектов в виде переданной в конструкторе строки для оператора MessageFormat*/
public class MultiReplacer extends ReplaceValue{
	/** строка, которая является форматом для заполнения MessageFormat.format*/
	private String messageFormatString;
	/** вывод значения по умолчанию, в случае не добавления ни одной строки для вывода*/
	private String defaultValue=null;
	/** объекты для вывода в строки */
	private ArrayList<Object[]> listOfObject=new ArrayList<Object[]>();
	
	/** заполнение всех добавленных объектов в виде переданной в контрукторе строки для оператора MessageFormat
	 * @param messageFormatString - строка, которая является шаблоном для MessageFormat.format
	 * */
	public MultiReplacer(String messageFormatString,String defaultValue){
		this.messageFormatString=messageFormatString;
		this.defaultValue=defaultValue;
	}
	
	/** очистить все мульти-объекты*/
	public void clearObjects(){
		this.listOfObject.clear();
	}
	
	public void add(Object ... objects){
		this.listOfObject.add(objects);
	}
	@Override
	public String getReplaceValue() {
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<listOfObject.size();counter++){
			returnValue.append(MessageFormat.format(messageFormatString, this.listOfObject.get(counter)));
		}
		if(returnValue.length()==0){
			return defaultValue;
		}else{
			return returnValue.toString();
		}
	}

}
