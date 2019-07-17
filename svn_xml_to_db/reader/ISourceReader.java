package svn_xml_to_db.reader;

import java.util.List;

import svn_xml_to_db.transit_format.LogEntry;

public interface ISourceReader {
	/** get {@link LogEntry} from source */
	public List<LogEntry> getLogEntry() throws ESourceReader;
}
