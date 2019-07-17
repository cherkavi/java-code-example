package bonpay.mail.sender_core.manager;

import java.util.ArrayList;

import bonpay.mail.sender_core.letter.Letter;
import bonpay.mail.sender_core.letter.LetterIdentifier;

/** интерфейс манипул€ции с письмами из базы данных 
 * <li> получение дл€ отправки </li>
 * <li> пометить как отправленное  </li>
 * */
public interface ILetterAware {
	/** получить все идентификаторы писем дл€ отправки из базы данных */
	public ArrayList<LetterIdentifier> getNewLetters(SenderIdentifier senderIdentifier);
	
	/** получить письмо по уникальному идентификатору 
	 * @param letterIdentifier - уникальный идентификатор письма 
	 * @return
	 */
	public Letter getLetter(LetterIdentifier letterIdentifier);

	/** установить дл€ письма флаг "отправлен" */
	public boolean setLetterAsSended(LetterIdentifier letterIdentifier);

	/** установить дл€ письма флаг "ошибка отправки " */
	public boolean setLetterAsSendedError(LetterIdentifier letterIdentifier, String errorMessage);

	/** дл€ указанного письма повысить значение флага ошибочной отправки */
	public boolean incLetterErrorCounter(LetterIdentifier letterIdentifier);
	
}
