package skype_caller.manager;

import skype_caller.command_generator.ACommandGenerator;

/** class for execute Generator after wait some time */
public class LaterExecuteGenerator implements Runnable{
	private final ACommandGenerator generator;
	private final IGeneratorObserver observer;
	
	public LaterExecuteGenerator(IGeneratorObserver generatorObserver, ACommandGenerator generator){
		this.generator=generator;
		this.observer=generatorObserver;
	}
	
	@Override
	public void run() {
		this.observer.needToExecuteGenerator(generator);
	}
}
