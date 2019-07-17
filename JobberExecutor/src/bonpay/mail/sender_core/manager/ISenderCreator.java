package bonpay.mail.sender_core.manager;

import java.util.ArrayList;

import bonpay.mail.sender_core.sender.SenderThread;

/** объект, который создает Sender на основании уникального идентификатора */
public interface ISenderCreator {
	/** получить уникальные идентификаторы по всем Sender */
	public ArrayList<SenderIdentifier> getAllSendersIdentifier();
	/** получить Sender на основании уникального идентификатора */
	public SenderThread getSender(SenderIdentifier senderIdentifier);
}
