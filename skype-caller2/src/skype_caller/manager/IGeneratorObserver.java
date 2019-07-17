package skype_caller.manager;

import skype_caller.command_generator.ACommandGenerator;

public interface IGeneratorObserver {
	/** notify commandGenertor observer about new command for execute  */
	public void needToExecuteGenerator(ACommandGenerator generator);
}
