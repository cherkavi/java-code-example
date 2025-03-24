package copy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;

import database.Field;
import database.Table;

/** объект, который производит копирование из одной таблицы в другую */
public class Worker {
	private Connection connection;
	
	/** объект, который производит копирование из одной таблицы в другую */
	public Worker(Connection connection){
		this.connection=connection;
	}

	/** полностью скопировать данные из источника и перелить их в приемник 
	 * @param source - таблица-источник данных  
	 * @param destination - таблица-приемник данных 
	 * @param commit нужно ли делать Commit
	 * @return
	 */
	public boolean fullCopy(Table source, Table destination, boolean commit){
		boolean returnValue=false;
		ResultSet rs=null;
		PreparedStatement ps=null;
		// create source query
		try{
			rs=connection.createStatement().executeQuery(this.getQuery(source));
			ps=connection.prepareStatement(this.getInsertQuery(destination));
			while(rs.next()){
				ps.clearParameters();
				for(int counter=0;counter<destination.getFieldSize();counter++){
					ps.setObject(counter+1, rs.getObject(counter+1));
				}
				ps.executeUpdate();
			}
			if(commit==true){
				connection.commit();
			}
			returnValue=true;
		}catch(Exception ex){
			System.err.println("fullCopy Exception: "+ex.getMessage());
			returnValue=false;
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
			try{
				ps.close();
			}catch(Exception ex){};
		}
		// create destination preparedstatement 
		return returnValue;
	}

	/** получить запрос к базе данных для вставки значений */
	public String getInsertQuery(Table table){
		StringBuffer returnValue=new StringBuffer();
		// returnValue.append("insert into table(one,two,three) values(?,?,?)");
		returnValue.append("insert into "+table.getName()+"(");
		for(int counter=0;counter<table.getFieldSize();counter++){
			returnValue.append(table.getField(counter).getName());
			if(counter!=(table.getFieldSize()-1)){
				returnValue.append(", ");
			}
		}
		returnValue.append(") values (");
		for(int counter=0;counter<table.getFieldSize();counter++){
			returnValue.append("?");
			if(counter!=(table.getFieldSize()-1)){
				returnValue.append(", ");
			}
		}
		returnValue.append(")");
		return returnValue.toString();
	}
	
	
	/** получить запрос к базе данных на основании таблицы */
	private String getQuery(Table source){
		StringBuffer returnValue=new StringBuffer();
		returnValue.append("select ");
		for(int counter=0;counter<source.getFieldSize();counter++){
			returnValue.append(source.getField(counter).getName());
			if(counter!=(source.getFieldSize()-1)){
				returnValue.append(", ");
			}
			
		}
		returnValue.append(" from ");
		returnValue.append(source.getName());
		if(source.getOrderSize()!=0){
			returnValue.append(" order by ");
			for(int counter=0;counter<source.getOrderSize();counter++){
				returnValue.append(source.getOrderField(counter));
				if(counter!=(source.getOrderSize()-1)){
					returnValue.append(", ");
				}
			}
		}
		return returnValue.toString();
	}
	
	/** получить таблицу из строки 
	 * @param value - строка вида: commodity_paper(id_commodity,SHOW_PAPER_WARRANTY,SHOW_PAPER_COMMODITY)  
	 * @return таблица 
	 */
	public static Table getTableFromString(String value){
		Table table=null;
		try{
			value=value.trim();
			int beginIndex=value.indexOf("(");
			table=new Table(value.substring(0,beginIndex));
			StringTokenizer token=new StringTokenizer(value.substring(beginIndex+1, value.length()-1),",");
			while(token.hasMoreElements()){
				table.addField(new Field(token.nextToken()));
			}
		}catch(Exception ex){
			System.err.println("getTableFromString exception:"+ex.getMessage());
		}
		return table;
	}
}
