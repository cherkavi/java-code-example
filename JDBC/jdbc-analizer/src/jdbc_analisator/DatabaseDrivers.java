package jdbc_analisator;

/** ��� ��������� � ���� ������  */
public enum DatabaseDrivers {
	Firebird("org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql:[//<HOST>[:<PORT>]/]<DB>" ), 
	MySQL("org.gjt.mm.mysql.Driver", "jdbc:mysql://<HOST>:<PORT>/<DB>" ), 
	Oracle("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@<HOST>:<PORT>:<SID>" );
	
	/** ������ ��� ��������  */
	private String driverName;
	/** ��� ���������� URL  */
	private String urlPattern;
	
	DatabaseDrivers(String driverName, String urlPattern){
		this.driverName=driverName;
		this.urlPattern=urlPattern;
	}
	
	/** �������� ������ ��� �������� ��� ��������  */
	public String getDriverName(){
		return this.driverName;
	}
	
	public String getUrlPattern(){
		return this.urlPattern;
	}
}

