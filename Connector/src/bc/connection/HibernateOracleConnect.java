package bc.connection;

public class HibernateOracleConnect extends PoolConnect{
	/** объект, который получает сессии
	 * @param login логин
	 * @param password пароль
	 * @param pool_size размер пула
	 * */
	public HibernateOracleConnect(String login, 
								  String password, 
								  Integer poolSize){
		super(login,password, poolSize);
	}
	
	@Override
	protected String getDataBasePath() {
		return "jdbc:oracle:thin:@91.195.53.27:1521:demo";
		//return "jdbc:oracle:thin:@192.168.15.254:1521:demo";
	}

	@Override
	protected String geDriverName() {
		return "oracle.jdbc.driver.OracleDriver";
	}

}
