package bc.data_terminal.editor.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/** класс, который является оберткой для переменных записи из таблицы V_DT_EXCHANGE_FILE_PAT_FIELDS или V_DT_EXCHANGE_FILE_FIELDS 
 * */
class ExchangeFileField{
	/** header for HTML attribute id*/
	private String field_id_header="field_";
	
	private void debug(String information){
		System.out.print("ExchangeFileFields:");
		System.out.print(" DEBUG: ");
		System.out.println(information);
	}
	private void error(String information){
		System.out.print("ExchangeFileFields:");
		System.out.print(" ERROR: ");
		System.out.println(information);
	}
 
	
    String p_cd_field="";
    String p_name_field="";
    String p_format_field="";
    String p_order_number="";
    String p_required="";
    String p_id_exchange_file_pattern="";
    String p_id_field_pattern="";
    
    /** объект-обертка для записи, которая четко идентифицирует одно поле файла 
     * */
    public ExchangeFileField(String id_file){
    	
    }
    /** загрузить в поля данные из ResultSet таблица: V_DT_EXCHANGE_FILE_PAT_FIELDS*/
    public ExchangeFileField(ResultSet rs,int order_counter){
    	this.loadFromResultSetByPattern(rs);
    	this.setOrderNumber(order_counter);
    }
    
    public ExchangeFileField(ResultSet rs){
    	this.loadFromResultSet(rs);
    }
    
    /** загрузить данные из таблицы V_DT_EXCHANGE_FILE_FIELDS */
    public boolean loadFromResultSet(ResultSet rs){
    	debug("loadFromResultSet:begin ");
    	boolean return_value=false;
    	try{
    		this.p_cd_field=rs.getString("CD_FIELD");
    		this.p_name_field=rs.getString("NAME_FIELD");
    		this.p_format_field=rs.getString("FORMAT_FIELD");
    		//this.p_order_number=rs.getString("");
    		this.p_required=rs.getString("REQUIRED");
    		this.p_id_exchange_file_pattern=rs.getString("ID_EXCHANGE_FILE");
    		this.p_id_field_pattern=rs.getString("ID_FIELD_PATTERN");
    		this.p_order_number=rs.getString("ORDER_NUMBER");
    		return_value=true;
    	}catch(SQLException ex){
    		error("loadFromResultSet SQLException:"+ex.getMessage());
    	}catch(Exception ex){
    		error("loadFromResultSet    Exception:"+ex.getMessage());
    	}
    	debug("loadFromResultSet:end ");
    	return return_value;
    }

    /** чтение данных из шаблонной таблицы V_DT_EXCHANGE_FILE_PAT_FIELDS */
    public boolean loadFromResultSetByPattern(ResultSet rs){
    	debug("loadFromResultSet:begin ");
    	boolean return_value=false;
    	try{
    		this.p_cd_field=rs.getString("CD_FIELD_PATTERN");
    		this.p_name_field=rs.getString("NAME_FIELD_PATTERN");
    		this.p_format_field=rs.getString("FORMAT_FIELD_PATTERN");
    		//this.p_order_number=rs.getString("");
    		this.p_required=rs.getString("REQUIRED_PATTERN");
    		this.p_id_exchange_file_pattern=rs.getString("ID_EXCHANGE_FILE_PATTERN");
    		this.p_id_field_pattern=rs.getString("ID_FIELD_PATTERN");
    		this.p_order_number=rs.getString("");
    		return_value=true;
    	}catch(SQLException ex){
    		error("loadFromResultSet SQLException:"+ex.getMessage());
    	}catch(Exception ex){
    		error("loadFromResultSet    Exception:"+ex.getMessage());
    	}
    	debug("loadFromResultSet:end ");
    	return return_value;
    }
    
    /** установить порядковый номер данного поля */
    public void setOrderNumber(int value){
    	this.p_order_number=Integer.toString(value);
    }
	
    /** получить данный элемент в виде HTML кода */
	public StringBuffer getHtml(){
		StringBuffer return_value=new StringBuffer();
		try{
			
			return_value.append("<TABLE id=\""+field_id_header+this.p_id_field_pattern+"\" border=1>");
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
			return_value.append(this.p_name_field);
			return_value.append("</TD>");
			return_value.append("<TD width=120>");
			return_value.append(this.p_format_field);
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
