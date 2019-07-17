package bc.data_terminal.editor.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;


/** элемент, который отображает на клиент данные, для полей (имя поля, порядок в списке, значение)*/
public class ExchangeFieldsPatternField {
	/** header for HTML attribute id*/
	private String field_id_header="field_";
	/** startup data for display HTML*/
	private String[] field_data;
	
	/** index of fields where placing UNIQUE ELEMENT */
	private int field_index_unique_value=0;
	/** index of fields where placing CAPTION */
	private int field_index_caption=1;
	/** index of fields where placing TYPE */
	private int field_index_type=2;
	/** count of all elements */
	private int field_index_length=3;
	/** поле, которое используется при парсинге текста от клиента, показывает, выбран ли данный элемент */
	private boolean field_checked=false;


	/** возвращает True, если данный элемент выбран на странице HTML */
	public boolean isChecked(){
		return this.field_checked;
	}

	public void setUniqueId(String value){
		if(field_data==null){
			field_data=new String[this.field_index_length];
		}
		field_data[this.field_index_unique_value]=value;
	}
	
	/** получить уникальный индекс данного элемента */
	public String getUniqueId(){
		try{
			return this.field_data[field_index_unique_value];
		}catch(Exception ex){
			return "";
		}
	}

	public void setCaption(String value){
		if(field_data==null){
			field_data=new String[this.field_index_length];
		}
		field_data[this.field_index_caption]=value;
	}

	/** получить заголовок для данного элемента*/
	public String getCaption(){
		try{
			return this.field_data[field_index_caption];
		}catch(Exception ex){
			return "";
		}
	}
	
	

 	public void setType(String value){
 
		if(field_data==null){
			field_data=new String[this.field_index_length];
		}
		field_data[this.field_index_type]=value;
	}
	
	/** получить тип для данного элемента */
	public String getType(){
		try{
			return this.field_data[field_index_type];
		}catch(Exception ex){
			return "";
		}
	}
	
	
	/** Создать элемент, который будет воспринят клиентом как элемент шаблона поля (имя поля, порядок в списке, значение )
	 * на основании записи из таблицы V_DT_EXCHANGE_FILE_PAT_FIELDS
	 * */
	public ExchangeFieldsPatternField(ResultSet rs){
		// TODO - наполнить объект из базы данных
		try{
			this.setUniqueId(rs.getString("ID_FIELD_PATTERN"));
			this.setCaption(rs.getString("NAME_FIELD_PATTERN"));
			this.setType(rs.getString("FORMAT_FIELD_PATTERN"));
		}catch(SQLException ex){
			
		}
	}
	
	/** Создать элемент, который будет воспринят клиентом как элемент шаблона поля (имя поля, порядок в списке, значение )*/
/*	public ExchangeFieldsPatternField(String[] data){
		this.field_data=data;
	}
*/	
	
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
	
	/** создать элемент на основании парсинга полученного HTML текста */
	public ExchangeFieldsPatternField(String html_text){
		if(this.field_data==null){
			this.field_data=new String[this.field_index_length];
		}
		// get name from <TABLE>
		try{
			this.setUniqueId(this.getAttributeFromString(html_text, "id").substring(this.field_id_header.length()));
		}catch(Exception ex){};
		// get another attributes from <TD>
		Parser parser=new Parser();
		parser.setLexer(new Lexer(html_text));
		TagNameFilter filter_td=new TagNameFilter("TD");
		try{
			NodeList list=parser.parse(filter_td);
			SimpleNodeIterator iterator=list.elements();
			Node node;
			int td_counter=0;
			while(iterator.hasMoreNodes()){
				try{
					node=iterator.nextNode();
					// checkbox
					if(td_counter==0){
						if(this.getAttributeFromString(node.getChildren().elementAt(0).getText(), "checked").equals("")){
							this.field_checked=false;
						}else{
							this.field_checked=true;
						}
					};
					// button up
					if(td_counter==1){
					};
					// button first 
					if(td_counter==2){
					};
					// caption
					if(td_counter==3){
						// not work
						//this.setCaption(node.getChildren().elementAt(1).getText());
					};
					// type
					if(td_counter==4){
						// not work
						//this.setType(node.getChildren().elementAt(1).getText());
					};
					// button down
					if(td_counter==5){
						
					};
					// button last
					if(td_counter==6){
						
					};
				}catch(Exception ex){
				}
				td_counter++;
			}
		}catch(Exception ex){
			
		}
		
	}
	
	public String toString(){
		return "Unique_name:"+this.getUniqueId()+"    Checked:"+this.isChecked();
	}
	
	/** конвертировать одну запись из базы данных в таблицу HTML с элементами управления 
	 * @param data - данные в виде массива строк, data[0] - уникальный идентификатор для получения точного значения
	 * выглядит на клиентской стороне как "field_" + уникальный элемент
	 * 
	 * */
	public StringBuffer getHtml(){
		StringBuffer return_value=new StringBuffer();
		try{
			
			return_value.append("<TABLE id=\""+field_id_header+field_data[field_index_unique_value]+"\" border=1>");
			return_value.append("<TR>");
			
			return_value.append("<TD>");
			return_value.append("<INPUT type=\"checkbox\" checked=\"checked\">");
			return_value.append("</TD>");
			return_value.append("<TD>");
			return_value.append("<input type=\"button\" value=\"up\" onclick=\"table_up(this)\">");
			return_value.append("</TD>");
			return_value.append("<TD>");
			return_value.append("<input type=\"button\" value=\"first\" onclick=\"table_first(this)\">");
			return_value.append("</TD>");
			return_value.append("<TD width=300>");
			return_value.append(field_data[field_index_caption]);
			return_value.append("</TD>");
			return_value.append("<TD width=120>");
			return_value.append(field_data[field_index_type]);
			return_value.append("</TD>");
			return_value.append("<TD>");
			return_value.append("<input type=\"button\" value=\"down\" onclick=\"table_down(this)\">");
			return_value.append("</TD>");
			return_value.append("<TD>");
			return_value.append("<input type=\"button\" value=\"last\" onclick=\"table_last(this)\">");
			return_value.append("</TD>");
			
			return_value.append("</TR>");
			return_value.append("</TABLE>");
		}catch(Exception ex){
		}
		return return_value;
	}
	
	
	
}
