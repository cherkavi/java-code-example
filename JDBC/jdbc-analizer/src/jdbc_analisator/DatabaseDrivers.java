package jdbc_analisator;

/** имя драйверов к базе данных  */
public enum DatabaseDrivers {
	Firebird("org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql:[//<HOST>[:<PORT>]/]<DB>" ), 
	MySQL("org.gjt.mm.mysql.Driver", "jdbc:mysql://<HOST>:<PORT>/<DB>" ), 
	Oracle("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@<HOST>:<PORT>:<SID>" );
	
	/** полное имя драйвера  */
	private String driverName;
	/** вид ожидаемого URL  */
	private String urlPattern;
	
	DatabaseDrivers(String driverName, String urlPattern){
		this.driverName=driverName;
		this.urlPattern=urlPattern;
	}
	
	/** получить полное имя драйвера для загрузки  */
	public String getDriverName(){
		return this.driverName;
	}
	
	public String getUrlPattern(){
		return this.urlPattern;
	}
}

