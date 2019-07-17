package bonpay.mail.sender_core.manager;

/** интерфейс, который содержит необходимые методы по изменению отправщиков почтовых рассылок */
public interface ISenderChange {
	/** оповестить о необходимости изменения в настройках Sender */
	public void notifySenderChange(SenderIdentifier senderIdentifier);
	/** оповестить о создании Sender */
	public void notifySenderCreate(SenderIdentifier senderIdentifier);
	/** оповестить об удалении Sender */
	public void notifySenderRemove(SenderIdentifier senderIdentifier);
}
