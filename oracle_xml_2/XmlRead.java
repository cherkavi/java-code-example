package database_connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.time.StopWatch;

public class XmlRead {
	public static void main(String[] args){
		System.out.println("begin");
		Connection connection=null;
		try{
			connection=OracleConnector.getConnection("jdbc:oracle:thin:@xz2013.zur.internal-domain.com:1526:PRT",
				    "TEST",
				    "Password");
			readData(connection);        // Column Type: 2007; Execute time: 813
			readDataAsString(connection);// Column Type: 12;   Execute time: 390
		}finally{
			if(connection!=null){
				try{
					connection.close();
				}catch(SQLException ex){};
			}
		}
		System.out.println("-end-");
	}


	private static void readDataAsString(Connection connection) {
		StopWatch timer=new StopWatch();
		timer.start();
		ResultSet rs=null;
		try{
			String query="select to_char(to_clob(payload)) from crmt_businessobjects WHERE contains(payload,'HASPATH (/payload/product_type/list/*/id=\"1516\")') > 0 and rownum<100";
			rs=connection.createStatement().executeQuery(query);
			System.out.println("Column Type: "+rs.getMetaData().getColumnType(1));
			while(rs.next()){
				// oracle.sql.OPAQUE value=(oracle.sql.OPAQUE)rs.getObject(1);
				// System.out.println("String representation: "+rs.getString(1));
				rs.getString(1);
			}
		}catch(SQLException ex){
			System.err.println("read Data Exception:"+ex.getMessage());
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
		timer.stop();
		System.out.println("Execute time: "+timer.getTime());
	}

	private static void readData(Connection connection) {
		StopWatch timer=new StopWatch();
		timer.start();
		ResultSet rs=null;
		try{
			// String query="select to_char(to_clob(payload)) from crmt_businessobjects WHERE contains(payload,'HASPATH (/payload/product_type/list/*/id=\"1516\")') > 0 and rownum<100";
			String query="select payload from crmt_businessobjects WHERE contains(payload,'HASPATH (/payload/product_type/list/*/id=\"1516\")') > 0 and rownum<100";			
			rs=connection.createStatement().executeQuery(query);
			System.out.println("Column Type: "+rs.getMetaData().getColumnType(1));
			while(rs.next()){
				// oracle.sql.OPAQUE value=(oracle.sql.OPAQUE)rs.getObject(1);
				oracle.xdb.XMLType value=(oracle.xdb.XMLType)rs.getObject(1);
				// System.out.println("String representation: "+value.getStringVal());
				value.getStringVal();
			}
		}catch(SQLException ex){
			System.err.println("read Data Exception:"+ex.getMessage());
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
		timer.stop();
		System.out.println("Execute time: "+timer.getTime());
	}
}
