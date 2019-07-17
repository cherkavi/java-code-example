package bonpay.mail.sender_core.sender;

import bonpay.mail.sender_core.manager.SenderIdentifier;

/** методы по получению настроек для  */
public interface ISenderSettingsAware {
	/** получить по указанному {@link SenderIdentifier} настройки */
	public SenderSettings getSenderSettingsByIdentifier(SenderIdentifier identifier);
}
