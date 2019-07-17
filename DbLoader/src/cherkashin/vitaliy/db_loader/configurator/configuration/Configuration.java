package cherkashin.vitaliy.db_loader.configurator.configuration;

import java.util.List;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.Connector;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.File;

/** this is program configuration */
public class Configuration {
	private Connector connector=null;
	private List<File> files=null;
	
	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	/** get File List */
	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	
}
