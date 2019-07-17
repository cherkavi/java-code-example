package common;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.linuxense.javadbf.DBFField;

import database.SqlReservWords;
import database.SqlType;


/** объект, который инкапсулирует все данные об полях в базе */
public class Field {
	private int position;
	private DBFField field;
	private SqlType sqlType=SqlType.VARCHAR;
	private String sqlFieldName;
	private int sqlTypeLength=100;
	private JComponent visualComponent=null;
	
	public Field(DBFField field, int position){
		this.field=field;
		this.position=position;
		// возможно, необходимо конвертирование полей из таблицы
		this.sqlFieldName=this.replaceSqlFieldName(this.field.getName());
		this.visualComponent=createJComponent();
	}
	
	public SqlType getSqlType(){
		return this.sqlType;
	}
	public int getSqlLength(){
		return sqlTypeLength;
	}
	
	private String replaceSqlFieldName(String sqlName){
		if(SqlReservWords.isSqlOperator(sqlName)){
			sqlName=sqlName+"_";
		}
		return sqlName;
	}
	
	private JComponent createJComponent(){
		return new JCheckBox(this.sqlFieldName,true);
	}
	
	public void setComponentBackground(Color color){
		this.visualComponent.setBackground(color);
	}
	
	public JComponent getJComponent(){
		return this.visualComponent;
	}
	
	public DBFField getDbfField(){
		return this.field;
	}

	public int getFieldPosition(){
		return this.position;
	}

	public String getSqlFieldName(){
		return this.sqlFieldName;
	}
	
	/** конвертировать тип DBF файла в тип SQL*/
	public String getSqlTypeFromDbfType(){
		if(field.getDataType()==DBFField.FIELD_TYPE_D){
			// Date
			this.sqlType=SqlType.TIMESTAMP;
			this.sqlTypeLength=0;
			return "TIMESTAMP";
		}
		if(field.getDataType()==DBFField.FIELD_TYPE_F){
			// Double
			this.sqlType=SqlType.DOUBLE;
			this.sqlTypeLength=0;
			return "FLOAT";
		}
		if(field.getDataType()==DBFField.FIELD_TYPE_L){
			// Logical
			this.sqlType=SqlType.VARCHAR;
			this.sqlTypeLength=5;
			return "VARCHAR(5)";
		}
		if(field.getDataType()==DBFField.FIELD_TYPE_M){
			return "VARCHAR("+field.getFieldLength()+")";
		}
		if(field.getDataType()==DBFField.FIELD_TYPE_N){
			// Numeric // INTEGER
			this.sqlTypeLength=0;
			this.sqlType=SqlType.DOUBLE;
			return "FLOAT";
		}
		if(field.getDataType()==DBFField.FIELD_TYPE_C){
			// Character
			this.sqlTypeLength=field.getFieldLength();
			this.sqlType=SqlType.VARCHAR;
			return "VARCHAR("+field.getFieldLength()+")";
		}
		this.sqlType=SqlType.VARCHAR;
		this.sqlTypeLength=field.getFieldLength();
		return "VARCHAR("+field.getFieldLength()+")";
	}
	
