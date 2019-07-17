package svn_xml_to_db.writer;

import java.util.List;

import svn_xml_to_db.transit_format.LogEntry;

public interface IDestinationWriter {
	/** write list of {@link LogEntry} to Destination  */
	public void writeToDestination(List<LogEntry> list)throws EDestinationException;
}
