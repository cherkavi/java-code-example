package skype_caller.command_generator;

import java.util.List;

public class GeneratorContainer {
	private List<ACommandGenerator> listOfCommandGenerator;

	public List<ACommandGenerator> getListOfCommandGenerator() {
		return listOfCommandGenerator;
	}

	public void setListOfCommandGenerator(
			List<ACommandGenerator> listOfCommandGenerator) {
		this.listOfCommandGenerator = listOfCommandGenerator;
	}
	
}
