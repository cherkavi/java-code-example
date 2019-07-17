package bonpay.mail.sender_core;

import bonpay.mail.sender_core.manager.ILetterAware;
import bonpay.mail.sender_core.manager.ILetterAwareFactory;
import database.IConnectionAware;

class LetterAwareFactory implements ILetterAwareFactory{
	private IConnectionAware connectorAware;
	
	public LetterAwareFactory(IConnectionAware connectorAware){
		this.connectorAware=connectorAware;
	}
	
	@Override
	public ILetterAware createNewLetterAware() {
		return new LetterAware(this.connectorAware);
	}
	
}