package bonpay.mail.sender_one_core.implementations;

import java.sql.ResultSet;
import java.sql.Connection;

import org.apache.log4j.Logger;

import bonpay.mail.sender_one_core.interfaces.ILetterSettingsAware;
import bonpay.mail.sender_one_core.interfaces.ILetterBodyAware;

/** письмо из базы данных на основании уникального идентификатора  */
public class LetterFromDatabaseById implements ILetterBodyAware, ILetterSettingsAware{
	private Logger logger=Logger.getLogger(this.getClass());
	/** получатели */
	private String[] recipients;
	/** предмет письма */
	private String subject;
	/** текст письма */
	private String text;
	/** от кого */
	private String from;
	/** логин */
	private String login;
	/** пароль */
	private String password;
	/** SMTP сервер */
	private String smtp;
	/** порт */
	private int port;
	/** необходимость в аутентификации */
	private boolean auth=false;
	
	/** прочесть необходимые поля из базы данных ( из таблицы bc_admin.VC_EMAIL_ALL ) 
	 * @param connection - соединение с базой данных 
	 * @param letterId - уникальный идентификатор письма 
	 * @throws Exception - если не удалось прочесть письмо 
	 */
	public LetterFromDatabaseById(Connection connection, int letterId) throws Exception{
		logger.debug("прочесть необходимые поля из базы данных ");
		String query="select * from bc_admin.VC_EMAIL_ALL where id_message="+letterId;
		ResultSet rs=connection.createStatement().executeQuery(query);
		logger.debug(query);
		if(rs.next()){
			this.loadFromResultSet(rs);
			rs.getStatement().close();
			logger.debug("письмо найдено и загружено в объект ");
		}else{
			logger.warn("письмо не найдено: "+letterId);
			rs.getStatement().close();
			throw new Exception("Letter was not found ");
		}
	}

	/** загрузить данный объект из ResultSet  
	 * @param rs - набор данных 
	 * @throws Exception если произошли ошибки при попытке получения письма и/или параметров  
	 */
	private void loadFromResultSet(ResultSet rs) throws Exception{
		this.recipients=new String[]{rs.getString("RECEIVER_EMAIL")};
		this.subject=rs.getString("TITLE_MESSAGE");
		this.text=rs.getString("TEXT_MESSAGE");
		this.from=rs.getString("SENDER_EMAIL");
		this.login=rs.getString("SMTP_USER");
		this.password=rs.getString("SMTP_PASSWORD");
		this.smtp=rs.getString("SMTP_SERVER");
		this.port=rs.getInt("SMTP_PORT");
		auth=rs.getString("NEED_AUTORIZATION").equals("Y");
	}

	
	
	/** прочесть необходимые поля из базы данных ( из таблицы bc_admin.VC_EMAIL_ALL )
	 * @param rs - набор данных (курсор должен стоять на записи )
	 * @throws Exception (исключение, если не удалось прочесть данные )
	 */
	public LetterFromDatabaseById(ResultSet rs) throws Exception{
		this.loadFromResultSet(rs);
	}
	

	@Override
	public String getLogin() {
		return this.login;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public int getPort() {
		return this.port;
	}

	@Override
	public String getSmtp() {
		return this.smtp;
	}

	@Override
	public boolean isAuth() {
		return this.auth;
	}


	@Override
	public String getFrom() {
		return this.from;
	}


	@Override
	public String[] getRecipients() {
		return this.recipients;
	}


	@Override
	public String getSubject() {
		return this.subject;
	}


	@Override
	public String getText() {
		return this.text;
	}
	
}
