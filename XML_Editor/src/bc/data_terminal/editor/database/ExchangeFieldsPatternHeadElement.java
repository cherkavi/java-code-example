package bc.data_terminal.editor.database;

import java.util.ArrayList;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

/** Класс, который содержит представление полей заголовка в файле XML */
public class ExchangeFieldsPatternHeadElement {
	/** имя аттрибута */
	private String field_name;
	private ExchangeFieldsPatternHeadAttribute[] field_attributes;
	
	/** аттрибут из заголовка - создание для HTML */
	public ExchangeFieldsPatternHeadElement(String element_name, ExchangeFieldsPatternHeadAttribute ... attributes){
		this.field_name=element_name;
		this.field_attributes=attributes;
	}
	
	/** получить аттрибут из строки текста, аттрибутом считать заключенные attribute_name="attribute_value" */
	protected String getAttributeFromString(String source, String attribute){
		String return_value="";
		int index=source.indexOf(attribute);
		if(index>=0){
			int index_end=source.indexOf("\"",index+attribute.length()+2);
			return_value=source.substring(index+2+attribute.length(),index_end);
		}
		return return_value;
	}
	
	/** возвращает true, если имя параметра совпадает с именем переданного значения */
	public boolean isNameEquals(String value){
		boolean return_value=false;
		if((field_name!=null)&&(value!=null)){
			return_value=field_name.equalsIgnoreCase(value);
		}else{
			//return_value=false;
		}
		
		return return_value;
	}
	
	/** установить значение для аттрибута, который может содержаться в данном объекте
	 * @param attribute_name - имя аттрибута, который может содержаться в данном объекте
	 * @param value - значение аттрибута, который может содержаться в данном объекте
	 * @return true - если аттрибут найден и установлен в нужное значение 
	 * */
	public boolean setAttributeValue(String attribute_name, String value){
		boolean return_value=false;
		if(this.field_attributes!=null){
			for(int counter=0;counter<this.field_attributes.length;counter++){
				if(this.field_attributes[counter].getName().equalsIgnoreCase(attribute_name)){
					this.field_attributes[counter].setCurrentValue(value);
					return_value=true;
					break;
				}
			}
		}
		return return_value;
	}
	
	/** получить значение аттрибута, который содержится в данном объекте*/
	public String getAttributeValue(String attribute_name){
		String return_value="";
		if(this.field_attributes!=null){
			for(int counter=0;counter<this.field_attributes.length;counter++){
				if(this.field_attributes[counter].getName().equalsIgnoreCase(attribute_name)){
					return_value=this.field_attributes[counter].getCurrentValue();
					break;
				}
			}
		}
		return return_value;
	}
	

	
	/** парсинг из полученного HTML текста (кода */
	public ExchangeFieldsPatternHeadElement(String html_text){
		
		this.field_name=getAttributeFromString(html_text, "id");
		ArrayList<ExchangeFieldsPatternHeadAttribute> attribute_list=new ArrayList<ExchangeFieldsPatternHeadAttribute>();
		
		Parser parser=new Parser();
		parser.setLexer(new Lexer(html_text));
		TagNameFilter filter_tr=new TagNameFilter("tr");
		try{
			NodeList list=parser.parse(filter_tr);
			SimpleNodeIterator iterator=list.elements();
			Node tr_element;
			int attribute_counter=0;
			while(iterator.hasMoreNodes()){
				tr_element=iterator.nextNode();
				if(attribute_counter>0){
					attribute_list.add(new ExchangeFieldsPatternHeadAttribute(tr_element.toHtml()));
					// element is not first, first is header
					// get SelectId, get selected_value
				}
				attribute_counter++;
			}
			this.field_attributes=attribute_list.toArray(new ExchangeFieldsPatternHeadAttribute[]{});
		}catch(ParserException ex){
			// error Parsing
			field_name=null;
			field_attributes=null;
		}catch(Exception ex){
			// error Parsing
			field_name=null;
			field_attributes=null;
		}
	}
	
	public String toString(){
		if(this.field_attributes!=null){
			StringBuffer return_value=new StringBuffer();
			return_value.append("Name:"+this.field_name+" [");
			for(int counter=0;counter<this.field_attributes.length;counter++){
				return_value.append(this.field_attributes[counter].getName()+":"+this.field_attributes[counter].getCurrentValue());
				if(counter!=(this.field_attributes.length-1)){
					return_value.append(", ");
				}
			}
			return_value.append("]");
			return return_value.toString();
		}else{
			return "Name:"+this.field_name+"  attributes is null";
		}
	}
	
	/** Получить Html текст по данному элементу для возможного отображения на полях формы*/
	public StringBuffer getHtmlText(){
		// получить HTML по элементу
		StringBuffer return_value=new StringBuffer();
		return_value.append("<TABLE id=\""+field_name+"\" border=1 width=150>");
		return_value.append("	<TR>");
		return_value.append("	<TH>");
		return_value.append("	<CENTER>");
		return_value.append(this.field_name);
		return_value.append("	</CENTER>");
		return_value.append("	</TH>");
		return_value.append("	</TR>");
		try{
			for(int counter=0;counter<this.field_attributes.length;counter++){
				return_value.append("<TR>");
				return_value.append("<TD>");
				return_value.append(this.field_attributes[counter].getHtmlText());
				return_value.append("</TD>");
				return_value.append("</TR>");
			}
		}catch(NullPointerException ex){
			System.out.println("getHtmlText: Null Pointer Exception");
		}
		return_value.append("</TABLE>");
		return return_value;
	}

	/** получить имя данного Pattern*/
	public String getName(){
		return this.field_name;
	}
	
	/** получить значение первого аттрибута, если его нет - вернуть пустую строку*/
	public String getFirstAttributeValue(){
		String return_value=null;
		if(this.field_attributes.length>0){
			return_value=this.field_attributes[0].getCurrentValue();
		}
		return (return_value==null)?"":return_value;
	}
}
















