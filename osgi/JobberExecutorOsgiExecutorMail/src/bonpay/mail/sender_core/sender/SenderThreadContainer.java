package bonpay.mail.sender_core.sender;

import java.sql.Connection;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import database.IConnectionAware;

import bonpay.mail.sender_core.manager.ILetterAwareFactory;
import bonpay.mail.sender_core.manager.ISenderCreator;
import bonpay.mail.sender_core.manager.SenderIdentifier;
import bonpay.mail.sender_core.sender.ISenderSettingsAware;
import bonpay.mail.sender_core.sender.SenderSettings;
import bonpay.mail.sender_core.sender.SenderThread;

/** Контейнер для отправителей почтовых сообщений  */
public class SenderThreadContainer implements ISenderCreator, ISenderSettingsAware, INotifyNewLetter, INotifySettingsChange{
	private Logger logger=Logger.getLogger(this.getClass());
	/** соединение с базой данных */
	private IConnectionAware connectWrapAware;
	/** фабрика, генерирующая объекты, знающие/ведающий о письмах на сервере */
	private ILetterAwareFactory letterAwareFactory;
	/** уникальные идентификаторы отправителей */
	private ArrayList<SenderIdentifier> listOfIdentifier=new ArrayList<SenderIdentifier>();
	/** список потоков-отправителей */
	private ArrayList<SenderThread> listOfSenderThread=new ArrayList<SenderThread>();
	/** флаг того, что все потоки уже запущены */
	private boolean flagThreadIsStarted=false;
	
	/** Контейнер для отправителей почтовых сообщений  */
	public SenderThreadContainer(IConnectionAware connectWrapAware, ILetterAwareFactory letterAwareFactory){
		this.connectWrapAware=connectWrapAware;
		this.letterAwareFactory=letterAwareFactory;
		logger.debug("прочесть все идентификаторы");
		this.readAllIdentifiers();
		logger.debug("создать все SenderThread");
		this.createAllSenderThread();
	}
	
	/** запустить все потоки-отправители */
	public void startAllSenderThread(){
		logger.debug("запустить все потоки-отправители");
		if(this.flagThreadIsStarted==false){
			for(int index=0;index<this.listOfSenderThread.size();index++){
				this.listOfSenderThread.get(index).start();
			}
			this.flagThreadIsStarted=true;
		}else{
			logger.debug("потоки уже запущены");
		}
	}
	
	/** на основании уникальных идентификаторов создать отправителей */
	private void createAllSenderThread(){
		logger.debug("на основании уникальных идентификаторов создать отправителей");
		for(int index=0;index<this.listOfIdentifier.size();index++){
			this.listOfSenderThread.add(new SenderThread(this.listOfIdentifier.get(index), 
														 letterAwareFactory.createNewLetterAware(), 
														 this));
		}
	}
	
	/** прочесть все уникальные идентификаторы отправителей */
	private void readAllIdentifiers(){
		logger.debug("прочесть все уникальные идентификаторы отправителей");
		Connection connection=this.connectWrapAware.getConnection();
		try{
			ResultSet rs=connection.createStatement().executeQuery("select * from bc_admin.VC_DS_EMAIL_PROFILE_ALL");
			logger.debug("select * from bc_admin.VC_DS_EMAIL_PROFILE_ALL");
			while(rs.next()){
				listOfIdentifier.add(new SenderIdentifier(rs.getInt("ID_EMAIL_PROFILE")));
			}
			rs.getStatement().close();
		}catch(Exception ex){
			System.err.println("SenderThreadContainer#getAllSenders Exception: "+ex.getMessage());
			logger.error("SenderThreadContainer#getAllSenders Exception: "+ex.getMessage(),ex);
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
	}
	
	@Override
	public ArrayList<SenderIdentifier> getAllSendersIdentifier() {
		return this.listOfIdentifier;
	}

	@Override
	public SenderThread getSender(SenderIdentifier senderIdentifier) {
		int index=this.listOfIdentifier.indexOf(senderIdentifier);
		if(index>=0){
			return this.listOfSenderThread.get(index);
		}else{
			return null;
		}
	}

	@Override
	public SenderSettings getSenderSettingsByIdentifier(SenderIdentifier identifier) {
		logger.debug("получить настройки Sender-a ");
		SenderSettings returnValue=null;
		Connection connection=this.connectWrapAware.getConnection();
		try{
			String query="select * from bc_admin.VC_DS_EMAIL_PROFILE_ALL where ID_EMAIL_PROFILE="+identifier.getId();
			logger.debug("select * from bc_admin.VC_DS_EMAIL_PROFILE_ALL where ID_EMAIL_PROFILE="+identifier.getId());
			ResultSet rs=connection.createStatement().executeQuery(query);
			if(rs.next()){
				returnValue=new SenderSettings(rs.getString("SENDER_EMAIL"),
											   rs.getString("SMTP_SERVER"),
											   rs.getInt("SMTP_PORT"),
											   rs.getString("SMTP_USER"),
											   rs.getString("SMTP_PASSWORD"),
											   rs.getString("NEED_AUTORIZATION").equals("Y"),
											   rs.getLong("DELAY_NEXT_LETTER"),
											   rs.getInt("max_error_messages"));
			}
			rs.getStatement().close();
		}catch(Exception ex){
			logger.error("Test#getAllSenders Exception: "+ex.getMessage(), ex);
			System.err.println("Test#getAllSenders Exception: "+ex.getMessage());
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}

	@Override
	public void notifyAboutNewLetter() {
		logger.debug("оповестить все потоки отправщиков о наличии новых писем ");
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifyAboutNewLetter();
		}
	}

	@Override
	public void notifyAboutNewLetter(SenderIdentifier senderIdentifier) {
		logger.debug("оповестить конкретного отпращика о наличии новых писем для него ");
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifyAboutNewLetter(senderIdentifier);
		}
	}

	@Override
	public void notifySenderSettingsChange() {
		logger.debug("оповестить всех отправщиков о необходимости обновления параметров (отправки) ");
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifySenderSettingsChange();
		}
	}

	@Override
	public void notifySenderSettingsChange(SenderIdentifier senderIdentifier) {
		logger.debug("оповестить конкретного отправщика о необходимости обновления параметров (отправки) ");
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifySenderSettingsChange(senderIdentifier);
		}
	}

	public void stopAllSenderThread() {
		logger.debug("остановить все потоки ");
		if(this.flagThreadIsStarted==true){
			for(int index=0;index<this.listOfSenderThread.size();index++){
				try{
					this.listOfSenderThread.get(index).stopThread();
					this.listOfSenderThread.get(index).interrupt();
				}catch(Exception ex){
					logger.info("ошибка остановки потока №"+index);
				}
			}
			this.flagThreadIsStarted=true;
		}else{
			logger.debug("потоки уже остановлены");
		}
	}
	
}