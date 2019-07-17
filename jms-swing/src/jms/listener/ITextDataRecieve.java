package jms.listener;

/** интерфейс прослушивания текстовых данных */
public interface ITextDataRecieve {
	
	/**  получено удаленное текстовое сообщение от сервера
	 * @param sender - отправитель сообщения  
	 * @param remoteTextMessage - текстовое сообщение от удаленного сервера 
	 * */
	public void recieveTextData(String sender, String remoteTextMessage);
}
