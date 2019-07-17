package bonpay.mail.sender_one_core.interfaces;

import java.sql.Connection;

public interface IConfirmSendLetter {
	/** записать письмо как посланное */
	public boolean setLetterAsSended(int id, Connection connection);
	/** записать письмо как не отправленное  */
	public boolean setLetterAsSendedError(int id, Connection connection);
}
