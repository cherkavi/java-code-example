package bonpay.mail.sender_core.sender;

/** класс настроек для отправщика (SMTP сервер, логин, пароль ... )*/
public class SenderSettings {
	/** SMTP сервер*/
	private String smtpServer;
	/** SMTP port */
	private int smtpPort;
	/** login */
	private String login;
	/** password*/
	private String password;
	/** need authentification */
	private boolean needAuthentification=false;
	/** время в милисекундах для следующей отправки данных на сервер */
	private long timeDelay;
	/** полный E-mail адрес получателя */
	private String from;
	
	/** класс настроек для отправщика (SMTP сервер, логин, пароль ... )
	 * @param from - полный адрес получателя 
	 * @param smtpServer - адрес сервера-отправителя 
	 * @param smtpPort - адрес сервера получателя 
	 * @param login - Логин
	 * @param password - пароль 
	 * @param needAuthentification - необходимость аутентификации 
	 * @param timeDelayMs - временная задержка
	 */
	public SenderSettings(String from, String smtpServer, int smtpPort, String login, String password, boolean needAuthentification, long timeDelayMs){
		this.from=from;
		this.smtpServer=smtpServer;
		this.smtpPort=smtpPort;
		this.login=login;
		this.password=password;
		this.needAuthentification=needAuthentification;
		this.timeDelay=timeDelayMs;
	}
	
	/** время в милисекундах для следующей отправки данных на сервер */
	public long getTimeDelay(){
		return timeDelay;
	}

	/**
	 * @return the smtpServer
	 */
	public String getSmtpServer() {
		return smtpServer;
	}

	/**
	 * @param smtpServer the smtpServer to set
	 */
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	/**
	 * @return the smtpPort
	 */
	public int getSmtpPort() {
		return smtpPort;
	}

	/**
	 * @param smtpPort the smtpPort to set
	 */
	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the needAuthentification
	 */
	public boolean isNeedAuthentification() {
		return needAuthentification;
	}

	/**
	 * @param needAuthentification the needAuthentification to set
	 */
	public void setNeedAuthentification(boolean needAuthentification) {
		this.needAuthentification = needAuthentification;
	}

	/**
	 * @param timeDelay the timeDelay to set
	 */
	public void setTimeDelay(long timeDelay) {
		this.timeDelay = timeDelay;
	}


	/** установить полный E-Mail отправителя */
	public void setFrom(String from){
		this.from=from;
	}
	
	/** полный E-mail отправителя */
	public String getFrom() {
		return this.from;
	}
	
	
}
