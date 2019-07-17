package database;

public enum EConnectionType {
	oracle("oracle.jdbc.driver.OracleDriver",
			"org.hibernate.dialect.OracleDialect"){
		
		/*
		 *  Oracle Thin
			jdbc:oracle:thin:@<HOST>:<PORT>:<SID>
		 */
		@Override
		public String getConnectionString(String host, int port, String databaseName){
			if(this.connectionString==null) {
				return "jdbc:oracle:thin:@"+host+":"+((port==0)?getDefaultPort():port)+":"+databaseName;
			}
			else return connectionString;
		}
		
		@Override
		public int getDefaultPort() {
			return 1521;
		}
	},

	firebird("org.firebirdsql.jdbc.FBDriver","org.hibernate.dialect.FirebirdDialect"){
		/*
			Firebird (JCA/JDBC Driver)
			jdbc:firebirdsql:[//<HOST>[:<PORT>]/]<DB>
		 */
		@Override
		public String getConnectionString(String host, int port, String databaseName){
			if(this.connectionString==null) {
				return "jdbc:firebirdsql://"+host+":"+((port==0)?getDefaultPort():port)+"/"+databaseName;
			}
			else return connectionString;
		}

		@Override
		public int getDefaultPort() {
			return 3050;
		}
	},
	
	mysql("org.gjt.mm.mysql.Driver","org.hibernate.dialect.MySQLDialect"){
		/*
			MySQL (MM.MySQL Driver)
			jdbc:mysql://<HOST>:<PORT>/<DB>
		 */
		@Override
		public String getConnectionString(String host, int port, String databaseName){
			if(this.connectionString==null) {
				return "jdbc:mysql://"+host+":"+((port==0)?getDefaultPort():port)+"/"+databaseName;
			}
			else return connectionString;
		}

		@Override
		public int getDefaultPort() {
			return 3306;
		}
		
	};
	
	/** получить объект-тип соединения */
	public static EConnectionType getConnectionType(String databaseUrl){
		EConnectionType returnValue=null;
		while(databaseUrl!=null){
			// MySQL
			if(databaseUrl.indexOf(":mysql:")>0){
				returnValue=EConnectionType.mysql;
				break;
			}
			// Oracle
			if(databaseUrl.indexOf(":oracle:")>0){
				returnValue=EConnectionType.oracle;
				break;
			}
			// Firebird
			if(databaseUrl.indexOf(":firebirdsql:")>0){
				returnValue=EConnectionType.firebird;
				break;
			}
			break;
		}
		return returnValue;
	}
	
	/** строка соединения с базой данных, если не равна null -  */
	protected String connectionString=null;
	
	public void setConnectionString(String connectionString){
		this.connectionString=connectionString;
	}
	
	/** 
	 * @param connectDriverClassName - имя драйвера класса для DriverManager
	 * @param hibernateDialect - 
	 * */
	EConnectionType(String connectDriverClassName, String hibernateDialect){
		this.driverName=connectDriverClassName;
		this.hibernateDialect=hibernateDialect;
	}
	
	private String driverName;
	private String hibernateDialect;
	
	/** @return получить имя драйвера соединения с базой данных */
	public String getDriverName(){
		return this.driverName;
	}
	
	/** @return получить имя Hibernate диалекта */
	public String getHibernateDialect(){
		return this.hibernateDialect;
	}

	/** получить строку соединения с указанной базой данных  
	 * @param host - хост, на который нужно заходить 
	 * @param port - порт
	 * @param databaseName - имя базы данных 
	 * @return
	 */
	public abstract String getConnectionString(String host, int port, String databaseName);
	
	/** @return получить порт по-умолчанию для указанной базы данных */
	public abstract int getDefaultPort();
}


/*
 * 
IBM DB2
jdbc:db2://<HOST>:<PORT>/<DB>
COM.ibm.db2.jdbc.app.DB2Driver

JDBC-ODBC Bridge
jdbc:odbc:<DB>
sun.jdbc.odbc.JdbcOdbcDriver

Microsoft SQL Server
jdbc:weblogic:mssqlserver4:<DB>@<HOST>:<PORT>
weblogic.jdbc.mssqlserver4.Driver

PointBase Embedded Server
jdbc:pointbase://embedded[:<PORT>]/<DB>
com.pointbase.jdbc.jdbcUniversalDriver

Cloudscape
jdbc:cloudscape:<DB>
COM.cloudscape.core.JDBCDriver

Cloudscape RMI
jdbc:rmi://<HOST>:<PORT>/jdbc:cloudscape:<DB>
RmiJdbc.RJDriver

IDS Server
jdbc:ids://<HOST>:<PORT>/conn?dsn='<ODBC_DSN_NAME>'
ids.sql.IDSDriver

Informix Dynamic Server
jdbc:informix-sqli://<HOST>:<PORT>/<DB>:INFORMIXSERVER=<SERVER_NAME>
com.informix.jdbc.IfxDriver

InstantDB (v3.13 and earlier)
jdbc:idb:<DB>
jdbc.idbDriver

InstantDB (v3.14 and later)
jdbc:idb:<DB>
org.enhydra.instantdb.jdbc.idbDriver

Interbase (InterClient Driver)
jdbc:interbase://<HOST>/<DB>
interbase.interclient.Driver

Hypersonic SQL (v1.2 and earlier)
jdbc:HypersonicSQL:<DB>
hSql.hDriver

Hypersonic SQL (v1.3 and later)
jdbc:HypersonicSQL:<DB>
org.hsql.jdbcDriver

Microsoft SQL Server (JTurbo Driver)
jdbc:JTurbo://<HOST>:<PORT>/<DB>
com.ashna.jturbo.driver.Driver

Microsoft SQL Server (Sprinta Driver)
jdbc:inetdae:<HOST>:<PORT>?database=<DB>
com.inet.tds.TdsDriver

Microsoft SQL Server 2000 (Microsoft Driver)
jdbc:microsoft:sqlserver://<HOST>:<PORT>[;DatabaseName=<DB>]
com.microsoft.jdbc.sqlserver.SQLServerDriver

Oracle OCI 8i
jdbc:oracle:oci8:@<SID>
oracle.jdbc.driver.OracleDriver

Oracle OCI 9i
jdbc:oracle:oci:@<SID>
oracle.jdbc.driver.OracleDriver

PostgreSQL (v6.5 and earlier)
jdbc:postgresql://<HOST>:<PORT>/<DB>
postgresql.Driver

PostgreSQL (v7.0 and later)
jdbc:postgresql://<HOST>:<PORT>/<DB>
org.postgresql.Driver

Sybase (jConnect 4.2 and earlier)
jdbc:sybase:Tds:<HOST>:<PORT>
com.sybase.jdbc.SybDriver

Sybase (jConnect 5.2)
jdbc:sybase:Tds:<HOST>:<PORT>
com.sybase.jdbc2.jdbc.SybDriver


*/