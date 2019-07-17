package bonpay.mail.sender_core.sender;

import bonpay.mail.sender_core.manager.SenderIdentifier;

/** оповещение о необходимости отправки нового письма */
public interface INotifyNewLetter {
	/** оповещение всех объектов о необходимости получения нового письма из базы данных */
	public void notifyAboutNewLetter();
	/** оповещение определенного объекта ({@link SenderIdentifier}) о необходимости получения нового письма из базы данных */ 
	public void notifyAboutNewLetter(SenderIdentifier senderIdentifier);
}
