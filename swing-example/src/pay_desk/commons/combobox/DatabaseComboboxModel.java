package pay_desk.commons.combobox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/** модель из базы данных */
public class DatabaseComboboxModel extends AbstractComboboxModel{
	private PreparedStatement getAllElement;
	private PreparedStatement getKeyBySelectedElement;
	private String fieldKey;
	private String fieldName;
	private Object selectedElement;
	
	/** модель из базы данных 
	 * @param connection - текущее соединение с базой данных
	 * @param tableName - имя таблицы
	 * @param fieldKey - имя поля-ключа
	 * @param fieldName - имя поля отображения
	 * @param currentKey - текущее значение ключа
	 */
	public DatabaseComboboxModel(Connection connection, String tableName, String fieldKey, String fieldName, Integer currentKey){
		this.fieldKey=fieldKey;
		this.fieldName=fieldName;
		try{
			// create getAllElement
			this.getAllElement=connection.prepareStatement("select "+fieldName+" from "+tableName +" order by "+fieldKey);
			this.getKeyBySelectedElement=connection.prepareStatement("select "+fieldKey+" from "+tableName+" where "+fieldName+"=?");
			if(currentKey!=null){
				ResultSet rs=connection.createStatement().executeQuery("select "+fieldName+" from "+tableName+" where "+fieldKey+"="+currentKey);
				if(rs.next()){
					this.selectedElement=rs.getString(fieldName);
				}else{
					this.selectedElement=null;
				}
			}else{
				try{
					this.selectedElement=this.getAllElements()[0];
				}catch(Exception ex){
					this.selectedElement=null;
				};
			}
			
			// fill selectedElement
		}catch(Exception ex){
			System.err.println("Exception ex:"+ex.getMessage());
		}
	}
	
	@Override
	public Object[] getAllElements() {
		ArrayList<String> returnValue=new ArrayList<String>();
		ResultSet rs=null;
		try{
			rs=this.getAllElement.executeQuery();
			while(rs.next()){
				returnValue.add(rs.getString(this.fieldName));
			}
		}catch(Exception ex){
			System.err.println("getAllElement Exception: "+ex.getMessage());
		}finally{
			try{
				rs.close();
			}catch(Exception ex){};
		}
		return returnValue.toArray(new String[]{});
	}

	@Override
	public Object getSelectdElement() {
		return this.selectedElement;
	}

	@Override
	public void setSelectedElement(Object object) {
		this.selectedElement=object;
	}

	@Override
	public Object getKeySelectedElement() {
		Integer returnValue=null;
		ResultSet rs=null;
		try{
			this.getKeyBySelectedElement.setObject(1,this.getSelectdElement());
			rs=getKeyBySelectedElement.executeQuery();
			while(rs.next()){
				returnValue=rs.getInt(this.fieldKey);
				if(rs.wasNull()){
					returnValue=null;
				}
			}
		}catch(Exception ex){
			System.err.println("getAllElement Exception: "+ex.getMessage());
		}
		return returnValue;
	}

}
