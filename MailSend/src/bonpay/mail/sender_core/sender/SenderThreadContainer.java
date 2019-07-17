package bonpay.mail.sender_core.sender;

import java.sql.Connection;

import java.sql.ResultSet;
import java.util.ArrayList;

import pay.database.connector.IConnectorAware;

import bonpay.mail.sender_core.manager.ILetterAware;
import bonpay.mail.sender_core.manager.ISenderCreator;
import bonpay.mail.sender_core.manager.SenderIdentifier;
import bonpay.mail.sender_core.sender.ISenderSettingsAware;
import bonpay.mail.sender_core.sender.SenderSettings;
import bonpay.mail.sender_core.sender.SenderThread;

/** Контейнер для отправителей почтовых сообщений  */
public class SenderThreadContainer implements ISenderCreator, ISenderSettingsAware, INotifyNewLetter, INotifySettingsChange{
	/** соединение с базой данных */
	private IConnectorAware connectWrapAware;
	/** объект, знающий/ведающий о письмах на сервере */
	private ILetterAware letterAware;
	/** уникальные идентификаторы отправителей */
	private ArrayList<SenderIdentifier> listOfIdentifier=new ArrayList<SenderIdentifier>();
	/** список потоков-отправителей */
	private ArrayList<SenderThread> listOfSenderThread=new ArrayList<SenderThread>();
	/** флаг того, что все потоки уже запущены */
	private boolean flagThreadIsStarted=false;
	
	/** Контейнер для отправителей почтовых сообщений  */
	public SenderThreadContainer(IConnectorAware connectWrapAware, ILetterAware letterAware){
		this.connectWrapAware=connectWrapAware;
		this.letterAware=letterAware;
		// прочесть все идентификаторы
		this.readAllIdentifiers();
		// создать все SenderThread
		this.createAllSenderThread();
	}
	
	/** запустить все потоки-отправители */
	public void startAllSenderThread(){
		if(this.flagThreadIsStarted==false){
			for(int index=0;index<this.listOfSenderThread.size();index++){
				this.listOfSenderThread.get(index).start();
			}
			this.flagThreadIsStarted=true;
		}else{
			// потоки уже запущены 
		}
	}
	
	/** на основании уникальных идентификаторов создать отправителей */
	private void createAllSenderThread(){
		for(int index=0;index<this.listOfIdentifier.size();index++){
			this.listOfSenderThread.add(new SenderThread(this.listOfIdentifier.get(index), letterAware, this));
		}
	}
	
	/** прочесть все уникальные идентификаторы отправителей */
	private void readAllIdentifiers(){
		Connection connection=this.connectWrapAware.getConnection();
		try{
			ResultSet rs=connection.createStatement().executeQuery("select * from bc_admin.VC_DS_EMAIL_PROFILE_ALL");
			while(rs.next()){
				listOfIdentifier.add(new SenderIdentifier(rs.getInt("ID_EMAIL_PROFILE")));
			}
			rs.getStatement().close();
		}catch(Exception ex){
			System.err.println("Test#getAllSenders Exception: "+ex.getMessage());
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
		SenderSettings returnValue=null;
		Connection connection=this.connectWrapAware.getConnection();
		try{
			String query="select * from bc_admin.VC_DS_EMAIL_PROFILE_ALL where ID_EMAIL_PROFILE="+identifier.getId();
			ResultSet rs=connection.createStatement().executeQuery(query);
			if(rs.next()){
				returnValue=new SenderSettings(rs.getString("SENDER_EMAIL"),
											   rs.getString("SMTP_SERVER"),
											   rs.getInt("SMTP_PORT"),
											   rs.getString("SMTP_USER"),
											   rs.getString("SMTP_PASSWORD"),
											   rs.getString("NEED_AUTORIZATION").equals("Y"),
											   rs.getLong("DELAY_NEXT_LETTER"));
			}
			rs.getStatement().close();
		}catch(Exception ex){
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
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifyAboutNewLetter();
		}
	}

	@Override
	public void notifyAboutNewLetter(SenderIdentifier senderIdentifier) {
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifyAboutNewLetter(senderIdentifier);
		}
	}

	@Override
	public void notifySenderSettingsChange() {
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifySenderSettingsChange();
		}
	}

	@Override
	public void notifySenderSettingsChange(SenderIdentifier senderIdentifier) {
		for(int index=0;index<this.listOfSenderThread.size();index++){
			this.listOfSenderThread.get(index).notifySenderSettingsChange(senderIdentifier);
		}
	}
	
}