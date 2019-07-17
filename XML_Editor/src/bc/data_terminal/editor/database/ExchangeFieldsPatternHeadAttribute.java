package bc.data_terminal.editor.database;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

/** класс, который содержит аттрибуты для элементов заголовка */
public class ExchangeFieldsPatternHeadAttribute {
	private Logger field_logger=Logger.getLogger("HeadAttribute");
	{
		field_logger.setLevel(Level.DEBUG);
	}
	
	/** имя аттрибута */
	private String field_name;
	/** имя переменной */
	private String[] field_values=null;
	private String field_current_value=null;
	
	private int field_flag_type=0;
	public static int TYPE_COMBOBOX=0;
	public static int TYPE_EDIT=1;

	public boolean isCombobox(){
		return (this.field_flag_type==TYPE_COMBOBOX);
	}
	public boolean isEdit(){
		return (this.field_flag_type==TYPE_EDIT);
	}
	
	
	/** получить аттрибут из строки текста, аттрибутом считать заключенные attribute_name="attribute_value" */
	private String getAttributeFromString(String source, String attribute){
		String return_value="";
		int index=source.indexOf(attribute);
		if(index>=0){
			int index_end=source.indexOf("\"",index+attribute.length()+2);
			return_value=source.substring(index+2+attribute.length(),index_end);
		}
		return return_value;
	}

	
	/** получить из HTML текста значение по указанному тэгу(если несколько - берется первый из списка) его аттрибута (если его нет - возвращает пустую строку)
	 * @param html_text full html text
	 * @param first_tag_name name of first tag, wich find into text
	 * @param attribute_name name of attribute into tag
	 * */
	private String getAttributeFromTag(String html_text, String first_tag_name, String attribute_name){
		String return_value="";
		Parser parser=new Parser();
		parser.setLexer(new Lexer(html_text));
		TagNameFilter filter_tr=new TagNameFilter(first_tag_name);
		try{
			NodeList list=parser.parse(filter_tr);
			SimpleNodeIterator iterator=list.elements();
			if(iterator.hasMoreNodes()){
				return_value=this.getAttributeFromString(iterator.nextNode().getText(), attribute_name);
			}
		}catch(Exception ex){
			
		}
		return return_value;
	}

	/** распарсить полученный HTML текст и получить на его основании значения для объекта (название и выделенное значение) TR тэг*/
	public ExchangeFieldsPatternHeadAttribute(String html_text){
		this.field_name=this.getAttributeFromTag(html_text, "SELECT", "id");
		this.field_current_value=this.getAttributeFromTag(html_text, "SELECT", " value");
	}
	
	
	
	/** 
	 * Аттрибут для класса HeadElement 
	 * @param name
	 * @param values
	 */
	public ExchangeFieldsPatternHeadAttribute(String name, String[] values){
		this.field_name=name;
		this.field_values=values;
		this.field_flag_type=TYPE_COMBOBOX;
	}

	
	/** получить имя аттрибута */
	public String getName(){
		return this.field_name;
	}
	
	/** получить значения аттрибутов */
	public String[] getValues(){
		return this.field_values;
	}
	
	/** получить выделенное значение */
	public String getCurrentValue(){
		return this.field_current_value;
	}
	
	/** установить выделенное значение для аттрибута */
	public void setCurrentValue(String value){
		this.field_current_value=value;
	}
	
	public StringBuffer getHtmlText(){
		StringBuffer return_value=new StringBuffer();
		if(this.isCombobox()){
			return_value.append("<b>");
			return_value.append(this.field_name);
			return_value.append("</b>");
			return_value.append("<select id=\""+this.field_name+"\"  >");
			try{
				for(int counter=0;counter<this.field_values.length;counter++){
					if(this.field_current_value!=null){
						if(this.field_current_value.equalsIgnoreCase(this.field_values[counter])){
							return_value.append("<option value=\""+this.field_values[counter]+"\" selected=\"selected\">");
						}else{
							return_value.append("<option value=\""+this.field_values[counter]+"\">");
						}
					}else{
						return_value.append("<option value=\""+this.field_values[counter]+"\">");
					}
					return_value.append(this.field_values[counter]);
					return_value.append("</option>");
				}
			}catch(NullPointerException ex){
				field_logger.error("getHTML:"+ex.getMessage());
			}
			return_value.append("</select>");
		};
		if(this.isEdit()){
			return_value.append("<b>");
			return_value.append(this.field_name);
			return_value.append("</b>");
			return_value.append("<input type=\"text\" id=\""+this.field_name+"\" >");
		}
		return return_value;
	}
}
