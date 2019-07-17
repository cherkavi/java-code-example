package cherkashin.vitaliy.db_loader.configurator.configuration;

import java.util.List;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.Connector;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.File;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.dictionary.Dictionary;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.format.Format;

/** this is program configuration */
public class Configuration {
	private Connector connector=null;
	private List<File> files=null;
	private List<Format> formats=null;
	private List<Dictionary> dictionaries=null;
	
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

	public void setFormats(List<Format> formats) {
		this.formats=formats;
	}

	/** get list of formats */
	public List<Format> getFormats() {
		return this.formats;
	}

	/** set dictionaries */
	public void setDictionaries(List<Dictionary> dictionaries) {
		this.dictionaries=dictionaries;
	}

	/** get dictionaries */
	public List<Dictionary> getDictionaries() {
		return dictionaries;
	}
	
	
}
