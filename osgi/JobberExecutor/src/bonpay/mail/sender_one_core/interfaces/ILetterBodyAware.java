package bonpay.mail.sender_one_core.interfaces;

/** интерфейс, который владеет необходимыми для создания/отправки письма параметрами */
public interface ILetterBodyAware {
	/** получить всех получателей данного уведомления */
	public String[] getRecipients();
	/** получить Subject письма */
	public String getSubject(); 
	/** получить текст письма */
	public String getText(); 
	/** адрес отправителя (от кого )*/
	public String getFrom();
}