	public Object getObjectForSql(Object object) {
		if(object==null){
			return null;
		}
		if(this.sqlType==SqlType.DOUBLE){
			try{
				return new Double(object.toString());
			}catch(Exception ex){
				return new Double(0);
			}
		}
		if(this.sqlType==SqlType.TIMESTAMP){
			try{
				java.sql.Date returnValue=new java.sql.Date(((java.util.Date)(object)).getTime());
				return returnValue;
			}catch(Exception ex){
				java.sql.Date returnValue=new java.sql.Date(  (new java.util.Date(0)).getTime());
				return returnValue;
			}
		}
		if(this.sqlType==SqlType.INTEGER){
			try{
				Double value=new Double(object.toString());
				int intValue=value.intValue();
				return new Integer(intValue);
			}catch(Exception ex){
				return new Integer(0);
			}
		}
		if(this.sqlType==SqlType.VARCHAR){
			try{
				return (String)(object.toString());
			}catch(Exception ex){
				return "";
			}
		}
		return object;
	}
	
	
	/** преобразование прочитанных данных из DBF в "нормальное" представление русских букв */
	public static String convertDosString(String value){
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<value.length();counter++){
			returnValue.append(getCharFromInteger(Integer.valueOf(value.charAt(counter)),value.charAt(counter)));
		}
		return returnValue.toString();
	}
	
	
	public static PreparedStatement getPreparedStatement(Connection connection, String tableName, Field[] fields) throws SQLException{
		StringBuffer query=new StringBuffer();
		query.append("INSERT INTO "+tableName+" (");
		for(int counter=0;counter<fields.length;counter++){
			query.append(fields[counter].getSqlFieldName());
			if(counter!=(fields.length-1)){
				query.append(", ");
			}
		}
		query.append(")");
		query.append(" VALUES(");
		for(int counter=0;counter<fields.length;counter++){
			query.append("?");
			if(counter!=(fields.length-1)){
				query.append(", ");
			}
		}
		query.append(")");
		return connection.prepareStatement(query.toString());		
	}
	
	/** преобразование русских символов в "нормальное отображение "*/
	private static char getCharFromInteger(int value,char defaultValue){
		if(value==(128))return 'А';
		if(value==(129))return 'Б';
		if(value==(130))return 'В';
		if(value==(131))return 'Г';
		if(value==(132))return 'Д';
		if(value==(133))return 'Е';
		if(value==(134))return 'Ж';
		if(value==(135))return 'З';
		if(value==(136))return 'И';
		if(value==(137))return 'Й';
		if(value==(138))return 'К';
		if(value==(139))return 'Л';
		if(value==(140))return 'М';
		if(value==(141))return 'Н';
		if(value==(142))return 'О';
		if(value==(143))return 'П';
		if(value==(144))return 'Р';
		if(value==(145))return 'С';
		if(value==(146))return 'Т';
		if(value==(147))return 'У';
		if(value==(148))return 'Ф';
		if(value==(149))return 'Х';
		if(value==(150))return 'Ц';
		if(value==(151))return 'Ч';
		if(value==(152))return 'Ш';
		if(value==(153))return 'Щ';
		if(value==(154))return 'Ъ';
		if(value==(155))return 'Ы';
		if(value==(156))return 'Ь';
		if(value==(157))return 'Э';
		if(value==(158))return 'Ю';
		if(value==(159))return 'Я';
		if(value==(160))return 'а';
		if(value==(161))return 'б';
		if(value==(162))return 'в';
		if(value==(163))return 'г';
		if(value==(164))return 'д';
		if(value==(165))return 'е';
		if(value==(166))return 'ж';
		if(value==(167))return 'з';
		if(value==(168))return 'и';
		if(value==(169))return 'й';
		if(value==(170))return 'к';
		if(value==(171))return 'л';
		if(value==(172))return 'м';
		if(value==(173))return 'н';
		if(value==(174))return 'о';
		if(value==(175))return 'п';
		if(value==(224))return 'р';
		if(value==(225))return 'с';
		if(value==(226))return 'т';
		if(value==(227))return 'у';
		if(value==(228))return 'ф';
		if(value==(229))return 'х';
		if(value==(230))return 'ц';
		if(value==(231))return 'ч';
		if(value==(232))return 'ш';
		if(value==(233))return 'щ';
		if(value==(234))return 'ъ';
		if(value==(235))return 'ы';
		if(value==(236))return 'ь';
		if(value==(237))return 'э';
		if(value==(238))return 'ю';
		if(value==(239))return 'я';
		//if(value==())return '_';
		if(value==(240))return 'Ё';
		if(value==(242))return 'Є';
		if(value==(244))return 'Ї';
		//if(value==())return '_';
		//if(value==())return '_';
		//if(value==())return '_';
		if(value==(241))return 'ё';
		if(value==(252))return '№';
		if(value==(243))return 'є';
		//if(value==())return '_';
		//if(value==())return '_';
		//if(value==())return '_';
		if(value==(245))return 'ї';
		return defaultValue;
	}
	
	public static String getSqlCreateTable(String tableName,Field[] fields){
		StringBuffer sql=new StringBuffer();
		sql.append("CREATE TABLE ");
		sql.append(tableName);
		sql.append(" ( ");
		for(int counter=0;counter<fields.length;counter++){
			sql.append(fields[counter].getSqlFieldName());
			sql.append(" ");
			sql.append(fields[counter].getSqlTypeFromDbfType());
			if(counter!=(fields.length-1)){
				sql.append(", ");
			}
		}
		sql.append(" ) ");
		return sql.toString();
	}

	public boolean isSelected(){
		try{
			return ((JCheckBox)this.visualComponent).isSelected();
		}catch(Exception ex){
			return false;
		}
	}
}
