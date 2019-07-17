package bonpay.mail.sender_one_core.interfaces;

public interface ILetterSettingsAware {
	/** логин SMTP */
	public String getLogin();
	/** пароль SMTP */
	public String getPassword();
	/** адрес SMTP сервера */
	public String getSmtp();
	/** порт SMTP сервера */
	public int getPort();
	/** нужна ли аутентификация на сервере при отправке письма */
	public boolean isAuth();
}
