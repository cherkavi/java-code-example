package bonclub.office_private.gui.text_exchange;

/** интерфейс по отправке текстового сообщения  */
public interface ITextOutput {
	/** отправить текстовое сообщение 
	 * @param message - текстовое сообщение
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - успешно отправлено </li>
	 * 	<li><b>false</b> - не отправлено </li>
	 * </ul>
	 * */
	public boolean sendText(String message);
}
