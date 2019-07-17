package bonpay.mail.sender_core;

import database.IConnectionAware;
import database.OracleConnectionAware;
import bonpay.jobber.launcher.executor.IExecutor;
import bonpay.mail.sender_core.sender.SenderThreadContainer;

/** объект, который запускает службу рассылки писем */
public class MailExecutor implements IExecutor{
	private static IConnectionAware connectorAware=new OracleConnectionAware();
	private static SenderThreadContainer senderThreadContainer=new SenderThreadContainer(connectorAware,new LetterAware(connectorAware));

	/** объект, который запускает службу рассылки писем */
	public MailExecutor(){
	}
	
	@Override
	public void execute() {
		senderThreadContainer.startAllSenderThread();
		senderThreadContainer.notifySenderSettingsChange();
		senderThreadContainer.notifyAboutNewLetter();
	}

	@Override
	public void stop() {
		senderThreadContainer.stopAllSenderThread();
		
	}

}
