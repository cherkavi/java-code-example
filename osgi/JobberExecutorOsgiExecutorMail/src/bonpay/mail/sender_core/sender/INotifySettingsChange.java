package bonpay.mail.sender_core.sender;

import bonpay.mail.sender_core.manager.SenderIdentifier;

/** интерфейс оповещения о необходимости обновления настроек  */
public interface INotifySettingsChange {
	/** оповещение всех отправителей {@link SenderThread} о необходимости обновления своих параметров */
	public void notifySenderSettingsChange();
	/** оповещение конкретного отправителя ({@link SenderIdentifer}) {@link SenderThread} о необходимости обновления своих параметров */ 
	public void notifySenderSettingsChange(SenderIdentifier senderIdentifier);
}
